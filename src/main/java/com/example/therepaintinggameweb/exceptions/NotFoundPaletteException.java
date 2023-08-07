package com.example.therepaintinggameweb.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundPaletteException extends AppException {
    public NotFoundPaletteException() {
        super("This palette doesn't exists!", HttpStatus.NOT_FOUND);
    }
}

