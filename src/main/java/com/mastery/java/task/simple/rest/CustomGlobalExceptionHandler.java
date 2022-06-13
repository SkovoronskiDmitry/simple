package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dto.ErrorDto;
import com.mastery.java.task.simple.service.datatime.DateTimeService;
import com.mastery.java.task.simple.service.exception.EmployeeServiceException;
import com.mastery.java.task.simple.service.exception.EmployeeServiceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CustomGlobalExceptionHandler {

    private final DateTimeService dateTimeService;

    @ExceptionHandler(value = {EmployeeServiceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleEmployeeServiceNotFoundException(final EmployeeServiceNotFoundException ex) {
        log.error("Not found", ex);
        return createErrorDto(HttpStatus.NOT_FOUND, "Employee not found."
        );
    }

    @ExceptionHandler(value = {EmployeeServiceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleEmployeeServiceException(final EmployeeServiceException ex) {
        log.error("Something wrong with EmployeeService", ex);
        return createErrorDto(HttpStatus.NOT_FOUND, "Service can't sorted out this issue."
        );
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleBedRequest(final IllegalArgumentException ex) {
        log.error("Bad Request", ex);
        return createErrorDto(HttpStatus.BAD_REQUEST, "Bad Request — The request was invalid or cannot be served."
        );
    }

    @ExceptionHandler(value = {DataAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleDataAccessException(final DataAccessException ex) {
        log.error("Internal Server Error", ex);
        return createErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while accessing the database."
        );
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto uncaughtException(final Throwable ex) {
        log.error("Unhandled exception caught!", ex);
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