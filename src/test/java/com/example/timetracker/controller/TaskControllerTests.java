package com.example.timetracker.controller;

import com.example.timetracker.dto.TaskCreateDTO;
import com.example.timetracker.dto.TaskUpdateDTO;
import com.example.timetracker.exception.ResourceNotFoundException;
import com.example.timetracker.mapper.TaskMapper;
import com.example.timetracker.model.Task;
import com.example.timetracker.model.User;
import com.example.timetracker.repository.TaskRepository;
import com.example.timetracker.repository.UserRepository;
import com.example.timetracker.util.ModelGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.assertj.core.api.Assertions.assertThat;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private ModelGenerator modelGenerator;
    @Autowired
    private Faker faker;
    private Task testTask;

    @BeforeEach
    public void setUp() {
        var user = userRepository.findByEmail("qwe@mail.ru")
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        testTask = Instancio.of(modelGenerator.getTaskModel()).create();
        testTask.setAssignee(user);
        taskRepository.save(testTask);
    }
    @Test
    public void testIndexTask() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk());
    }
    @Test
    public void testCreateTask() throws Exception {
        TaskCreateDTO dto = new TaskCreateDTO();
        dto.setAssigneeId(1L);
        dto.setName(faker.lorem().word());
        var request = post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));
        mockMvc.perform(request)
                .andExpect(status().isCreated());
        Task task = taskRepository.findByName((String) dto.getName()).orElseThrow(null);
        assertThat(task).isNotNull();
        assertThat(task.getName()).isEqualTo(dto.getName());
        assertThat(task.getAssignee().getId()).isEqualTo(dto.getAssigneeId());
    }
    @Test
    public void testUpdateTask() throws Exception {
        User user = userRepository.findByEmail("qwe@mail.ru").orElseThrow(null);
        TaskUpdateDTO dto = new TaskUpdateDTO();
        dto.setName(JsonNullable.of("new name"));
        dto.setAssigneeId(JsonNullable.of(user.getId()));

        var request = put("/api/tasks/{id}", testTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));
        mockMvc.perform(request)
                .andExpect(status().isOk());
        assertThat(testTask.getName()).isEqualTo(dto.getName().get());
        assertThat(testTask.getAssignee().getId()).isEqualTo(dto.getAssigneeId().get());
    }
    @Test
    public void testShowTask() throws Exception {
        taskRepository.save(testTask);
        var request = get("/api/tasks/{id}", testTask.getId());
        var result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        var body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                v -> v.node("name").isEqualTo(testTask.getName())

        );
    }
    @Test
    public void testDeleteTask() throws Exception {
        MockHttpServletRequestBuilder request = delete("/api/tasks/{id}", testTask.getId());
        mockMvc.perform(request)
                .andExpect(status().isNoContent());
        assertThat(taskRepository.existsById(testTask.getId())).isFalse();
    }
}
