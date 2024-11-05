package com.supplychain.userservice.command.controller;

import com.supplychain.userservice.command.data.User;
import com.supplychain.userservice.command.model.UserDTO;
import com.supplychain.userservice.command.service.UserService;
import com.supplychain.userservice.enumeration.UserRole;
import com.supplychain.userservice.enumeration.Designation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.base-path}")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/list-user")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody UserDTO dto) {
        if (Objects.equals(dto.getRole(), UserRole.BUSINESS))
            dto.setDesignation(Designation.MANAGER);

        dto.setId(UUID.randomUUID().toString());
        dto.setUsername(dto.getPhoneNumber());

        return userService.saveUser(dto);
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody UserDTO dto) {
        return userService.login(dto.getPhoneNumber(), dto.getPassword());
    }
}