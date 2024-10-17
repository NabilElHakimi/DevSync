package services;

import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.repository.UserRepository;
import org.example.DevSync1.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUserNull() {
        assertThrows(IllegalArgumentException.class, () -> {
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

        assertThrows(IllegalArgumentException.class, () -> {
            userService.save(user);
        });

        user.setFirstName("John");
        user.setLastName("");

        assertThrows(IllegalArgumentException.class, () -> {
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

        assertThrows(IllegalArgumentException.class, () -> {
            userService.save(user);
        });

        user.setFirstName("Nabil");
        user.setLastName(null);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.save(user);
        });
    }
}
