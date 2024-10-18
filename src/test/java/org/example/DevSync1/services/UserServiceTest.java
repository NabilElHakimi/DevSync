package org.example.DevSync1.services;

import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.exceptions.UserIDCannotBeNull;
import org.example.DevSync1.exceptions.UserNotFoundException;
import org.example.DevSync1.exceptions.UserObjectIsNull;
import org.example.DevSync1.repository.UserRepository;
import org.example.DevSync1.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUserNull() {
        assertThrows(UserObjectIsNull.class, () -> {
            userService.save(null);
        });
    }

    @Test
    void testSaveUserWithEmptyAttributes() {
        User user = new User();
        user.setFirstName("Nabil");
        user.setLastName("");
        user.setEmail("Hakimi@gmail.com");
        user.setPassword("00000");
        user.setRole(Role.USER);

        assertThrows(UserObjectIsNull.class, () -> {
            userService.save(user);
        });

        user.setFirstName("John");
        user.setLastName("");

        assertThrows(UserObjectIsNull.class, () -> {
            userService.save(user);
        });
    }

    @Test
    void testSaveUserWithNullAttributes() {
        User user = new User();
        user.setFirstName(null);
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");
        user.setRole(Role.USER);

        assertThrows(UserObjectIsNull.class, () -> {
            userService.save(user);
        });

        user.setFirstName("Nabil");
        user.setLastName(null);

        assertThrows(UserObjectIsNull.class, () -> {
            userService.save(user);
        });
    }

    @Test
    void testUpdateUserNull() {
        assertThrows(UserObjectIsNull.class, () -> {
            userService.update(null);
        });
    }

    @Test
    void testDeleteUserNull() {
        assertThrows(UserIDCannotBeNull.class, () -> {
            userService.delete(null);
        });
    }

    @Test
    void testFindByIdUserNull() {
        assertThrows(UserIDCannotBeNull.class, () -> {
            userService.findById(null);
        });
    }

    @Test
    void testFindByIdUserNotFound() {
        assertThrows(UserIDCannotBeNull.class, () -> {
            userService.findById(10000000000000000L);
        });
    }

    @Test
    void testSaveValidUser() {
        User user = new User();
        user.setFirstName("Nabil");
        user.setLastName("Hakimi");
        user.setEmail("Hakimiiiippp@gmail.com");
        user.setPassword("00000");
        user.setRole(Role.USER);
        when(userRepository.save(user)).thenReturn(true);  // Mocking the return of the User object after saving
        boolean result = userService.save(user);
        assertTrue(result);
    }
}
