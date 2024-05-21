package com.example.timetracker.service;

import com.example.timetracker.dto.*;
import com.example.timetracker.exception.ResourceNotFoundException;
import com.example.timetracker.mapper.TaskMapper;
import com.example.timetracker.mapper.TimeTrackerMapper;
import com.example.timetracker.model.Task;
import com.example.timetracker.model.TimeTracker;
import com.example.timetracker.model.User;
import com.example.timetracker.repository.TaskRepository;

import com.example.timetracker.repository.TimeTrackerRepository;
import com.example.timetracker.repository.UserRepository;
import com.example.timetracker.specification.TaskSpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.*;

@Service
@AllArgsConstructor
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TimeTrackerRepository timeTrackerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskSpecification specBuilder;
    @Autowired
    private TimeTrackerMapper timeTrackerMapper;
    private StartTime startTime;



// фильтрация задач по пользователю. http://localhost:8080/api/tasks?assigneeId=2 выведет все задачи пользователя
// с id=2
//    public List<TaskDTO> getAll(TaskParamsDTO params) {
//        Specification<Task> spec = specBuilder.build(params);
//        List<Task> tasks = taskRepository.findAll(spec);
//        return tasks.stream()
//                .map(taskMapper::map)
//                .toList();
//    }

  public List<TaskTimeTrackerDataDto> getAll() {
      List<Task> tasks = taskRepository.findAll();
      List<TimeTracker> timeTrackers = timeTrackerRepository.findAll();

      List<TaskTimeTrackerDataDto> dto = new ArrayList<>();
      List<TaskDTO> taskDTOS = new ArrayList<>();
      List<TimeTrackerDTO> timeTrackerDTOS = new ArrayList<>();

      for (Task task : tasks) {
       TaskDTO taskDTO = taskMapper.map(task);
       taskDTOS.add(taskDTO);

      }
      for (TimeTracker timeTracker : timeTrackers) {
          TimeTrackerDTO timeTrackerDTO = timeTrackerMapper.map(timeTracker);
          timeTrackerDTOS.add(timeTrackerDTO);
      }
      for (int i = 0; i < tasks.size() && i < timeTrackers.size(); i++) {
          TaskTimeTrackerDataDto taskTimeTrackerDataDto = new TaskTimeTrackerDataDto();
          taskTimeTrackerDataDto.setTaskDTO(taskDTOS.get(i));
          taskTimeTrackerDataDto.setTimeTrackerDTO(timeTrackerDTOS.get(i));
          dto.add(taskTimeTrackerDataDto);
      }

      return dto;
  }

//    public TaskDTO findById(long id) {
//        Task task = taskRepository.findById(id)
//                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));
//        TaskDTO dto = taskMapper.map(task);
//        return dto;
//    }
    public TaskTimeTrackerDataDto findById(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        TaskTimeTrackerDataDto dto = new TaskTimeTrackerDataDto();
        TaskDTO taskDTO = taskMapper.map(task);
        TimeTracker timeTracker = timeTrackerRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("TimeTracker not found"));
//        timeTracker.setStartTime(timeTracker.getStartTime());
//        timeTracker.setStopTime(timeTracker.getStopTime());
        TimeTrackerDTO timeTrackerDTO = timeTrackerMapper.map(timeTracker);

        dto.setTaskDTO(taskDTO);
        dto.setTimeTrackerDTO(timeTrackerDTO);
        return dto;
    }
//    public TaskDTO create(TaskCreateDTO taskData) {
//        Task task = taskMapper.map(taskData);
//        Long assigneeId = taskData.getAssigneeId();
//        if (assigneeId != null) {
//            var assignee = userRepository.findById(assigneeId).orElse(null);
//            task.setAssignee(assignee);
//        }
//        taskRepository.save(task);
//        TaskDTO dto = taskMapper.map(task);
//        return dto;
//    }
    public DataDto create(TaskCreateDTO taskData) {
        Task task = taskMapper.map(taskData);
        Long assigneeId = taskData.getAssigneeId();
        if (assigneeId != null) {
            User assignee = userRepository.findById(assigneeId).orElseThrow(null);
            task.setAssignee(assignee);
        }
        taskRepository.save(task);
        TimeTrackerCreateDTO timeTrackerCreateDTO = new TimeTrackerCreateDTO();
        timeTrackerCreateDTO.setNameTask(taskData.getName());
        timeTrackerCreateDTO.setNameTimeTrackerId(task.getId());
//        timeTrackerCreateDTO.setStartTime(startTime.startStopTime());
//        timeTrackerCreateDTO.setStopTime(startTime.startStopTime());

        TimeTracker timeTracker = timeTrackerMapper.map(timeTrackerCreateDTO);
        timeTrackerRepository.save(timeTracker);
        DataDto dto = new DataDto();
        dto.setTaskData(taskData);
        dto.setTimeData(timeTrackerCreateDTO);
        return dto;
    }
    public TaskDTO update(TaskUpdateDTO taskData, long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        taskMapper.update(taskData, task);
        return taskMapper.map(task);
    }
    public void delete(long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
    }
}
