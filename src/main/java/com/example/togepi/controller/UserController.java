package com.example.togepi.controller;

import com.example.togepi.exception.ResourceNotFoundException;
import com.example.togepi.model.User;
import com.example.togepi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "/users/{userId}")
    public User getUserById(@PathVariable Long userId) throws ResourceNotFoundException {
        return userService.getUserById(userId);
    }

    @PostMapping(value = "/users")
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping(value = "/users/{userId}")
    public User updateUser(@PathVariable Long userId, @Valid @RequestBody User user) throws ResourceNotFoundException {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping(value = "/users/{userId}")
    public Map<String, Boolean> deleteUser(@PathVariable Long userId) throws ResourceNotFoundException {
        return userService.deleteUser(userId);
    }
}
