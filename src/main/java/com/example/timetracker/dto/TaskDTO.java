package com.example.timetracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO {
    private long id;
    private String name;
    private LocalDate createdAt;
    private LocalDate timeoutAt;
    private Long assigneeId;
}
