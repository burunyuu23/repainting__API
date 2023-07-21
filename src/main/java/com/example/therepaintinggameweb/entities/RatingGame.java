package com.example.therepaintinggameweb.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "rating_game")
@Data
public class RatingGame extends Game{
    @OneToMany(
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<RatingGameStory> gameStories = new ArrayList<>();

    @Column(name = "rating")
    private Long rating;
}

