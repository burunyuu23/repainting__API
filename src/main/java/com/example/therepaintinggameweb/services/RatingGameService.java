package com.example.therepaintinggameweb.services;

import com.example.therepaintinggameweb.dtos.requests.GameStepRequestDTO;
import com.example.therepaintinggameweb.dtos.requests.game.GameStartRequestDTO;
import com.example.therepaintinggameweb.dtos.responses.game.*;
import com.example.therepaintinggameweb.entities.*;
import com.example.therepaintinggameweb.exceptions.GameIsEndException;
import com.example.therepaintinggameweb.exceptions.GameNotFoundException;
import com.example.therepaintinggameweb.exceptions.UserNotFromThisGameException;
import com.example.therepaintinggameweb.logic.Color;
import com.example.therepaintinggameweb.logic.GameStatus;
import com.example.therepaintinggameweb.logic.GameWrapper;
import com.example.therepaintinggameweb.logic.GameWrapperFactory;
import com.example.therepaintinggameweb.repos.*;
import com.example.therepaintinggameweb.utils.UserUtils;
import com.nimbusds.jose.shaded.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Math.*;

@Service
public class RatingGameService extends GameService{
    private final RatingGameStoryRepo gameStoryRepo;
    private final RatingGameRepo gameRepo;

    public RatingGameService(PalettesRepo palettesRepo, ModelMapper modelMapper, Gson gson, GameWrapperFactory gameWrapperFactory, ConcurrentHashMap<String, GameWrapper> gameWrapperList, UserRepo userRepo, RatingGameStoryRepo gameStoryRepo, RatingGameRepo gameRepo) {
        super(palettesRepo, modelMapper, gson, gameWrapperFactory, gameWrapperList, userRepo);
        this.gameStoryRepo = gameStoryRepo;
        this.gameRepo = gameRepo;
    }

    @Override
    public GameStartResponseDTO startGame(GameStartRequestDTO gameStartRequestDTO) {
        Color[] palettes = palettesSetup(gameStartRequestDTO.getPaletteId());

        GameWrapper gameWrapper = gameWrapperFactory.createGameWrapper(palettes);

        String gameId = UUID.randomUUID().toString();
        gameWrapperList.put(gameId, gameWrapper);

        gameSave(gameId, gameWrapper, gameStartRequestDTO);

        GameStartResponseDTO gameStartResponseDTO = modelMapper.map(gameWrapper, GameStartResponseDTO.class);
        gameStartResponseDTO.setGameId(gameId);

        return gameStartResponseDTO;
    }

    @Override
    protected Game gameSave(String gameId, GameWrapper gameWrapper, GameStartRequestDTO gameStartRequestDTO) {
        User user = UserUtils.addAndGetCurrentUser();

        RatingGame ratingGame = new RatingGame();
        ratingGame.setGameId(gameId);
        ratingGame.setUser(user);

        ratingGame.setMap(gson.toJson(gameWrapper.getMap()));
        ratingGame.setPalettes(palettesRepo.findById(gameStartRequestDTO
                        .getPaletteId())
                .orElse(palettesRepo.findById(0L).orElse(null)));
        ratingGame.setEnd(false);
        gameRepo.save(ratingGame);

        return ratingGame;
    }

    @Override
    protected Game checkGameExist(String gameId) {
        Optional<RatingGame> gameOptional = gameRepo.findById(gameId);
        if (gameOptional.isEmpty())
            throw new GameNotFoundException();

        RatingGame ratingGame = gameOptional.get();
        if (!ratingGame.getUser().getUserId().equals(UserUtils.getCurrentUserId()))
            throw new UserNotFromThisGameException();

        if (ratingGame.isEnd())
            throw new GameIsEndException();

        return ratingGame;
    }

