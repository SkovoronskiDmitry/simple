package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dto.ErrorDto;
import com.mastery.java.task.simple.service.datatime.DateTimeService;
import com.mastery.java.task.simple.service.exception.EmployeeServiceNotFoundException;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CustomGlobalExceptionHandlerTest {

    private static final Date DATE = new Date();

    @Mock
    private DateTimeService dateTimeService;
    @InjectMocks
    private CustomGlobalExceptionHandler customGlobalExceptionHandler;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        verifyNoInteractions(dateTimeService);
    }

    @Test
    void handleEmployeeServiceNotFoundException() {
        //given
        final EmployeeServiceNotFoundException employeeServiceNotFoundException = new EmployeeServiceNotFoundException("1");
        when(dateTimeService.getCurrentDate()).thenReturn(DATE);

        //when
        final ErrorDto errorDto =
                customGlobalExceptionHandler.handleEmployeeServiceNotFoundException(employeeServiceNotFoundException);
        //then
        Mockito.verify(dateTimeService).getCurrentDate();
        Assertions.assertNotNull(errorDto);
        assertThat(errorDto.getDate(), CoreMatchers.is(DATE));
        assertThat(errorDto.getHttpStatus(), CoreMatchers.is(HttpStatus.NOT_FOUND));
        assertThat(errorDto.getError(), CoreMatchers.is(Collections.singletonList("Employee not found.")));
    }

    @Test
    void testHandleBedRequest() {
        //given
        final IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
        when(dateTimeService.getCurrentDate()).thenReturn(DATE);

        //when
        final ErrorDto errorDto =
                customGlobalExceptionHandler.handleBedRequest(illegalArgumentException);
        //then
        Mockito.verify(dateTimeService).getCurrentDate();
        Assertions.assertNotNull(errorDto);
        assertThat(errorDto.getDate(), CoreMatchers.is(DATE));
        assertThat(errorDto.getHttpStatus(), CoreMatchers.is(HttpStatus.BAD_REQUEST));
        assertThat(errorDto.getError(), CoreMatchers.is(Collections.singletonList("Bad Request — The request was invalid or cannot be served.")));
    }

    @Test
    void testHandleDataAccessException() {
        //given
        final DataAccessException dataAccessException = new DataAccessException("") {};
        when(dateTimeService.getCurrentDate()).thenReturn(DATE);

        //when
        final ErrorDto errorDto =
                customGlobalExceptionHandler.handleDataAccessException(dataAccessException);

        //then
        Mockito.verify(dateTimeService).getCurrentDate();
        Assertions.assertNotNull(errorDto);
        assertThat(errorDto.getDate(), CoreMatchers.is(DATE));
        assertThat(errorDto.getHttpStatus(), CoreMatchers.is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(errorDto.getError(), CoreMatchers.is(Collections.singletonList("An error occurred while accessing the database.")));
    }

    @Test
    void uncaughtException() {
        //given
        final Throwable throwable = new Throwable();
        when(dateTimeService.getCurrentDate()).thenReturn(DATE);

        //when
        final ErrorDto errorDto =
                customGlobalExceptionHandler.uncaughtException(throwable);

        //then
        Mockito.verify(dateTimeService).getCurrentDate();
        Assertions.assertNotNull(errorDto);
        assertThat(errorDto.getDate(), CoreMatchers.is(DATE));
        assertThat(errorDto.getHttpStatus(), CoreMatchers.is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(errorDto.getError(), CoreMatchers.is(Collections.singletonList("API developers should avoid this error. If an error occurs in the global catch blog, the stacktrace should be logged and not returned as response.")));
    }
}