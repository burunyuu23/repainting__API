package com.example.therepaintinggameweb.services;

import com.example.therepaintinggameweb.dtos.requests.GameStepRequestDTO;
import com.example.therepaintinggameweb.dtos.requests.game.GameStartRequestDTO;
import com.example.therepaintinggameweb.dtos.responses.game.NonRatingGameResponseDTO;
import com.example.therepaintinggameweb.entities.*;
import com.example.therepaintinggameweb.exceptions.*;
import com.example.therepaintinggameweb.logic.GameStatus;
import com.example.therepaintinggameweb.logic.GameWrapper;
import com.example.therepaintinggameweb.logic.GameWrapperFactory;
import com.example.therepaintinggameweb.repos.NonRatingGameRepo;
import com.example.therepaintinggameweb.repos.NonRatingGameStoryRepo;
import com.example.therepaintinggameweb.repos.PalettesRepo;
import com.example.therepaintinggameweb.repos.UserRepo;
import com.example.therepaintinggameweb.utils.GameSessionManager;
import com.example.therepaintinggameweb.utils.UserUtils;
import com.nimbusds.jose.shaded.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NonRatingGameService extends GameService {
    private final NonRatingGameStoryRepo nonRatingGameStoryRepo;
    private final NonRatingGameRepo nonRatingGameRepo;

    public NonRatingGameService(PalettesRepo palettesRepo, ModelMapper modelMapper, Gson gson, GameWrapperFactory gameWrapperFactory, GameSessionManager gameSessionManager, UserRepo userRepo, NonRatingGameStoryRepo nonRatingGameStoryRepo, NonRatingGameRepo nonRatingGameRepo) {
        super(palettesRepo, modelMapper, gson, gameWrapperFactory, gameSessionManager, userRepo);
        this.nonRatingGameStoryRepo = nonRatingGameStoryRepo;
        this.nonRatingGameRepo = nonRatingGameRepo;
    }

    @Override
    protected Game gameSave(String gameId, GameWrapper gameWrapper, GameStartRequestDTO gameStartRequestDTO) {
        User user = UserUtils.addAndGetCurrentUser();

        NonRatingGame nonRatingGame = new NonRatingGame();
        nonRatingGame.setGameId(gameId);
        nonRatingGame.setUser(user);

        nonRatingGame.setMap(gson.toJson(gameWrapper.getMap()));
        nonRatingGame.setMaxRounds(gameWrapper.getMaxRounds());
        nonRatingGame.setPalettes(palettesRepo.findById(gameStartRequestDTO
                        .getPaletteId())
                .orElse(palettesRepo.findById(0L).orElse(null)));
        nonRatingGame.setEnd(gameWrapper.getGameStatus() != GameStatus.PLAYING);
        nonRatingGameRepo.save(nonRatingGame);

        return nonRatingGame;
    }

    public NonRatingGameResponseDTO resultGame(String gameId) {
        NonRatingGameResponseDTO nonRatingGameResponseDTO = new NonRatingGameResponseDTO();

        Optional<NonRatingGame> gameOptional = nonRatingGameRepo.findById(gameId);
        if (gameOptional.isEmpty())
            throw new GameNotFoundException();
        NonRatingGame nonRatingGame = gameOptional.get();

        nonRatingGameResponseDTO.setNonRatingGame(nonRatingGame);
        return nonRatingGameResponseDTO;
    }

    @Override
    public void endGame(GameWrapper gameWrapper, Game nonRatingGame) {
        nonRatingGame.setEnd(true);
        nonRatingGame.setIsWin(gameWrapper.getGameStatus() == GameStatus.WON);
        nonRatingGameRepo.save((NonRatingGame) nonRatingGame);
    }

    @Override
    protected GameStory gameStorySave(Game nonRatingGame, String gameId, GameWrapper gameWrapper, GameStepRequestDTO gameStepRequestDTO) {
        NonRatingGameStory nonRatingGameStory = new NonRatingGameStory();
        nonRatingGameStory.setGame((NonRatingGame) nonRatingGame);
        nonRatingGameStory.setId(new GameStoryId(gameId, gameWrapper.getCurrentRound()));
        nonRatingGameStory.setChosenColor(gameStepRequestDTO.getColorId());
        nonRatingGameStory.setRoundMap(gson.toJson(gameWrapper.getMap()));

        nonRatingGameStoryRepo.save(nonRatingGameStory);
        return nonRatingGameStory;
    }

    @Override
    protected Game checkGameExist(String gameId) {
        Optional<NonRatingGame> gameOptional = nonRatingGameRepo.findById(gameId);
        if (gameOptional.isEmpty())
            throw new GameNotFoundException();

        NonRatingGame nonRatingGame = gameOptional.get();
        if (!nonRatingGame.getUser().getUserId().equals(UserUtils.getCurrentUserId()))
            throw new UserNotFromThisGameException();

        if (nonRatingGame.isEnd())
            throw new GameIsEndException();

        return nonRatingGame;
    }
}
