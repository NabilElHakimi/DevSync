package org.example.DevSync1;

import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.repository.UserRepository;
import org.example.DevSync1.util.HashPassword;
import org.mindrot.jbcrypt.BCrypt;

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

        System.out.println("Hashed Password: " + user.getPassword());


    }

}
