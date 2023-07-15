package com.example.therepaintinggameweb.services;

import com.example.therepaintinggameweb.dtos.responses.GameStartResponseDTO;
import com.example.therepaintinggameweb.dtos.responses.GameStepResponseDTO;
import com.example.therepaintinggameweb.logic.GameWrapper;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

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
