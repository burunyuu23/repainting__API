package com.example.therepaintinggameweb.dtos.responses;

import com.example.therepaintinggameweb.logic.GameStatus;
import lombok.Data;

@Data
public class GameStepResponseDTO {
    private GameStartResponseDTO game;
    private int currentRound;
}
