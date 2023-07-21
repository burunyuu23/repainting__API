package com.example.therepaintinggameweb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "non_rating_game_story")
@Data
public class NonRatingGameStory extends GameStory {
    @JsonIgnore
    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    private NonRatingGame game;
}

