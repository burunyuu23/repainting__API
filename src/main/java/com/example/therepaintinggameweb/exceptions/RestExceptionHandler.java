package com.example.therepaintinggameweb.exceptions;
import com.example.therepaintinggameweb.dtos.responses.ErrorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.python.core.PyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.python.core.Py.RuntimeError;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {
    private final ModelMapper modelMapper;

    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<ErrorResponseDTO> handleAppException(AppException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(modelMapper.map(exception, ErrorResponseDTO.class));
    }

    @ExceptionHandler(value = {PyException.class})
    public ResponseEntity<ErrorResponseDTO> handlePyException(PyException exception) {
        return ResponseEntity.status(500)
                .body(modelMapper.map(exception, ErrorResponseDTO.class));
    }

}
