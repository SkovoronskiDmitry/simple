package com.mastery.java.task.simple.service.converter;

import com.mastery.java.task.simple.dto.Employee;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

public class CustomConverterFromIntegerToEmployee implements Converter<String, Employee> {
    @Override
    public Employee convert(String from) {
        String[] data = from.split(",");
        return new Employee(
                Long.parseLong(data[0]),
                data[1],
                data[2],
                data[3],
                Long.parseLong(data[4]),
                data[5],
                LocalDate.parse(data[6]));

    }
}
