package com.example.therepaintinggameweb.exceptions;

import org.springframework.http.HttpStatus;

public class ColorRepeatedException extends AppException {
    public ColorRepeatedException() {
        super("This color is already picked!", HttpStatus.BAD_REQUEST);
    }
}