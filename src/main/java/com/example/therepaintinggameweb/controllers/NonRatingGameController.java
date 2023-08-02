package com.example.therepaintinggameweb.controllers;

import com.example.therepaintinggameweb.dtos.requests.GameStepRequestDTO;
import com.example.therepaintinggameweb.dtos.requests.game.GameStartRequestDTO;
import com.example.therepaintinggameweb.dtos.responses.game.GameStartResponseDTO;
import com.example.therepaintinggameweb.dtos.responses.game.GameStepResponseDTO;
import com.example.therepaintinggameweb.dtos.responses.game.NonRatingGameResponseDTO;
import com.example.therepaintinggameweb.services.NonRatingGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/game")
@Data
@Tag(name = "Non rating game", description = "Just play for fun logic")
public class NonRatingGameController {
    private final NonRatingGameService service;

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

    @GetMapping("/{game_id}")
    @Operation(summary = "Return user_id game story")
    @ApiResponse(
            responseCode = "200",
            description = "Operation succeeded",
            content = @Content(
                    schema = @Schema(
                            implementation = NonRatingGameResponseDTO.class)))
    public ResponseEntity<NonRatingGameResponseDTO> resultGame(@PathVariable String game_id) {
        return ResponseEntity.ok(service.resultGame(game_id));
    }
}
