package com.courses.controller;

import com.courses.dto.UserDto;
import com.courses.enums.Role;
import com.courses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/set-role")
    public UserDto setUserRole(@RequestParam("id") Integer id, @RequestParam("role") Role role) {
        return userService.setRole(id, role);
    }
}
