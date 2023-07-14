package com.example.therepaintinggameweb.dtos.responses;

import com.example.therepaintinggameweb.logic.GameStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GameStepResponseDTO extends GameStartResponseDTO{
    private int currentRound;
}
