package com.example.therepaintinggameweb.dtos.responses;

import com.example.therepaintinggameweb.entities.NonRatingGame;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NonRatingGameUserGamesResponseDTO {
    private List<NonRatingGame> games;
}
