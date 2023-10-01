package com.example.therepaintinggameweb.dtos.responses.game;

import java.time.LocalDateTime;

import com.example.therepaintinggameweb.entities.Game;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GamesHistoryResponseDTO<T extends Game> {
    private T game;
    private Integer finalRound;
    private LocalDateTime endTime;
}