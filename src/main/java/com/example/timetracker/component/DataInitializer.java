package com.example.timetracker.component;

import com.example.timetracker.dto.UserCreateDTO;
import com.example.timetracker.mapper.UserMapper;
import com.example.timetracker.model.User;
import com.example.timetracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        defaultUser();
    }
    public void defaultUser() {
        UserCreateDTO userData = new UserCreateDTO();
        userData.setFirstName("Alexey");
        userData.setLastName("Petrov");
        userData.setEmail("qwe@mail.ru");
        User user = userMapper.map(userData);
        userRepository.save(user);
    }
}
