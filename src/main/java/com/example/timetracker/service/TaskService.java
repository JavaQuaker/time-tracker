package com.example.timetracker.service;

import com.example.timetracker.dto.TaskCreateDTO;
import com.example.timetracker.dto.TaskDTO;
import com.example.timetracker.dto.TaskParamsDTO;
import com.example.timetracker.dto.TaskUpdateDTO;
import com.example.timetracker.exception.ResourceNotFoundException;
import com.example.timetracker.mapper.TaskMapper;
import com.example.timetracker.model.Task;
import com.example.timetracker.repository.TaskRepository;

import com.example.timetracker.repository.UserRepository;
import com.example.timetracker.specification.TaskSpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskSpecification specBuilder;



//    public List<TaskDTO> getAll() {
//        List<Task> task = taskRepository.findAll();
//        return task.stream()
//                .map(taskMapper::map)
//                .toList();
//    }
    public List<TaskDTO> getAll(TaskParamsDTO params) {
        var spec = specBuilder.build(params);
        var tasks = taskRepository.findAll(spec);
        return tasks.stream()
                .map(taskMapper::map)
                .toList();
    }

    public TaskDTO findById(long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        TaskDTO dto = taskMapper.map(task);
        return dto;
    }
    public TaskDTO create(TaskCreateDTO taskData) {
        Task task = taskMapper.map(taskData);
        Long assigneeId = taskData.getAssigneeId();
        if (assigneeId != null) {
            var assignee = userRepository.findById(assigneeId).orElse(null);
            task.setAssignee(assignee);
        }
        taskRepository.save(task);
        TaskDTO dto = taskMapper.map(task);
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
