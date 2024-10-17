package org.example.DevSync1;

import lombok.Value;
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
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        new TaskService().delete(1L);
        /*Task task =   new TaskService().getTaskById(1L);
        System.out.println(task.getCreatedBy().getFirstName());*/


//        System.out.println(new TaskService().taskStatistic() + " %");


    }

}
