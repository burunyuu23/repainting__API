package com.example.therepaintinggameweb.dtos.responses.game;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RatingGameStepResponseDTO extends GameStepResponseDTO {
    private int ratingChange;
}
