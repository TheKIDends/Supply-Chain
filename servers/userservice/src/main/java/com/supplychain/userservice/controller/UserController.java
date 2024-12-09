package com.supplychain.userservice.controller;

import com.supplychain.userservice.data.User;
import com.supplychain.userservice.model.UserDTO;
import com.supplychain.userservice.service.UserService;
import com.supplychain.userservice.enumeration.UserRole;
import com.supplychain.userservice.enumeration.Designation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.base-path}")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("${endpoint.list-user}")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("${endpoint.get-user-by-token}")
    public ResponseEntity<Map<String, Object>> getUserByToken(@RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        System.out.println(accessToken);
        return userService.getUserByToken(accessToken);
    }

    @PostMapping("${endpoint.register}")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserDTO dto) {
        if (Objects.equals(dto.getRole(), UserRole.BUSINESS))
            dto.setDesignation(Designation.MANAGER);

        dto.setId(UUID.randomUUID().toString());
        dto.setEnabled(true);
        dto.setUsername(dto.getPhoneNumber());

        return userService.saveUser(dto);
    }

    @PostMapping("${endpoint.login}")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO dto) {
        return userService.login(dto.getPhoneNumber(), dto.getPassword());
    }
}