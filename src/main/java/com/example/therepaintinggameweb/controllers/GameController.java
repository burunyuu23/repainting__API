package com.example.therepaintinggameweb.controllers;

import com.example.therepaintinggameweb.services.GameService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public String index() {
        return service.getGame().replace("\n", "<br>");
    }

}