    @Override
    protected GameStory gameStorySave(Game game, String gameId, GameWrapper gameWrapper, GameStepRequestDTO gameStepRequestDTO) {
        RatingGameStory gameStory = new RatingGameStory();
        gameStory.setGame((RatingGame) game);
        gameStory.setId(new GameStoryId(gameId, gameWrapper.getCurrentRound()));
        gameStory.setChosenColor(gameStepRequestDTO.getColorId());
        gameStory.setRoundMap(gson.toJson(gameWrapper.getMap()));
        gameStoryRepo.save(gameStory);

        gameStory.setRatingChange(countRating(game, gameStory.getId().getRound(), gameStory.getStepTime()));
        gameStoryRepo.save(gameStory);

        return gameStory;
    }

    public RatingGameResultResponseDTO resultGame(String gameId) {
        RatingGameResultResponseDTO ratingGameResponseDTO = new RatingGameResultResponseDTO();

        Optional<RatingGame> gameOptional = gameRepo.findById(gameId);
        if (gameOptional.isEmpty())
            throw new GameNotFoundException();
        RatingGame ratingGame = gameOptional.get();

        ratingGameResponseDTO.setRatingGame(ratingGame);
        return ratingGameResponseDTO;
    }

    private int countRating(Game game, int currentRound, LocalDateTime currentTime) {
        System.out.println(gameRepo.sumRatingByUserId(UserUtils.getCurrentUserId()));
        System.out.println(currentRound);
        System.out.println(game.getStartTime());
        System.out.println(currentTime);
        Integer currentRating = gameRepo.sumRatingByUserId(UserUtils.getCurrentUserId());
        if (currentRating == null)
            currentRating = 0;

        Duration duration;
        if (currentRound == 1)
            duration = Duration.between(game.getStartTime(), currentTime);
        else {
            GameStory prevGameStory = gameStoryRepo.findById(new GameStoryId(game.getGameId(), currentRound - 1))
                    .orElseThrow(GameNotFoundException::new);

            duration = Duration.between(prevGameStory.getStepTime(), currentTime);
        }

        System.out.println(duration);
        double deltaTime = duration.toMillis() / 1000.0;
        System.out.println(deltaTime);

        double deltaRating = max(0, log(currentRating/100.0 + 1));
        int normalRound = (int) (22 - (deltaRating + 1));
        int normalTime = (int) (5 * deltaRating);

        return (int) ((pow(normalTime, 2) - pow(deltaTime, 2))/deltaTime * log((23 + normalRound - currentRound)/3.0));
    }

    @Override
    public RatingGameStepResponseDTO stepGame(GameStepRequestDTO gameStepRequestDTO) {
        RatingGameStepResponseDTO ratingGameStepResponseDTO = modelMapper.map(super.stepGame(gameStepRequestDTO), RatingGameStepResponseDTO.class);
        RatingGameStory prevGameStory = gameStoryRepo.findById(new GameStoryId(ratingGameStepResponseDTO.getGameId(), ratingGameStepResponseDTO.getCurrentRound()))
                .orElseThrow(GameNotFoundException::new);
        ratingGameStepResponseDTO.setRatingChange(prevGameStory.getRatingChange());
        return ratingGameStepResponseDTO;
    }

    @Override
    protected void endGame(GameWrapper gameWrapper, Game game) {
        game.setEnd(true);
        game.setIsWin(gameWrapper.getGameStatus() == GameStatus.WON);

        long additionalRating = 30 * (game.getIsWin() ? 1 : -1) +
                (game.getIsWin() ? 5L * (gameWrapper.getMaxRounds() - gameWrapper.getCurrentRound()) : 0);

        RatingGame ratingGame = (RatingGame) game;
        Integer prevRating = gameRepo.sumRatingByGameId(game.getGameId());
        prevRating = prevRating == null ? 0 : prevRating;
        ratingGame.setRating(prevRating + gameStoryRepo.sumRatingByGameId(game.getGameId()) + additionalRating);

        gameRepo.save(ratingGame);
    }
}
