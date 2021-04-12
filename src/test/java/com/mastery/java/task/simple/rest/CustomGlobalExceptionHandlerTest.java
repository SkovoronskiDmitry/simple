package com.mastery.java.task.simple.rest;

import com.mastery.java.task.simple.dto.EntityForExceptionHandler;
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
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.net.BindException;
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
    void handleClassNotFound() {
        //given
        final ClassNotFoundException classNotFoundException = new ClassNotFoundException();
        when(dateTimeService.getCurrentDate()).thenReturn(DATE);

        //when
        final EntityForExceptionHandler entityForExceptionHandler =
                customGlobalExceptionHandler.handleClassNotFound(classNotFoundException);
        //then
        Mockito.verify(dateTimeService).getCurrentDate();
        Assertions.assertNotNull(entityForExceptionHandler);
        assertThat(entityForExceptionHandler.getDate(), CoreMatchers.is(DATE));
        assertThat(entityForExceptionHandler.getHttpStatus(), CoreMatchers.is(HttpStatus.NOT_FOUND));
        assertThat(entityForExceptionHandler.getError(), CoreMatchers.is(Collections.singletonList("Employee not found, may be Employee with such ID didn't exist")));
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
        assertThat(entityForExceptionHandler.getError(), CoreMatchers.is(Collections.singletonList("Check request")));
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
        assertThat(entityForExceptionHandler.getError(), CoreMatchers.is(Collections.singletonList("Unhandled exception caught!")));
    }
}