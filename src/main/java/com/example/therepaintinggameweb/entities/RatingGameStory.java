package com.example.therepaintinggameweb.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rating_game_story")
@Data
public class RatingGameStory extends GameStory{
    @JsonIgnore
    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    private RatingGame game;

    @Column(name = "rating_change")
    private int ratingChange;
}



