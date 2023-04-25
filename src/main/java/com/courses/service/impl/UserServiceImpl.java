package com.courses.service.impl;

import com.courses.dto.UserDto;
import com.courses.enums.Role;
import com.courses.model.User;
import com.courses.repository.UserRepository;
import com.courses.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllUsers() {
        logger.info("Retrieving all users");
        return userRepository.findAllByRole(Role.USER).stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto setRole(Integer id, Role role) {
        logger.info("Setting new role: {} to user: {}", role, id);
        var userToUpdate = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " doesn't exist"));
        userToUpdate.setRole(role);
        var userToReturn = userRepository.save(userToUpdate);
        return toUserDto(userToReturn);
    }

    private UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
