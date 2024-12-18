package com.example.timetracker.controller;

import com.example.timetracker.dto.*;
import com.example.timetracker.exception.ResourceNotFoundException;
import com.example.timetracker.mapper.TaskMapper;

import com.example.timetracker.repository.TaskRepository;
import com.example.timetracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskService taskService;
    private TaskParamsDTO params;
    @Operation(summary = "Get the list all tasks")
    @ApiResponse(responseCode = "200", description = "List of all tasks",
    content = {@Content(mediaType = "application/json",
    schema = @Schema(implementation = TaskCreateDTO.class))})
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskTimeTrackerDataDto> index(/*TaskParamsDTO params*/) {
        return taskService.getAll();
    }
    @Operation(summary = "Get task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "400", description = "Task not found")
    })
    @GetMapping(path = "show/{id}")
    public TaskTimeTrackerDataDto show(
            @Parameter(description = "id of task to be found")
            @PathVariable long id) {
        return taskService.findById(id);
    }

    @Operation(summary = "Create new task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created"),
            @ApiResponse(responseCode = "400", description = "Task did not create")
    })
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public DataDto create(
            @Parameter(description = "Task data to save")
            @Valid @RequestBody TaskCreateDTO taskData) {
        return taskService.create(taskData);
    }
    @Operation(summary = "Update task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task update"),
            @ApiResponse(responseCode = "400", description = "Task with id not found")
    })
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    TaskDTO update(@RequestBody TaskUpdateDTO taskData, @PathVariable long id) {
        return taskService.update(taskData, id);
    }
    @Operation(summary = "Find by user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User to be found"),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
    @GetMapping(path = "findByUserId/{userId}")
    @ResponseStatus(HttpStatus.OK)
    List<TaskTimeTrackerDataDto> findByUserId(@PathVariable long userId) {
        return taskService.findByUserId(userId);
    }
    /*
    Проверить метод сортировки по выбранной дате. Метод должен возвращать задачи в интервале выбранных
    дат. Метод возвращает 500 (start = NPE) либо 405. Не верно передаю формат времени в запросе???
    */

    @Operation(summary = "Find by tasks between date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task to be found"),
            @ApiResponse(responseCode = "400", description = "Task not found")
    })
    @GetMapping(path = "findByTime/{userId}")
    @ResponseStatus(HttpStatus.OK)
    List<TaskTimeTrackerDataDto> findByTime(@RequestBody TimeRequestDTO dto, @PathVariable long userId) {
        return taskService.findByTime(dto, userId);
    }

    @Operation(summary = "Delete by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task delete"),
            @ApiResponse(responseCode = "400", description = "Task with id not found")
    })
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        taskService.delete(id);
    }
}
