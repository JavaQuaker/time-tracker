package com.example.timetracker.util;

import com.example.timetracker.model.Task;
import com.example.timetracker.model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Getter
@Component
public class ModelGenerator {
    private Model<User> userModel;
    private Model<Task> taskModel;
    @Autowired
    private Faker faker;
    @PostConstruct
    public void init() {
        userModel = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getFirstName), () -> faker.name().firstName())
                .supply(Select.field(User::getLastName), () -> faker.name().lastName())
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .toModel();
        System.out.println("userModel " + userModel);

        taskModel = Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getName), () -> faker.lorem().word())
                .toModel();
    }
}
