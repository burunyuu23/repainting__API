package com.example.therepaintinggameweb.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class Game {

    @Id
    @Column(name = "game_id")
    private String gameId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "palettes_id")
    private Palettes palettes;

    @Column(name = "map", columnDefinition = "::json")
    private String map;

    @Column(name = "is_end")
    private boolean isEnd;
    @Column(name = "is_win")
    private Boolean isWin;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @PrePersist
    public void prePersist() {
        this.startTime = LocalDateTime.now();
    }
}
