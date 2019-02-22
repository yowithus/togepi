package com.example.togepi.service.impl;

import com.example.togepi.exception.ResourceNotFoundException;
import com.example.togepi.model.User;
import com.example.togepi.service.UserService;
import com.example.togepi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        final String password = user.getPassword();

        return new org.springframework.security.core.userdetails.User(username, password, getAuthority());
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User userRequest) throws ResourceNotFoundException {
        return userRepository.findById(userId).map(user -> {
            user.setUsername(userRequest.getUsername());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public Map<String, Boolean> deleteUser(Long userId) throws ResourceNotFoundException {
        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);

            final Map<String, Boolean> response = new HashMap<>();
            response.put("isDeleted", Boolean.TRUE);
            return response;
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
