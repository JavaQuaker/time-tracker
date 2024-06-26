package com.example.timetracker.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class TaskParamsDTO {
    private String name;
    private Date startTime;
    private Date stopTime;
    private long assigneeId;
}
