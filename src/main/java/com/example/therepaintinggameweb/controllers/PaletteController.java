package com.example.therepaintinggameweb.controllers;

import com.example.therepaintinggameweb.dtos.requests.game.GameStartRequestDTO;
import com.example.therepaintinggameweb.dtos.responses.game.GameStartResponseDTO;
import com.example.therepaintinggameweb.dtos.responses.palette.GetPaletteResponseDTO;
import com.example.therepaintinggameweb.services.PaletteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/palette")
@Data
@Tag(name = "Palette", description = "Work with palettes!")
public class PaletteController {

    private final PaletteService service;

    @GetMapping("/{id}")
    @Operation(summary = "Get palette by id")
    @ApiResponse(
            responseCode = "200",
            description = "Operation succeeded",
            content = @Content(
                    schema = @Schema(
                            implementation = GameStartResponseDTO.class)))
    public ResponseEntity<GetPaletteResponseDTO> getPalette(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPalette(id));
    }

}
