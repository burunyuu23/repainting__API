package com.example.therepaintinggameweb.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameStoryId implements Serializable {

    @Column(name = "game_id")
    private String gameId;

    @Column(name = "round")
    private int round;
}

