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

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("11");

        // Hash the password before storing it
        String hashedPassword = new HashPassword().hashedPassword(user.getPassword());
        user.setPassword(hashedPassword);

        User user1 = new UserRepository().findById(1L);

        System.out.println("========================================================================");
        System.out.println("User: " + user1.getFirstName() + " " + user1.getLastName());
        System.out.println("========================================================================");

        user.setRole(Role.USER);
        Tag newTag = new Tag();
        newTag.setName("Urgent");
        newTag.setDescription("Tasks that need to be completed urgently");

        Task newTask = new Task();
        newTask.setTitle("Complete Project");
        newTask.setDescription("Finish the project by the end of the week");
        newTask.setDueDate(LocalDateTime.now().plusDays(5));
        newTask.setAssignedTo(user1);

        Token newToken = new Token();
        newToken.setUser(user1);
        newToken.setAvailableTokens(100);
        newToken.setDailyTokens(10);
        newToken.setMonthlyTokens(300);
        newToken.setLastUpdated(LocalDateTime.now());


        new TaskRepository().save(newTask);
//        new TaskRepository().save(newTag);
        new TokenRepository().save(newToken);



        new UserRepository().save(user);

        System.out.println("Hashed Password: " + user.getPassword());
    }

}
