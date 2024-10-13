package com.example.demo.security;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImpTest {
    private final UserRepository userRepository = mock(UserRepository.class);

    private UserDetailsServiceImp userDetailsServiceImp;

    @Before
    public void setUp() {
        userDetailsServiceImp = new UserDetailsServiceImp(userRepository);
        com.example.demo.Utils.injectObjects(userDetailsServiceImp, "userRepository", userRepository);
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "nosm";
        User user = new User();
        user.setUsername(username);
        String password = "password123";
        user.setPassword(password);
        user.setId(1L);

        when(userRepository.findByUsername(username)).thenReturn(user);

        UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(username);
        assertNotNull(userDetails);

        assertEquals(password, userDetails.getPassword());
        assertEquals(username, userDetails.getUsername());
    }
}
