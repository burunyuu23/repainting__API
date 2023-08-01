package com.example.therepaintinggameweb.services;

import com.example.therepaintinggameweb.dtos.requests.GameStepRequestDTO;
import com.example.therepaintinggameweb.dtos.requests.game.GameStartRequestDTO;
import com.example.therepaintinggameweb.dtos.responses.game.NonRatingGameResponseDTO;
import com.example.therepaintinggameweb.entities.*;
import com.example.therepaintinggameweb.exceptions.GameIsEndException;
import com.example.therepaintinggameweb.exceptions.GameNotFoundException;
import com.example.therepaintinggameweb.exceptions.UserNotFromThisGameException;
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

import java.util.Optional;

@Service
public class NonRatingGameNonAuthService extends GameService{
    public NonRatingGameNonAuthService(PalettesRepo palettesRepo, ModelMapper modelMapper, Gson gson, GameWrapperFactory gameWrapperFactory, GameSessionManager gameSessionManager, UserRepo userRepo) {
        super(palettesRepo, modelMapper, gson, gameWrapperFactory, gameSessionManager, userRepo);
    }

    @Override
    protected Game gameSave(String gameId, GameWrapper gameWrapper, GameStartRequestDTO gameStartRequestDTO) {
        return null;
    }

    @Override
    public void endGame(GameWrapper gameWrapper, Game nonRatingGame) {
    }

    @Override
    protected GameStory gameStorySave(Game nonRatingGame, String gameId, GameWrapper gameWrapper, GameStepRequestDTO gameStepRequestDTO) {
        return null;
    }

    @Override
    protected Game checkGameExist(String gameId) {
        return null;
    }
}
