package com.example.therepaintinggameweb.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;

    @OneToMany(
            mappedBy = "game",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<GameStory> gameStories = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "palettes_id")
    private Palettes palettes;

    @Column(name = "map", columnDefinition = "jsonb")
    private String map;

    @Column(name = "is_rating")
    private boolean isRating;
    @Column(name = "is_end")
    private boolean isEnd;
    @Column(name = "is_win")
    private Boolean isWin;
    @Column(name = "rating")
    private Integer rating;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @PrePersist
    public void prePersist() {
        this.startTime = LocalDateTime.now();
    }
}

