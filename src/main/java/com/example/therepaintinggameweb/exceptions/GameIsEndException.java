package com.example.therepaintinggameweb.exceptions;

import org.springframework.http.HttpStatus;

public class GameIsEndException extends AppException {
    public GameIsEndException() {
        super("This game is already over!", HttpStatus.BAD_REQUEST);
    }
}
