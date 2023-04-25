package com.courses.unit.service;

import com.courses.enums.Role;
import com.courses.model.User;
import com.courses.repository.UserRepository;
import com.courses.service.UserService;
import com.courses.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void init() {
        userRepository = mock(UserRepository.class);

        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAllByRole(any())).thenReturn(List.of(getTestUser()));

        var result = userService.getAllUsers().get(0);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("test", result.getFirstName());
        assertEquals("test", result.getLastName());
        assertEquals("test", result.getEmail());
        assertEquals(Role.USER, result.getRole());
    }

    @ParameterizedTest
    @EnumSource(Role.class)
    void testSetRole(Role role) {
        var user = getTestUser();
        user.setRole(role);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        var result = userService.setRole(1, role);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("test", result.getFirstName());
        assertEquals("test", result.getLastName());
        assertEquals("test", result.getEmail());
        assertEquals(role, result.getRole());
    }

    @Test
    void testSetRoleUserNotFound() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.setRole(1, Role.USER));
    }

    private User getTestUser() {
        return User.builder()
                .id(1)
                .firstName("test")
                .lastName("test")
                .email("test")
                .password("test")
                .role(Role.USER)
                .build();
    }

}
