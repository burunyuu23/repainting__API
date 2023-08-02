package com.example.therepaintinggameweb.dtos.requests.game;

import lombok.Data;

@Data
public class GameStartRequestDTO {
    private Long paletteId = 0L;
    private Long fieldSize = 12L;
    private Long maxRounds = 22L;
}
