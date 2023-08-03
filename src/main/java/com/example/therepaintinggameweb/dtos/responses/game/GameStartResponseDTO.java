package com.example.therepaintinggameweb.dtos.responses.game;

import com.example.therepaintinggameweb.logic.CellWrapper;
import com.example.therepaintinggameweb.logic.Color;
import jakarta.persistence.PrePersist;
import lombok.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class GameStartResponseDTO extends GameStartResponseBaseDTO {
    @Getter(value = AccessLevel.PUBLIC)
    protected LocalDateTime startTime;

    public GameStartResponseDTO() {
        this.startTime = LocalDateTime.now();
    }
}
