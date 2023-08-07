package com.example.therepaintinggameweb.dtos.responses.palette;

import com.example.therepaintinggameweb.logic.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPaletteResponseDTO {
    private Color[] palette;
}
