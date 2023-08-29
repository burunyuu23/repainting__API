package com.example.therepaintinggameweb.exceptions;

import org.springframework.http.HttpStatus;

public class GameNotFoundException extends AppException{
    public GameNotFoundException() {
        super("Game with current gameID not found", HttpStatus.NOT_FOUND);
    }
}