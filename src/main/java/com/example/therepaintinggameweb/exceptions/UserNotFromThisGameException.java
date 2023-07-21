package com.example.therepaintinggameweb.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFromThisGameException extends AppException{
    public UserNotFromThisGameException() {
        super("You can't do step, cause it's not your game!", HttpStatus.BAD_REQUEST);
    }
}
