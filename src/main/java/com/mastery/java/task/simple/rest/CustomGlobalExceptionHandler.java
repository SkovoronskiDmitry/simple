package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dao.exception.EmployeeDaoException;
import com.mastery.java.task.simple.dto.EntityForExceptionHandler;
import com.mastery.java.task.simple.service.DateTimeService;
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
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

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
                Collections.singletonList("Employee not found, may be Employee with such ID didn't exist")
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
                Collections.singletonList("Check request")
        );
    }

    @ExceptionHandler(value = {
            Throwable.class,
            DataAccessException.class,
            SQLException.class,
            EmployeeDaoException.class
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public EntityForExceptionHandler uncaughtException(final Throwable ex) {
        LOGGER.error("Unhandled exception caught!", ex);
        return new EntityForExceptionHandler(
                dateTimeService.getCurrentDate(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                Collections.singletonList("Unhandled exception caught!")
        );
    }

    @Override
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