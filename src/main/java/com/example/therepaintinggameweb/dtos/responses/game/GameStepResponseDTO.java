package com.example.therepaintinggameweb.dtos.responses.game;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GameStepResponseDTO extends GameStartResponseDTO{
    protected int currentRound;
    private boolean isEnd;
}
