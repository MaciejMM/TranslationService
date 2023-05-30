package com.example.translations.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException apiRequestException) {
        HttpStatus badRequestStatus = HttpStatus.NOT_FOUND;
        LOGGER.error(apiRequestException.getMessage(), apiRequestException);
        ApiException apiException = new ApiException(
                apiRequestException.getMessage(),
                badRequestStatus,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequestStatus);
    }



}
