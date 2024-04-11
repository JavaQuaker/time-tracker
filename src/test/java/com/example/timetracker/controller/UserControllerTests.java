package com.example.timetracker.controller;

import com.example.timetracker.dto.UserCreateDTO;

import com.example.timetracker.dto.UserUpdateDto;
import com.example.timetracker.mapper.UserMapper;
import com.example.timetracker.model.User;
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

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private Faker faker;
    @Autowired
    private ModelGenerator modelGenerator;
    private User testUser;
    @BeforeEach
    public void setUp() {
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        System.out.println("testUser.getFirstName " + testUser.getFirstName());
        System.out.println("testUser.getLastName " + testUser.getLastName());
        System.out.println("testUser.getEmail " + testUser.getEmail());
        userRepository.save(testUser);
    }
    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());

    }

   @Test
    public void testCreateUser() throws Exception {
       UserCreateDTO dto = new UserCreateDTO();
       dto.setFirstName(faker.name().name());
       dto.setLastName(faker.name().name());

       var request = post("/api/users")
               .contentType(MediaType.APPLICATION_JSON)
               .content(om.writeValueAsString(dto));
       mockMvc.perform(request)
               .andExpect(status().isCreated());
   }
   @Test
    public void testShowUser() throws Exception {
       MockHttpServletRequestBuilder request = get("/api/users/{id}", testUser.getId());
       MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String body = result.getResponse().getContentAsString();
        assertThatJson(body).and(
                v -> v.node("firstName").isEqualTo(testUser.getFirstName()),
                v -> v.node("lastName").isEqualTo(testUser.getLastName())
        );
   }
   @Test
    public void testUpdateUser() throws Exception {
       UserUpdateDto dto = new UserUpdateDto();
       dto.setFirstName(JsonNullable.of(faker.name().name()));
       dto.setLastName(JsonNullable.of(faker.name().name()));
       MockHttpServletRequestBuilder request = put("/api/users/{id}", testUser.getId())
               .contentType(MediaType.APPLICATION_JSON)
               .content(om.writeValueAsString(dto));
       mockMvc.perform(request)
               .andExpect(status().isOk());
       testUser = userRepository.findById(testUser.getId()).get();
       System.out.println("testUser.getId " + testUser.getId());
       System.out.println("testUser.getFirstNameSSS " + testUser.getFirstName());
       System.out.println("testUser.getLastNameSSS " + testUser.getLastName());
       System.out.println("dto.FirstName " + dto.getFirstName().get());
       System.out.println("dto.LastName " + dto.getLastName().get());
       assertThat(testUser.getFirstName()).isEqualTo(dto.getFirstName().get());
       assertThat(testUser.getLastName()).isEqualTo(dto.getLastName().get());
   }
   @Test
    public void testDeleteUser() throws Exception {
       MockHttpServletRequestBuilder request = delete("/api/users/{id}", testUser.getId());
       mockMvc.perform(request)
               .andExpect(status().isNoContent());
       assertThat(userRepository.existsById(testUser.getId())).isFalse();
   }
}
