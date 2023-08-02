package com.example.therepaintinggameweb.controllers;

import com.example.therepaintinggameweb.dtos.requests.GameStepRequestDTO;
import com.example.therepaintinggameweb.dtos.requests.game.GameStartRequestDTO;
import com.example.therepaintinggameweb.dtos.responses.game.*;
import com.example.therepaintinggameweb.services.RatingGameService;
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
@RequestMapping("/rating-game")
@Data
@Tag(name = "Rating game", description = "Play game logic for rating earnings")
public class RatingGameController {
    private final RatingGameService service;

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
                            implementation = RatingGameStepResponseDTO.class)))
    public ResponseEntity<RatingGameStepResponseDTO> stepGame(@RequestBody GameStepRequestDTO gameStepRequestDTO) {
        return ResponseEntity.ok(service.stepGame(gameStepRequestDTO));
    }

    @GetMapping("/{game_id}")
    @Operation(summary = "Return user_id game story")
    @ApiResponse(
            responseCode = "200",
            description = "Operation succeeded",
            content = @Content(
                    schema = @Schema(
                            implementation = RatingGameResultResponseDTO.class)))
    public ResponseEntity<RatingGameResultResponseDTO> resultGame(@PathVariable String game_id) {
        return ResponseEntity.ok(service.resultGame(game_id));
    }


}
