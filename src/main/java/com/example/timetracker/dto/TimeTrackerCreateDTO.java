package com.example.timetracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class TimeTrackerCreateDTO {
//    private long id;
    private String nameTask;
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private long nameTimeTrackerId;
}
