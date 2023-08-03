package com.example.therepaintinggameweb.dtos.responses.game;

import com.example.therepaintinggameweb.logic.CellWrapper;
import com.example.therepaintinggameweb.logic.Color;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public abstract class GameStartResponseBaseDTO {
    private String gameId;
    protected CellWrapper[][] map;
    protected Color[] colors;
    protected long maxRounds = 22;
    protected long fieldSize = 12;
    @Getter(value = AccessLevel.PROTECTED)
    protected LocalDateTime startTime;

    public GameStartResponseBaseDTO() {
        this.startTime = LocalDateTime.now();
    }
}

