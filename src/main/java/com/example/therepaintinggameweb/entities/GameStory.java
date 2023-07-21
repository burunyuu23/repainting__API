package com.example.therepaintinggameweb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class GameStory {
    @EmbeddedId
    private GameStoryId id;

    @Column(name = "round_map", columnDefinition = "::json")
    private String roundMap;

    @Column(name = "chosen_color")
    private int chosenColor;

    @Column(name = "step_time")
    private LocalDateTime stepTime;

    public GameStory() {
        this.stepTime = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.stepTime = LocalDateTime.now();
    }
}
