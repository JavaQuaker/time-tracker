package com.example.timetracker.controller;

import com.example.timetracker.dto.TimeTrackerCreateDTO;
import com.example.timetracker.dto.TimeTrackerDTO;
import com.example.timetracker.dto.TimeTrackerUpdateDTO;
import com.example.timetracker.mapper.TimeTrackerMapper;
import com.example.timetracker.repository.TimeTrackerRepository;
import com.example.timetracker.service.TimeTrackerService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/timeTrackers")
public class TimeTrackerController {
    @Autowired
    private TimeTrackerRepository timeTrackerRepository;
    @Autowired
    private TimeTrackerMapper timeTrackerMapper;
    @Autowired
    private TimeTrackerService timeTrackerService;
    @GetMapping(path = "/{id}")
    public TimeTrackerDTO show(@PathVariable long id) {
        return timeTrackerService.findById(id);
    }
    @PostMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public TimeTrackerDTO create(/*@RequestBody TimeTrackerCreateDTO timeData*/@PathVariable long id) {
        return timeTrackerService.create(id);
    }
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    TimeTrackerDTO update(@PathVariable long id) {
        return timeTrackerService.update(id);
    }
}
