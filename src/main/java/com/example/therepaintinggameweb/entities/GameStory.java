package com.example.therepaintinggameweb.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_story")
public class GameStory {

    @EmbeddedId
    private GameStoryId id;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "round")
    private int round;

    @Column(name = "chosen_color")
    private int chosenColor;

    @Column(name = "step_time")
    private LocalDateTime stepTime;

    @PrePersist
    public void prePersist() {
        this.stepTime = LocalDateTime.now();
    }
}

