package com.mastery.java.task.simple.service.datatime.impl;

import com.mastery.java.task.simple.service.datatime.DateTimeService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DateTimeServiceImpl implements DateTimeService {

    @Override
    public Date getCurrentDate() {
        return new Date();
    }
}
