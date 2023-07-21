package com.example.therepaintinggameweb.exceptions;

import org.springframework.http.HttpStatus;

public class PaletteNotFoundException extends AppException{
    public PaletteNotFoundException() {
        super("Palette have strange format", HttpStatus.NOT_FOUND);
    }
}
