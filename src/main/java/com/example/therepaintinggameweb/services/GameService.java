package com.example.therepaintinggameweb.services;

import com.example.therepaintinggameweb.dtos.requests.GameStepRequestDTO;
import com.example.therepaintinggameweb.dtos.requests.game.GameStartRequestDTO;
import com.example.therepaintinggameweb.dtos.responses.game.GameStartResponseDTO;
import com.example.therepaintinggameweb.dtos.responses.game.GameStepResponseDTO;
import com.example.therepaintinggameweb.entities.Game;
import com.example.therepaintinggameweb.entities.GameStory;
import com.example.therepaintinggameweb.entities.Palettes;
import com.example.therepaintinggameweb.exceptions.*;
import com.example.therepaintinggameweb.logic.Color;
import com.example.therepaintinggameweb.logic.GameStatus;
import com.example.therepaintinggameweb.logic.GameWrapper;
import com.example.therepaintinggameweb.logic.GameWrapperFactory;
import com.example.therepaintinggameweb.repos.PalettesRepo;
import com.example.therepaintinggameweb.repos.UserRepo;
import com.example.therepaintinggameweb.utils.GameSessionManager;
import com.example.therepaintinggameweb.utils.UserUtils;
import com.nimbusds.jose.shaded.gson.Gson;
import lombok.Data;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.*;

@Data
public abstract class GameService {
    protected final PalettesRepo palettesRepo;
    protected final ModelMapper modelMapper;
    protected final Gson gson;
    protected final GameWrapperFactory gameWrapperFactory;
    protected final GameSessionManager gameSessionManager;
    protected final UserRepo userRepo;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public GameService(PalettesRepo palettesRepo, ModelMapper modelMapper, Gson gson, GameWrapperFactory gameWrapperFactory, GameSessionManager gameSessionManager, UserRepo userRepo) {
        this.palettesRepo = palettesRepo;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.gameWrapperFactory = gameWrapperFactory;
        this.gameSessionManager = gameSessionManager;
        this.userRepo = userRepo;
        UserUtils.setUserRepo(userRepo);
    }

    public GameStartResponseDTO startGame(GameStartRequestDTO gameStartRequestDTO) {

        if (gameStartRequestDTO.getMaxRounds() < 1 || gameStartRequestDTO.getFieldSize() < 2 || gameStartRequestDTO.getFieldSize() > 101)
            throw new BadGameParamsException();

        Color[] palettes = palettesSetup(gameStartRequestDTO.getPaletteId());

        GameWrapper gameWrapper = gameWrapperFactory.createGameWrapper(palettes,
                gameStartRequestDTO.getFieldSize(), gameStartRequestDTO.getMaxRounds());

        String gameId = UUID.randomUUID().toString();

        if (!UserUtils.isGuest()) {
            Game game = gameSave(gameId, gameWrapper, gameStartRequestDTO);
            String userId = UserUtils.getCurrentUserId();
            if (gameSessionManager.getSessions().containsKey(userId)) {
                GameSessionManager.GameSession gameSession = gameSessionManager.removeSession(userId);
                Game oldGame = checkGameExist(gameSession.getGameId());
                endGame(gameSession.getGameWrapper(), oldGame);
            };
            gameSessionManager.startNewSession(gameId, userId, gameWrapper);
        } else {
            gameSessionManager.startNewSession(gameId, gameId, gameWrapper);
        }

        GameStartResponseDTO gameStartResponseDTO = modelMapper.map(gameWrapper, GameStartResponseDTO.class);
        gameStartResponseDTO.setGameId(gameId);

        return gameStartResponseDTO;
    }

    protected abstract Game gameSave(String gameId, GameWrapper gameWrapper, GameStartRequestDTO gameStartRequestDTO);

    public GameStepResponseDTO stepGame(GameStepRequestDTO gameStepRequestDTO) {
        String gameId = gameStepRequestDTO.getGameId();
        int colorId = gameStepRequestDTO.getColorId();

        if (colorId < 0 || colorId > 6)
            throw new ColorNotExistException();

        if (gameId == null)
            throw new GameNotFoundException();

        GameWrapper gameWrapper;

        try {
            if (!UserUtils.isGuest()) {
                String userId = UserUtils.getCurrentUserId();
                GameSessionManager.GameSession gameSession = gameSessionManager.getSessions().get(userId);
                gameWrapper = gameSession.getGameWrapper();
                if (Objects.equals(gameSession.getGameId(), gameId))
                    gameSessionManager.restartSession(userId);
                else
                    throw new GameIsEndException();
            } else {
                gameSessionManager.restartSession(gameId);
                GameSessionManager.GameSession gameSession = gameSessionManager.getSessions().get(gameId);
                gameWrapper = gameSession.getGameWrapper();
            }
        } catch (Exception e) {
            throw new GameIsEndException();
        }

        if (gameWrapper.colorRepeated(colorId))
            throw new ColorRepeatedException();

        gameWrapper.step(colorId);
        gameWrapper.updateState();

        GameStepResponseDTO gameStepResponseDTO = modelMapper.map(gameWrapper, GameStepResponseDTO.class);
        gameStepResponseDTO.setGameId(gameId);
        gameStepResponseDTO.setMap(gameWrapper.getMap());
        gameStepResponseDTO.setEnd(gameWrapper.getGameStatus() != GameStatus.PLAYING);

        if (gameWrapper.getGameStatus() != GameStatus.PLAYING) {
            if (!UserUtils.isGuest()) {
                String userId = UserUtils.getCurrentUserId();
                gameSessionManager.endSession(userId);
            } else {
                gameSessionManager.endSession(gameId);
            }
        }

        if (!UserUtils.isGuest()) {
            Game game = checkGameExist(gameId);
            gameStorySave(game, gameId, gameWrapper, gameStepRequestDTO);

            if (gameWrapper.getGameStatus() != GameStatus.PLAYING)
                endGame(gameWrapper, game);
        }

        return gameStepResponseDTO;
    }

    protected abstract Game checkGameExist(String gameId);

    protected abstract GameStory gameStorySave(Game game, String gameId, GameWrapper gameWrapper, GameStepRequestDTO gameStepRequestDTO);

    protected abstract void endGame(GameWrapper gameWrapper, Game game);

    protected abstract Page<Game> userGames(PageRequest pageRequest, String userId);

    protected Color[] palettesSetup(Long paletteId) {
        Color[] palettes = null;

        Optional<Palettes> palettesOptional = palettesRepo.findById(paletteId);
        if (paletteId != 0 && palettesOptional.isPresent()) {
            try {
                palettes = new Color[6];
                for (int i = 0; i < 6; i++) {
                    JSONParser jsonParser = new JSONParser(palettesOptional.get().getPalette());
                    LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) jsonParser.parseArray().get(i);
                    palettes[i] = new Color(Integer.parseInt(linkedHashMap.get("id").toString()),
                            linkedHashMap.get("hexCode").toString());
                }
            } catch (ParseException e) {
                throw new PaletteNotFoundException();
            }
        }

        return palettes;
    }
}
