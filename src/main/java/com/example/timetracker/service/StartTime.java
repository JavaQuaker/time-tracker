package com.example.timetracker.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

@Service
public class StartTime {
    Calendar calendar = new GregorianCalendar();

    public Date startStopTime() {
        calendar = Calendar.getInstance();
        return calendar.getTime();
    }
}
