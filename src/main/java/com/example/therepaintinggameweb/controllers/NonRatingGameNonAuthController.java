package com.example.therepaintinggameweb.controllers;

import com.example.therepaintinggameweb.dtos.requests.GameStepRequestDTO;
import com.example.therepaintinggameweb.dtos.requests.game.GameStartRequestDTO;
import com.example.therepaintinggameweb.dtos.responses.game.GameStartResponseDTO;
import com.example.therepaintinggameweb.dtos.responses.game.GameStepResponseDTO;
import com.example.therepaintinggameweb.services.NonRatingGameNonAuthService;
import com.example.therepaintinggameweb.services.NonRatingGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/no-auth")
@Data
@Tag(name = "Non rating game no auth", description = "Play for unauthorized users")
public class NonRatingGameNonAuthController {
    private final NonRatingGameNonAuthService service;

    @PostMapping("/start")
    @Operation(summary = "Started game")
    @ApiResponse(
            responseCode = "200",
            description = "Operation succeeded",
            content = @Content(
                    schema = @Schema(
                            implementation = GameStartResponseDTO.class)))
    public ResponseEntity<GameStartResponseDTO> startGame(@RequestBody GameStartRequestDTO gameStartRequestDTO) {
        return ResponseEntity.ok(service.startGame(gameStartRequestDTO));
    }

    @PostMapping("/step")
    @Operation(summary = "Step game")
    @ApiResponse(
            responseCode = "200",
            description = "Operation succeeded",
            content = @Content(
                    schema = @Schema(
                            implementation = GameStepResponseDTO.class)))
    public ResponseEntity<GameStepResponseDTO> stepGame(@RequestBody GameStepRequestDTO gameStepRequestDTO) {
        return ResponseEntity.ok(service.stepGame(gameStepRequestDTO));
    }
}
