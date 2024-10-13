package com.example.demo.controllers;

import com.example.demo.Utils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;

    private final UserRepository userRepo = mock(UserRepository.class);

    private final CartRepository cartRepo = mock(CartRepository.class);

    private final PasswordEncoder encoder = mock(PasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        Utils.injectObjects(userController, "userRepository", userRepo);
        Utils.injectObjects(userController, "cartRepository", cartRepo);
        Utils.injectObjects(userController, "passwordEncoder", encoder);
    }

    @Test
    public void createUser() {
        when(encoder.encode("passwordTest111")).thenReturn("hashedPassword");
        CreateUserRequest newUser = new CreateUserRequest();
        newUser.setUsername("test");
        newUser.setPassword("passwordTest111");
        final ResponseEntity<User> response = userController.createUser(newUser);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User userRes = response.getBody();

        assertNotNull(userRes);
        assertEquals(0, userRes.getId());
        assertEquals("test", userRes.getUsername());
        assertEquals("hashedPassword", userRes.getPassword());
    }

    @Test
    public void findUserById() {
        when(encoder.encode("passwordTest111")).thenReturn("hashedPassword");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("passwordTest111");
        final ResponseEntity<User> response = userController.createUser(r);
        User user = response.getBody();
        when(userRepo.findById(0L)).thenReturn(java.util.Optional.ofNullable(user));

        final ResponseEntity<User> userResponseEntity = userController.findById(0L);

        User userRes = userResponseEntity.getBody();
        assertNotNull(userRes);
        assertEquals(0, userRes.getId());
        assertEquals("test", userRes.getUsername());
        assertEquals("hashedPassword", userRes.getPassword());
    }

    @Test
    public void findUserByUserName() {
        when(encoder.encode("passwordTest111")).thenReturn("hashedPassword");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("passwordTest111");
        final ResponseEntity<User> response = userController.createUser(r);
        User user = response.getBody();
        when(userRepo.findByUsername("test")).thenReturn(user);

        final ResponseEntity<User> userResponseEntity = userController.findByUserName("test");

        User userRes = userResponseEntity.getBody();
        assertNotNull(userRes);
        assertEquals(0, userRes.getId());
        assertEquals("test", userRes.getUsername());
        assertEquals("hashedPassword", userRes.getPassword());
    }
}
