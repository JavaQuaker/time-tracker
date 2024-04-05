package com.example.timetracker.controller;

import com.example.timetracker.dto.UserCreateDTO;
import com.example.timetracker.dto.UserDTO;
import com.example.timetracker.dto.UserUpdateDto;
import com.example.timetracker.exception.ResourceNotFoundException;
import com.example.timetracker.mapper.UserMapper;
import com.example.timetracker.repository.UserRepository;
import com.example.timetracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Operation(summary = "Get list of all users")
    @ApiResponse(responseCode = "200", description = "List of all users",
    content = {@Content(mediaType = "application/json",
    schema = @Schema(implementation = UserDTO.class))})
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> index() {
        return userService.getAll();
    }
    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "User not found")})

    @GetMapping(path = "/{id}")
    public UserDTO show(
            @Parameter(description = "id of user to be found")
            @PathVariable long id) {
        return userService.findById(id);
    }
    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "201", description = "User create")
    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(
            @Parameter(description = "User data to save")
            @Valid @RequestBody UserCreateDTO userData) {
        return userService.create(userData);
    }
    @Operation(summary = "Update user by his id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User update"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO update(@RequestBody UserUpdateDto userData, @PathVariable long id) {
        return userService.update(userData, id);
    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "user delete")
            @PathVariable long id) {
        userService.delete(id);
    }
}
