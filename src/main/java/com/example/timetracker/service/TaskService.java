package com.example.timetracker.service;

import com.example.timetracker.dto.TaskCreateDTO;
import com.example.timetracker.dto.TaskDTO;
import com.example.timetracker.dto.TaskUpdateDTO;
import com.example.timetracker.exception.ResourceNotFoundException;
import com.example.timetracker.mapper.TaskMapper;
import com.example.timetracker.model.Task;
import com.example.timetracker.repository.TaskRepository;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskMapper taskMapper;

    public List<TaskDTO> getAll() {
        List<Task> task = taskRepository.findAll();
        return task.stream()
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
        taskRepository.save(task);
        TaskDTO dto = taskMapper.map(task);
        return dto;
    }
    public TaskDTO update(TaskUpdateDTO taskData, long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        return taskMapper.map(task);
    }
    public void delete(long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
    }
}
