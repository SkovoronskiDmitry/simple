package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dto.EntityForExceptionHandler;
import com.mastery.java.task.simple.rest.exception.EmployeeNotFoundException;
import com.mastery.java.task.simple.service.DateTimeService;
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
    void handleEmployeeNotFoundException() {
        //given
        final EmployeeNotFoundException employeeNotFoundException = new EmployeeNotFoundException(1);
        when(dateTimeService.getCurrentDate()).thenReturn(DATE);

        //when
        final EntityForExceptionHandler entityForExceptionHandler =
                customGlobalExceptionHandler.handleEmployeeNotFoundException(employeeNotFoundException);
        //then
        Mockito.verify(dateTimeService).getCurrentDate();
        Assertions.assertNotNull(entityForExceptionHandler);
        assertThat(entityForExceptionHandler.getDate(), CoreMatchers.is(DATE));
        assertThat(entityForExceptionHandler.getHttpStatus(), CoreMatchers.is(HttpStatus.NOT_FOUND));
        assertThat(entityForExceptionHandler.getError(), CoreMatchers.is(Collections.singletonList("Employee not found.")));
    }

    @Test
    void testHandleBedRequest() {
        //given
        final IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
        when(dateTimeService.getCurrentDate()).thenReturn(DATE);

        //when
        final EntityForExceptionHandler entityForExceptionHandler =
                customGlobalExceptionHandler.handleBedRequest(illegalArgumentException);
        //then
        Mockito.verify(dateTimeService).getCurrentDate();
        Assertions.assertNotNull(entityForExceptionHandler);
        assertThat(entityForExceptionHandler.getDate(), CoreMatchers.is(DATE));
        assertThat(entityForExceptionHandler.getHttpStatus(), CoreMatchers.is(HttpStatus.BAD_REQUEST));
        assertThat(entityForExceptionHandler.getError(), CoreMatchers.is(Collections.singletonList("Bad Request — The request was invalid or cannot be served.")));
    }

    @Test
    void testHandleDataAccessException() {
        //given
        final DataAccessException dataAccessException = new DataAccessException("") {};
        when(dateTimeService.getCurrentDate()).thenReturn(DATE);

        //when
        final EntityForExceptionHandler entityForExceptionHandler =
                customGlobalExceptionHandler.handleDataAccessException(dataAccessException);

        //then
        Mockito.verify(dateTimeService).getCurrentDate();
        Assertions.assertNotNull(entityForExceptionHandler);
        assertThat(entityForExceptionHandler.getDate(), CoreMatchers.is(DATE));
        assertThat(entityForExceptionHandler.getHttpStatus(), CoreMatchers.is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(entityForExceptionHandler.getError(), CoreMatchers.is(Collections.singletonList("An error occurred while accessing the database.")));
    }

    @Test
    void teatHandleNullPointerException() {
        //given
        final NullPointerException nullPointerException = new NullPointerException();
        when(dateTimeService.getCurrentDate()).thenReturn(DATE);

        //when
        final EntityForExceptionHandler entityForExceptionHandler =
                customGlobalExceptionHandler.handleNullPointerException(nullPointerException);

        //then
        Mockito.verify(dateTimeService).getCurrentDate();
        Assertions.assertNotNull(entityForExceptionHandler);
        assertThat(entityForExceptionHandler.getDate(), CoreMatchers.is(DATE));
        assertThat(entityForExceptionHandler.getHttpStatus(), CoreMatchers.is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(entityForExceptionHandler.getError(), CoreMatchers.is(Collections.singletonList("Received null in a case where an object is required.")));
    }

    @Test
    void teatHandleRuntimeException() {
        //given
        final RuntimeException runtimeException = new RuntimeException();
        when(dateTimeService.getCurrentDate()).thenReturn(DATE);

        //when
        final EntityForExceptionHandler entityForExceptionHandler =
                customGlobalExceptionHandler.handleRuntimeException(runtimeException);

        //then
        Mockito.verify(dateTimeService).getCurrentDate();
        Assertions.assertNotNull(entityForExceptionHandler);
        assertThat(entityForExceptionHandler.getDate(), CoreMatchers.is(DATE));
        assertThat(entityForExceptionHandler.getHttpStatus(), CoreMatchers.is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(entityForExceptionHandler.getError(), CoreMatchers.is(Collections.singletonList("Thrown during the normal operation of the JVM.")));
    }

    @Test
    void uncaughtException() {
        //given
        final Throwable throwable = new Throwable();
        when(dateTimeService.getCurrentDate()).thenReturn(DATE);

        //when
        final EntityForExceptionHandler entityForExceptionHandler =
                customGlobalExceptionHandler.uncaughtException(throwable);

        //then
        Mockito.verify(dateTimeService).getCurrentDate();
        Assertions.assertNotNull(entityForExceptionHandler);
        assertThat(entityForExceptionHandler.getDate(), CoreMatchers.is(DATE));
        assertThat(entityForExceptionHandler.getHttpStatus(), CoreMatchers.is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(entityForExceptionHandler.getError(), CoreMatchers.is(Collections.singletonList("API developers should avoid this error. If an error occurs in the global catch blog, the stracktrace should be logged and not returned as response.")));
    }
}