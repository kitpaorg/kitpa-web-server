package com.kitpa.kitpaserver.controller;

import com.kitpa.kitpaserver.exception.AlreadyExistsException;
import com.kitpa.kitpaserver.exception.InvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalApiExceptionHandler {
    @ExceptionHandler({AlreadyExistsException.class, InvalidException.class})
    public ResponseEntity<?> clientError(Exception e) {
        log.error("error", e);
        return ResponseEntity.badRequest().build();
    }
}
