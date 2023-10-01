package com.example.therepaintinggameweb.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.example.therepaintinggameweb.dtos.responses.CellResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimbusds.jose.shaded.gson.Gson;

@Data
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Game {
    @JsonIgnore
    @Transient
    protected Gson gson = new Gson();

    @Id
    @Column(name = "game_id")
    protected String gameId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    protected User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "palettes_id")
    protected Palettes palettes;

    @Column(name = "map", columnDefinition = "::json")
    protected String map;

    @Column(name = "is_end")
    protected boolean isEnd;
    @Column(name = "is_win")
    protected Boolean isWin;

    @Column(name = "start_time")
    protected LocalDateTime startTime;

    @PrePersist
    public void prePersist() {
        this.startTime = LocalDateTime.now();
    }

    public CellResponseDTO[][] getMap() {
        return gson.fromJson(this.map, CellResponseDTO[][].class);
    }
}
