package com.courses.service;

import com.courses.dto.UserDto;
import com.courses.enums.Role;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto setRole(Integer id, Role role);
}
