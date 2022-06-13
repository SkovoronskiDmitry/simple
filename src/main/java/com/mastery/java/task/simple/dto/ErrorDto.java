package com.mastery.java.task.simple.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Value
@RequiredArgsConstructor
public final class ErrorDto {

    Date date;

    HttpStatus httpStatus;

    List<String> error;
}
