package com.example.togepi.service;

import com.example.togepi.model.User;
import com.example.togepi.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<User> getUsers();

    User getUserById(Long userId) throws ResourceNotFoundException;

    User createUser(User user);

    User updateUser(Long userId, User user) throws ResourceNotFoundException;

    Map<String, Boolean> deleteUser(Long userId) throws ResourceNotFoundException;
}
