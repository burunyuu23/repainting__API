package com.example.therepaintinggameweb.dtos.requests.game;

import lombok.Data;

@Data
public class GameStartRequestDTO {
    private Long paletteId;
    private Long fieldSize;
    private Long maxRounds;
}
