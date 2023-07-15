package com.example.therepaintinggameweb.controllers;

import com.example.therepaintinggameweb.dtos.requests.GameStepRequestDTO;
import com.example.therepaintinggameweb.dtos.responses.GameStartResponseDTO;
import com.example.therepaintinggameweb.dtos.responses.GameStepResponseDTO;
import com.example.therepaintinggameweb.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/repaint-game")
@Data
@Tag(name = "TheRepaintGame", description = "Methods of working with the repaint game")
public class GameController {
    private final GameService service;

    @GetMapping("/game/start")
    @Operation(summary = "Started game")
    @ApiResponse(
            responseCode = "200",
            description = "Operation succeeded",
            content = @Content(
                    schema = @Schema(
                            implementation = GameStepResponseDTO.class),
                    examples = @ExampleObject(value = """
                            {"map": [
                            [{captured: true; value: 3}, {captured: false; value: 2}, {captured: false; value: 6}, {captured: false; value: 3}, {captured: false; value: 1}, {captured: false; value: 6}, {captured: false; value: 2}, {captured: false; value: 4}, {captured: false; value: 2}, {captured: false; value: 7}, {captured: false; value: 5}, {captured: false; value: 3}], 
                            [{captured: false; value: 7}, {captured: false; value: 5}, {captured: false; value: 7}, {captured: false; value: 2}, {captured: false; value: 4}, {captured: false; value: 7}, {captured: false; value: 3}, {captured: false; value: 7}, {captured: false; value: 2}, {captured: false; value: 1}, {captured: false; value: 3}, {captured: false; value: 4}], 
                            [{captured: false; value: 2}, {captured: false; value: 2}, {captured: false; value: 6}, {captured: false; value: 7}, {captured: false; value: 4}, {captured: false; value: 6}, {captured: false; value: 4}, {captured: false; value: 2}, {captured: false; value: 7}, {captured: false; value: 5}, {captured: false; value: 6}, {captured: false; value: 1}], 
                            [{captured: false; value: 3}, {captured: false; value: 3}, {captured: false; value: 4}, {captured: false; value: 2}, {captured: false; value: 4}, {captured: false; value: 3}, {captured: false; value: 2}, {captured: false; value: 7}, {captured: false; value: 7}, {captured: false; value: 6}, {captured: false; value: 6}, {captured: false; value: 5}], 
                            [{captured: false; value: 7}, {captured: false; value: 7}, {captured: false; value: 3}, {captured: false; value: 7}, {captured: false; value: 1}, {captured: false; value: 1}, {captured: false; value: 5}, {captured: false; value: 5}, {captured: false; value: 4}, {captured: false; value: 4}, {captured: false; value: 2}, {captured: false; value: 6}], 
                            [{captured: false; value: 4}, {captured: false; value: 5}, {captured: false; value: 7}, {captured: false; value: 7}, {captured: false; value: 2}, {captured: false; value: 7}, {captured: false; value: 1}, {captured: false; value: 5}, {captured: false; value: 6}, {captured: false; value: 1}, {captured: false; value: 7}, {captured: false; value: 5}], 
                            [{captured: false; value: 7}, {captured: false; value: 3}, {captured: false; value: 1}, {captured: false; value: 1}, {captured: false; value: 4}, {captured: false; value: 7}, {captured: false; value: 4}, {captured: false; value: 3}, {captured: false; value: 4}, {captured: false; value: 5}, {captured: false; value: 3}, {captured: false; value: 7}], 
                            [{captured: false; value: 4}, {captured: false; value: 5}, {captured: false; value: 6}, {captured: false; value: 3}, {captured: false; value: 6}, {captured: false; value: 4}, {captured: false; value: 3}, {captured: false; value: 5}, {captured: false; value: 6}, {captured: false; value: 2}, {captured: false; value: 7}, {captured: false; value: 7}], 
                            [{captured: false; value: 7}, {captured: false; value: 4}, {captured: false; value: 1}, {captured: false; value: 4}, {captured: false; value: 2}, {captured: false; value: 3}, {captured: false; value: 5}, {captured: false; value: 1}, {captured: false; value: 4}, {captured: false; value: 3}, {captured: false; value: 6}, {captured: false; value: 2}], 
                            [{captured: false; value: 3}, {captured: false; value: 1}, {captured: false; value: 1}, {captured: false; value: 2}, {captured: false; value: 7}, {captured: false; value: 4}, {captured: false; value: 1}, {captured: false; value: 1}, {captured: false; value: 3}, {captured: false; value: 7}, {captured: false; value: 4}, {captured: false; value: 1}], 
                            [{captured: false; value: 5}, {captured: false; value: 4}, {captured: false; value: 3}, {captured: false; value: 4}, {captured: false; value: 4}, {captured: false; value: 6}, {captured: false; value: 4}, {captured: false; value: 4}, {captured: false; value: 7}, {captured: false; value: 1}, {captured: false; value: 6}, {captured: false; value: 6}], 
                            [{captured: false; value: 4}, {captured: false; value: 3}, {captured: false; value: 6}, {captured: false; value: 5}, {captured: false; value: 4}, {captured: false; value: 4}, {captured: false; value: 3}, {captured: false; value: 2}, {captured: false; value: 6}, {captured: false; value: 5}, {captured: false; value: 1}, {captured: false; value: 7}]
                            ]
                            }""")
            ))
    public ResponseEntity<GameStartResponseDTO> startGame() {
        return ResponseEntity.ok(service.getGame());
    }

    @GetMapping("/game/step/{colorId}")
    public ResponseEntity<GameStepResponseDTO> stepGame(@PathVariable int colorId) {
        return ResponseEntity.ok(service.stepGame(colorId));
    }

    @GetMapping("/t")
    public Authentication whoAmI() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context.getAuthentication();
    }

    @GetMapping("/resource")
    public ResponseEntity<String> administratorReadRes(){
        return ResponseEntity.of(Optional.of("Resource for users with fancy access attribute"));
    }
}
