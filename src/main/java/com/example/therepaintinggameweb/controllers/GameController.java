package com.example.therepaintinggameweb.controllers;

import com.example.therepaintinggameweb.dtos.requests.GameStepRequestDTO;
import com.example.therepaintinggameweb.dtos.responses.GameStepResponseDTO;
import com.example.therepaintinggameweb.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repaint-game")
@Data
@Tag(name = "TheRepaintGame", description = "Methods of working with the repaint game")
public class GameController {
    private final GameService service;

    @GetMapping("/start")
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
    public String startGame() {
        return service.getGame().replace("\n", "<br>")
                .toLowerCase()
                .replace("value: ", "<h1 style=\"background-color: ")
                .replace("},", ";\">HI!!!</h1>").replace("}],", ";\">HI!!!</h1>");
    }

    @GetMapping("/step/{colorId}")
    public void stepGame(@PathVariable int colorId) {
//        service
    }
}
