package com.example.demospringcours.exposition;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class MonControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> gereNoSuchElement(HttpServletRequest request, NoSuchElementException exception) {
        return new ResponseEntity<>("Personne inconnue", HttpStatus.NOT_FOUND);
    }
}
