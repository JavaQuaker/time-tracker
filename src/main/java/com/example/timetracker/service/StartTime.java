package com.example.timetracker.service;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
public class StartTime {


    public LocalDateTime startStopTime() {
        LocalDateTime time = LocalDateTime.now();
        return time;
    }
}
