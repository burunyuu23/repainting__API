package com.example.therepaintinggameweb.exceptions;

import org.springframework.http.HttpStatus;

public class ColorNotExistException extends AppException {
    public ColorNotExistException() {
        super("This color is not exists! Pick from 0 to 6", HttpStatus.BAD_REQUEST);
    }
}
