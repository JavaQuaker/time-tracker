package com.example.timetracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;


@Getter
@Setter
public class TimeTrackerDTO {
    private long id;
    private String nameTask;
    private Date startTime;
    private Date stopTime;
    private long nameTimeTrackerId;
}
