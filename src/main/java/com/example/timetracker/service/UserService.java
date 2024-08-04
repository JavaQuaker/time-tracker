package com.example.timetracker.service;

import com.example.timetracker.dto.UserCreateDTO;
import com.example.timetracker.dto.UserDTO;
import com.example.timetracker.dto.UserUpdateDto;
import com.example.timetracker.mapper.UserMapper;
import com.example.timetracker.model.User;
import com.example.timetracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.timetracker.exception.ResourceNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::map)
                .toList();
    }
    public UserDTO findById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        return userMapper.map(user);
    }
    public UserDTO create(UserCreateDTO userData) {
       User user = userMapper.map(userData);
       userRepository.save(user);
        UserDTO userDTO = userMapper.map(user);
       return userDTO;
    }
    public UserDTO update(UserUpdateDto userData, long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        userMapper.update(userData, user);
        UserDTO userDTO = userMapper.map(user);
        return userDTO;
    }
    public void delete(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
    
}
