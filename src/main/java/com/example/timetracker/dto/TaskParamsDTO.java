package com.example.timetracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskParamsDTO {
    private String name;
    private long assigneeId;
}
