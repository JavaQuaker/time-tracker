package com.example.timetracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import java.time.LocalDateTime;
@Getter
@Setter
public class TimeRequestDTO {
    private LocalDateTime createdAt;
    private LocalDateTime timeoutAt;


}
