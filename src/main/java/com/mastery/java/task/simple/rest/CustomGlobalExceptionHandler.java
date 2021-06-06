package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dto.EntityForExceptionHandler;
import com.mastery.java.task.simple.service.DateTimeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

    private final static Logger LOGGER = Logger.getLogger(CustomGlobalExceptionHandler.class);

    private final DateTimeService dateTimeService;

    @Autowired
    public CustomGlobalExceptionHandler(final DateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    @ExceptionHandler(value = {ClassNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public EntityForExceptionHandler handleClassNotFound(final ClassNotFoundException ex) {
        LOGGER.error("Class not found", ex);
        return new EntityForExceptionHandler(
                dateTimeService.getCurrentDate(),
                HttpStatus.NOT_FOUND,
                Collections.singletonList("Not found — There is no resource behind the URI")
        );
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EntityForExceptionHandler handleBedRequest(final IllegalArgumentException ex) {
        LOGGER.error("Check request", ex);
        return new EntityForExceptionHandler(
                dateTimeService.getCurrentDate(),
                HttpStatus.BAD_REQUEST,
                Collections.singletonList("Bad Request — The request was invalid or cannot be served. The exact error should be explained in the error payload. E.g. „The JSON is not valid")
        );
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public EntityForExceptionHandler uncaughtException(final Throwable ex) {
        LOGGER.error("Unhandled exception caught!", ex);
        return new EntityForExceptionHandler(
                dateTimeService.getCurrentDate(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                Collections.singletonList("API developers should avoid this error. If an error occurs in the global catch blog, the stracktrace should be logged and not returned as response.")
        );
    }


    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {

        final List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        final EntityForExceptionHandler entityForExceptionHandler =
                new EntityForExceptionHandler(dateTimeService.getCurrentDate(), status, errors);

        return new ResponseEntity<>(entityForExceptionHandler, headers, status);
    }
}