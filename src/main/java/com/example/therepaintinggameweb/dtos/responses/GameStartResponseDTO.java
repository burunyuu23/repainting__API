package com.example.therepaintinggameweb.dtos.responses;

import com.example.therepaintinggameweb.logic.CellWrapper;
import com.example.therepaintinggameweb.logic.Color;
import lombok.Data;

@Data
public class GameStartResponseDTO {
    private CellResponseDTO[][] map;
    private Color[] colors;
    private int maxRounds;
}
