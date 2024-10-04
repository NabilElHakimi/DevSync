package org.example.DevSync1;

import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.repository.UserRepository;
import org.example.DevSync1.util.HashPassword;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@examplemmmm.com");

        user.setRole(Role.USER);

        user.setPassword(new HashPassword().hashedPassword(user.getPassword()));

        System.out.println(user.getPassword());

    }


}
