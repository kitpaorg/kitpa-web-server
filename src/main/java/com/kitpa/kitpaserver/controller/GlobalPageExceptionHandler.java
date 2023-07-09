package com.kitpa.kitpaserver.controller;

import com.kitpa.kitpaserver.exception.AlreadyExistsException;
import com.kitpa.kitpaserver.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalPageExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFoundException(Exception e) {
        log.warn("error=", e);
        return new ModelAndView("error/4xx");
    }
}
