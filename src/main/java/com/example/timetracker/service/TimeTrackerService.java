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




//    public TimeTrackerDTO create(TimeTrackerCreateDTO trackerData) {
//        TimeTracker timeTracker = timeTrackerMapper.map(trackerData);
////        timeTracker.setStartTime(timeTracker.getStartTime());
//        timeTracker.setStartTime(startTime.startStopTime());
//        System.out.println("startTime " + startTime.startStopTime());
//        timeTrackerRepository.save(timeTracker);
//        TimeTrackerDTO timeTrackerDTO = timeTrackerMapper.map(timeTracker);
//        System.out.println("timeTrackerDTO.getNameTask() " + timeTrackerDTO.getNameTask());
//        System.out.println("timeTrackerDTO.getId() " + timeTrackerDTO.getId());
//        System.out.println("timeTrackerDTO.getStartTime() " + timeTrackerDTO.getStartTime());
//        return timeTrackerDTO;
//    }
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
//    public TimeTrackerDTO update(TimeTrackerUpdateDTO dto, long id) {
//        var timeTracker = timeTrackerRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("TimeTracker not found"));
////        timeTracker.setStartTime(timeTracker.getStopTime());
//        timeTracker.setStopTime(startTime.startStopTime());
//        System.out.println("stopTime " + startTime.startStopTime());
//        timeTrackerMapper.update(dto, timeTracker);
//        timeTrackerRepository.save(timeTracker);
//        TimeTrackerDTO timeTrackerDTO = timeTrackerMapper.map(timeTracker);
//        return timeTrackerDTO;
//    }
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
}
