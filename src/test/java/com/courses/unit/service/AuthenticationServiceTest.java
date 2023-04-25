package com.courses.unit.service;

import com.courses.config.security.JwtService;
import com.courses.dto.AuthenticateRequestDto;
import com.courses.dto.RegisterRequestDto;
import com.courses.enums.Role;
import com.courses.exception.UserAlreadyExistException;
import com.courses.model.User;
import com.courses.repository.UserRepository;
import com.courses.service.impl.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    private AuthenticationService authenticationService;

    @BeforeEach
    void init() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        authenticationManager = mock(AuthenticationManager.class);

        authenticationService = new AuthenticationService(
                userRepository,
                passwordEncoder,
                jwtService,
                authenticationManager);
    }

    @Test
    void testRegister() {
        when(jwtService.generateToken(any())).thenReturn("token");
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        var result = authenticationService.register(new RegisterRequestDto("test", "test", "email", "password"));

        verify(userRepository, atLeastOnce()).save(any());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());

        var capturedUser = userArgumentCaptor.getValue();
        assertEquals("test", capturedUser.getFirstName());
        assertEquals("test", capturedUser.getLastName());
        assertEquals("email", capturedUser.getUsername());
        assertEquals("encodedPassword", capturedUser.getPassword());
        assertEquals(Role.USER, capturedUser.getRole());

        assertNotNull(result);
        assertEquals("token", result.getToken());
    }

    @Test
    void testRegisterUserAlreadyExist() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistException.class, () -> authenticationService.register(new RegisterRequestDto()));
    }

    @Test
    void testAuthenticate() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));
        when(jwtService.generateToken(any())).thenReturn("token");

        var result = authenticationService.authenticate(new AuthenticateRequestDto("email", "password"));

        verify(authenticationManager, atLeastOnce()).authenticate(any());
        assertNotNull(result);
        assertEquals("token", result.getToken());
    }

    @Test
    void testAuthenticateUsernameNotFound() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.authenticate(new AuthenticateRequestDto()));
    }

}
