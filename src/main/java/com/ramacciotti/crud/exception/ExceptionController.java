package com.ramacciotti.crud.exception;

import com.ramacciotti.crud.dto.ExceptionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleItemNotFoundException(ItemNotFoundException exception) {

        ExceptionDTO error = new ExceptionDTO(HttpStatus.NOT_FOUND.value(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @ExceptionHandler(NullFieldException.class)
    public ResponseEntity<ExceptionDTO> handleNullFieldException(NullFieldException exception) {

        ExceptionDTO error = new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

    }

}
