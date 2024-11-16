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


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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

        TimeTrackerDTO timeTrackerDTO = timeTrackerMapper.map(timeTracker);

        dto.setTaskDTO(taskDTO);
        dto.setTimeTrackerDTO(timeTrackerDTO);
        return dto;
    }

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

    public List<TaskTimeTrackerDataDto> findByUserId(long id) {
        List<Task> tasks = taskRepository.findAllByAssigneeId(id);
        List<TimeTracker> timeTrackers = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            TimeTracker timeTracker = timeTrackerRepository.findByNameTimeTrackerId(tasks.get(i).getId())
                    .orElseThrow(() -> new ResourceNotFoundException(""));
            timeTrackers.add(timeTracker);
        }
        List<TaskTimeTrackerDataDto> dto = new ArrayList<>();
        List<TaskDTO> taskDTOS = new ArrayList<>();
        List<TimeTrackerDTO> timeTrackerDTOS = new ArrayList<>();

        for (Task task : tasks) {
            TaskDTO taskDTO = taskMapper.map(task);
            taskDTOS.add(taskDTO);
            System.out.println("task " + task.getId() + " " + task.getName());
        }
        for (TimeTracker timeTracker : timeTrackers) {
            TimeTrackerDTO timeTrackerDTO = timeTrackerMapper.map(timeTracker);
            timeTrackerDTOS.add(timeTrackerDTO);
            System.out.println("timeTracker " + timeTracker.getId() + " " + timeTracker.getNameTimeTracker());

        }
        for (int i = 0; i < tasks.size() && i < timeTrackers.size(); i++) {
            TaskTimeTrackerDataDto taskTimeTrackerDataDto = new TaskTimeTrackerDataDto();
            taskTimeTrackerDataDto.setTaskDTO(taskDTOS.get(i));
            taskTimeTrackerDataDto.setTimeTrackerDTO(timeTrackerDTOS.get(i));
            dto.add(taskTimeTrackerDataDto);
            printIdsAndNames(dto);
        }

        return dto;
    }

    //проверить метод поиска задач по конкрентному пользователю в заданный интервал времени.
    //Запрос ничего не возвращает. Метод не выполнятеся. Отладочная печать фиксирует отсутсвие работы метода
    public List<TaskTimeTrackerDataDto> findByTime(TimeRequestDTO data, long id) {
      List<Task> tasks = taskRepository.findAllByAssigneeIdAndCreatedAtAndTimeoutAt(id, data.getCreatedAt(), data.getTimeoutAt());
      for (Task task : tasks) {
          System.out.println("What task consider " + task.getName() + " " + task.getCreatedAt() + " " + task.getTimeoutAt());
      }
      List<TimeTracker> timeTrackers = new ArrayList<>();
      for (int i = 0; i < tasks.size(); i++) {
          TimeTracker timeTracker = timeTrackerRepository.findByNameTimeTrackerId(tasks.get(i).getId())
                  .orElseThrow(()-> new ResourceNotFoundException("task not found"));
          timeTrackers.add(timeTracker);
      }
      List<TaskDTO> taskDTOS = new ArrayList<>();
      List<TimeTrackerDTO> timeTrackerDTOS = new ArrayList<>();
      List<TaskTimeTrackerDataDto> dto = new ArrayList<>();

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
      printIdsAndNames(dto);
      return dto;
    }

//      List<Task> tasks = taskRepository.findAllByAssigneeId(id);
//      List<TimeTracker> timeTrackers = new ArrayList<>();
//      for (int i = 0; i < tasks.size(); i++) {
//          TimeTracker timeTracker = timeTrackerRepository.findByNameTimeTrackerId(tasks.get(i).getId())
//                  .orElseThrow(() -> new ResourceNotFoundException("task not found"));
//          timeTrackers.add(timeTracker);
//      }
//      List<TaskDTO> taskDTOS = new ArrayList<>();
//      List<TimeTrackerDTO> timeTrackerDTOS = new ArrayList<>();
//      List<TaskTimeTrackerDataDto> dto = new ArrayList<>();
//
//      for (Task task : tasks) {
//          TaskDTO taskDTO = taskMapper.map(task);
//          if (taskDTO.getCreatedAt().getDayOfWeek().equals(data.getStart().getDayOfWeek()) &&
//                  taskDTO.getCreatedAt().getDayOfWeek().equals(data.getStop().getDayOfWeek())) {
//              taskDTOS.add(taskDTO);
//          }
//      }
//
//      for (TimeTracker timeTracker : timeTrackers) {
//          TimeTrackerDTO timeTrackerDTO = timeTrackerMapper.map(timeTracker);
//          timeTrackerDTOS.add(timeTrackerDTO);
//      }
//
//      for (int i = 0; i < tasks.size() && i < timeTrackers.size(); i++) {
//          TaskTimeTrackerDataDto taskTimeTrackerDataDto = new TaskTimeTrackerDataDto();
//          taskTimeTrackerDataDto.setTaskDTO(taskDTOS.get(i));
//          taskTimeTrackerDataDto.setTimeTrackerDTO(timeTrackerDTOS.get(i));
//          dto.add(taskTimeTrackerDataDto);
//          }
//        return dto;
//    }
    public void delete(long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        taskRepository.deleteById(id);
    }

    // Метод для проверки содержимого объекта taskTimeTrackersDataDto
    public void printIdsAndNames(List<TaskTimeTrackerDataDto> dto) {

        for (Object item : dto) {
            if (item instanceof TaskDTO) {
                TaskDTO task = (TaskDTO) item;
                System.out.println("TaskDTO - ID: " + task.getId() + ", Name: " + task.getName());
            } else if (item instanceof TaskTimeTrackerDataDto) {
                TaskTimeTrackerDataDto timeTracker = (TaskTimeTrackerDataDto) item;
                System.out.println("TimeTrackerDataDto - ID: " + timeTracker.getTimeTrackerDTO().getId()
                        + ", Name: " + timeTracker.getTimeTrackerDTO().getNameTask());
            } else {
                System.out.println("Unknown type: " + item.getClass().getName());
            }
        }
    }
}
