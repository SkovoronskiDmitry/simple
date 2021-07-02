package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dto.ErrorDto;
import com.mastery.java.task.simple.rest.exception.EmployeeNotFoundException;
import com.mastery.java.task.simple.service.datatime.DateTimeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
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

    @ExceptionHandler(value = {EmployeeNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleEmployeeNotFoundException(final EmployeeNotFoundException ex) {
        LOGGER.error("Not found", ex);
        return createErrorDto(HttpStatus.NOT_FOUND, "Employee not found."
        );
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBedRequest(final IllegalArgumentException ex) {
        LOGGER.error("Bad Request", ex);
        return createErrorDto(HttpStatus.BAD_REQUEST, "Bad Request — The request was invalid or cannot be served."
        );
    }

    @ExceptionHandler(value = {DataAccessException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleDataAccessException(final DataAccessException ex) {
        LOGGER.error("Internal Server Error", ex);
        return createErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while accessing the database."
        );
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto uncaughtException(final Throwable ex) {
        LOGGER.error("Unhandled exception caught!", ex);
        return createErrorDto(HttpStatus.INTERNAL_SERVER_ERROR,
                "API developers should avoid this error. If an error occurs in the global catch blog, the stacktrace should be logged and not returned as response."
        );
    }

    private ErrorDto createErrorDto(final HttpStatus status, final String errorMessage) {
        return new ErrorDto(
                dateTimeService.getCurrentDate(),
                status,
                Collections.singletonList(errorMessage)
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

        final ErrorDto errorDto =
                new ErrorDto(dateTimeService.getCurrentDate(), status, errors);

        return new ResponseEntity<>(errorDto, headers, status);
    }
}