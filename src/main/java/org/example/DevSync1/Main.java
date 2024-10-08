package org.example.DevSync1;

import org.example.DevSync1.entity.Tag;
import org.example.DevSync1.entity.Task;
import org.example.DevSync1.entity.Token;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.repository.TaskRepository;
import org.example.DevSync1.repository.TokenRepository;
import org.example.DevSync1.repository.UserRepository;
import org.example.DevSync1.util.HashPassword;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        // Hash the password before storing it

        User user1 = new UserRepository().findById(1L);

        System.out.println("========================================================================");
        System.out.println("User: " + user1.getFirstName() + " " + user1.getLastName());
        System.out.println("========================================================================");


        Task newTask = new Task();
        newTask.setTitle("Complete Projeczzzzzzt");
        newTask.setDescription("Finish the project by the end of the week");
        newTask.setDueDate(LocalDate.now().plusDays(5));
        newTask.setAssignedTo(user1);
        newTask.setId(2L);
        new TaskRepository().update(newTask);


        System.out.println("Task updated successfully");


    }

}
