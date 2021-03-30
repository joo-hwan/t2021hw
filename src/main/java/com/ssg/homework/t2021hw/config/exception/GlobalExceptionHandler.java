package com.ssg.homework.t2021hw.config.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
Exception 처리를 위한 클래스
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger("businessLog");

    /*
    http 401
     */
    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<String> CustomUnauthorizedExceptionHandler(CustomUnauthorizedException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpStatus.UNAUTHORIZED.name() + " - " + e.getMessage());
    }

    /*
    http 404
     */
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<String> CustomNotFoundExceptionHandler(CustomNotFoundException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatus.NOT_FOUND.name() + " - " + e.getMessage());
    }

    /*
    위 Exception외에 exception 발생 시 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생하였음.");
    }

}
