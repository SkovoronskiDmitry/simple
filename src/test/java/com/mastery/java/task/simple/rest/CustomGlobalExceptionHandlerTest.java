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
        assertThat(entityForExceptionHandler.getError(), CoreMatchers.is(Collections.singletonList("Not found — There is no resource behind the URI")));
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
        assertThat(entityForExceptionHandler.getError(), CoreMatchers.is(Collections.singletonList("Bad Request — The request was invalid or cannot be served. The exact error should be explained in the error payload. E.g. „The JSON is not valid")));
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