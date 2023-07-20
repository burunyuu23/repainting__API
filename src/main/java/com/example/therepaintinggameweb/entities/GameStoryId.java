package com.example.therepaintinggameweb.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class GameStoryId implements Serializable {

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "round")
    private int round;
}

