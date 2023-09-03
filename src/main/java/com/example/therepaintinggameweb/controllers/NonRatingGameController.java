package com.example.therepaintinggameweb.controllers;

import com.example.therepaintinggameweb.dtos.requests.GameStepRequestDTO;
import com.example.therepaintinggameweb.dtos.requests.game.GameStartRequestDTO;
import com.example.therepaintinggameweb.dtos.responses.game.GameStartResponseDTO;
import com.example.therepaintinggameweb.dtos.responses.game.GameStepResponseDTO;
import com.example.therepaintinggameweb.dtos.responses.game.NonRatingGameResponseDTO;
import com.example.therepaintinggameweb.entities.Game;
import com.example.therepaintinggameweb.services.NonRatingGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
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
    @Operation(summary = "Return game_id story")
    @ApiResponse(
            responseCode = "200",
            description = "Operation succeeded",
            content = @Content(
                    schema = @Schema(
                            implementation = NonRatingGameResponseDTO.class)))
    public ResponseEntity<NonRatingGameResponseDTO> resultGame(@PathVariable String game_id) {
        return ResponseEntity.ok(service.resultGame(game_id));
    }

    @GetMapping("/user/{user_id}")
    @Operation(summary = "Return user_id games stories")
    @ApiResponse(
            responseCode = "200",
            description = "Operation succeeded",
            content = @Content(
                    schema = @Schema(
                            implementation = NonRatingGameResponseDTO.class)))
    public Page<Game> userGames(@PathVariable String user_id,
                                @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                @RequestParam(value = "limit", defaultValue = "10") @Min(1) @Max(100) Integer limit,
                                @RequestParam(value = "sort", defaultValue = "startTime") String sortField) {
        return service.userGames(PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField)), user_id);
    }
}
