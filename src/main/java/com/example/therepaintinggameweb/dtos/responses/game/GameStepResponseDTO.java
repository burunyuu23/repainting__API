package com.example.therepaintinggameweb.dtos.responses.game;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class GameStepResponseDTO extends GameStartResponseBaseDTO{
    protected int currentRound;
    private boolean isEnd;
    private LocalDateTime stepTime;

    public GameStepResponseDTO() {
        this.stepTime = LocalDateTime.now();
    }
}
