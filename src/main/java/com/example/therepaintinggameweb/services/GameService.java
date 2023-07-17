package com.example.therepaintinggameweb.services;

import com.example.therepaintinggameweb.dtos.responses.GameStartResponseDTO;
import com.example.therepaintinggameweb.dtos.responses.GameStepResponseDTO;
import com.example.therepaintinggameweb.logic.GameWrapper;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Data
public class GameService {
    private final ModelMapper modelMapper;
    private final GameWrapper gameWrapper;

    public GameStartResponseDTO getGame() {
        gameWrapper.restart();
        return modelMapper.map(gameWrapper, GameStartResponseDTO.class);
    }

    public GameStepResponseDTO stepGame(int colorId) {
        gameWrapper.step(colorId);
        gameWrapper.updateState();
        return modelMapper.map(gameWrapper, GameStepResponseDTO.class);
    }
}
