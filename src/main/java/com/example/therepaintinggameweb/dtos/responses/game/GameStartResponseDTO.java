package com.example.therepaintinggameweb.dtos.responses.game;

import com.example.therepaintinggameweb.logic.CellWrapper;
import com.example.therepaintinggameweb.logic.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class GameStartResponseDTO {
    private String gameId;
    protected CellWrapper[][] map;
    protected Color[] colors;
    protected long maxRounds = 22;
    protected long fieldSize = 12;
}
