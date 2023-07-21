package com.example.therepaintinggameweb.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "non_rating_game")
@Data
public class NonRatingGame extends Game {
    @OneToMany(
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<NonRatingGameStory> gameStories = new ArrayList<>();

    @Column(name = "max_rounds")
    private int maxRounds;

}

