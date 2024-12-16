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
    public ResponseEntity<Map<String, Object>> getAllUsers(@RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        return userService.getAllUsers(accessToken);
    }

    @GetMapping("${endpoint.get-user-by-token}")
    public ResponseEntity<Map<String, Object>> getUserByToken(@RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        return userService.getUserByToken(accessToken);
    }

    @GetMapping("${endpoint.get-user-by-id}/{userId}")
    public ResponseEntity<Map<String, Object>> getUserById(@RequestHeader("Authorization") String authorizationHeader, @PathVariable String userId) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        return userService.getUserById(accessToken, userId);
    }

    @PostMapping("${endpoint.register}")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserDTO dto) {
        if (Objects.equals(dto.getRole(), UserRole.BUSINESS)) {
            dto.setDesignation(Designation.MANAGER);
        } else
        if (Objects.equals(dto.getRole(), UserRole.CARRIER)) {
            dto.setDesignation(Designation.MANAGER);
        } 

        dto.setId(UUID.randomUUID().toString());
        dto.setEnabled(true);
        dto.setUsername(dto.getPhoneNumber());

        return userService.saveUser(dto);
    }

    @PostMapping("${endpoint.login}")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO dto) {
        return userService.login(dto.getPhoneNumber(), dto.getPassword());
    }

    @PostMapping("${endpoint.edit-user}")
    public ResponseEntity<Map<String, Object>> editUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UserDTO dto) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        return userService.editUser(accessToken, dto);
    }
}