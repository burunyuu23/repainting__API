package com.example.therepaintinggameweb.exceptions;

import org.springframework.http.HttpStatus;

public class BadGameParamsException extends AppException{
    public BadGameParamsException() {
        super("Please select the correct params", HttpStatus.BAD_REQUEST);
    }
}
