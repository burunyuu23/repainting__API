package com.example.therepaintinggameweb.services;

import com.example.therepaintinggameweb.dtos.requests.GameStepRequestDTO;
import com.example.therepaintinggameweb.dtos.requests.game.GameStartRequestDTO;
import com.example.therepaintinggameweb.entities.*;
import com.example.therepaintinggameweb.logic.GameWrapper;
import com.example.therepaintinggameweb.logic.GameWrapperFactory;
import com.example.therepaintinggameweb.repos.PalettesRepo;
import com.example.therepaintinggameweb.repos.UserRepo;
import com.example.therepaintinggameweb.utils.GameSessionManager;
import com.nimbusds.jose.shaded.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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
    protected Page<Game> userGames(PageRequest pageRequest, String userId) {
        return null;
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
