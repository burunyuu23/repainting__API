package com.example.therepaintinggameweb.logic;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
public enum GameStatus {
    PLAYING(0),
    LOSE(1),
    WON(2);

    private final int status;

    public static GameStatus getStatus(int status) {
        for (GameStatus gameStatus : GameStatus.values()) {
            if (gameStatus.status == status) {
                return gameStatus;
            }
        }
        return null; // Возвращаем null, если статус не найден
    }
}
