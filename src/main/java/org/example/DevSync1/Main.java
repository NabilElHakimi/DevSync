package org.example.DevSync1;

import org.example.DevSync1.entity.Tag;
import org.example.DevSync1.entity.Task;
import org.example.DevSync1.entity.Token;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.repository.TagRepository;
import org.example.DevSync1.repository.TaskRepository;
import org.example.DevSync1.repository.TokenRepository;
import org.example.DevSync1.repository.UserRepository;
import org.example.DevSync1.service.TagService;
import org.example.DevSync1.service.TaskService;
import org.example.DevSync1.service.UserService;
import org.example.DevSync1.util.HashPassword;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        User user = new UserService().findById(2L);
        Task task = new Task();
        task.setAssignedTo(user);
        task.setTitle("Task 1");
        task.setDescription("Description 1");
        task.setDueDate(LocalDate.now().plusDays(1));

        List<Tag> tags = new TagService().findAll();
        tags.remove(0);
        task.setTags(tags);
        new TaskService().updateTaskWithNewTags(8L , task , tags);


    }

}
