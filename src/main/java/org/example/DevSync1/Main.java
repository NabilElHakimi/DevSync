package org.example.DevSync1;

import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@examplemmmm.com");
        user.setPassword("password");
        user.setRole(Role.USER);
    
        new UserRepository().save(user);

    }


}
