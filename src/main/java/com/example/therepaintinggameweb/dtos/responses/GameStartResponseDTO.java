package com.example.therepaintinggameweb.dtos.responses;

import com.example.therepaintinggameweb.logic.CellWrapper;
import com.example.therepaintinggameweb.logic.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class GameStartResponseDTO {
    private CellWrapper[][] map;
    private Color[] colors;
    private int maxRounds;
    private int fieldSize;
}
