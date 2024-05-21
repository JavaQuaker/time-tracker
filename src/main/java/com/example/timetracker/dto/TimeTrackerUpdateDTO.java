package com.example.timetracker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class TimeTrackerUpdateDTO {
    @NotNull
    private JsonNullable<Date> stopTime;
}
