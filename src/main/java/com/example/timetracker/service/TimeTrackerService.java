package com.example.timetracker.service;

import com.example.timetracker.dto.TimeTrackerCreateDTO;
import com.example.timetracker.dto.TimeTrackerDTO;
import com.example.timetracker.dto.TimeTrackerUpdateDTO;
import com.example.timetracker.exception.ResourceNotFoundException;
import com.example.timetracker.mapper.TimeTrackerMapper;
import com.example.timetracker.model.TimeTracker;
import com.example.timetracker.repository.TimeTrackerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

@Service
@AllArgsConstructor
public class TimeTrackerService {
    @Autowired
    private TimeTrackerRepository timeTrackerRepository;
    @Autowired
    private TimeTrackerMapper timeTrackerMapper;
    private StartTime startTime;
    private Calendar calendar;

    //старт времени отсчета задачи
    public TimeTrackerDTO create(long id) {
        TimeTracker timeTracker = timeTrackerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("timeTracker not found"));
        timeTracker.setStartTime(startTime.startStopTime());
        timeTrackerRepository.save(timeTracker);
        TimeTrackerDTO dto = timeTrackerMapper.map(timeTracker);
        System.out.println("check startTime " + timeTracker.getStartTime());
        return dto;
    }
    public TimeTrackerDTO update(long id) {
        TimeTracker timeTracker = timeTrackerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("timeTracker not found"));
        timeTracker.setStopTime(startTime.startStopTime());
        timeTrackerRepository.save(timeTracker);
        TimeTrackerDTO dto = timeTrackerMapper.map(timeTracker);
        System.out.println("check stopTime " + timeTracker.getStopTime());
        return dto;
    }
    public TimeTrackerDTO findById(long id) {
        TimeTracker timeTracker = timeTrackerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("timeTracker not found"));
        TimeTrackerDTO dto = timeTrackerMapper.map(timeTracker);
        return dto;
    }
    public void delete(long id) {
        var timeTrackers = timeTrackerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("not found"));
        timeTrackerRepository.deleteById(id);
    }
}
