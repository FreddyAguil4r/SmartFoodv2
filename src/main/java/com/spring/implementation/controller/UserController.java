package com.spring.implementation.controller;

import com.spring.implementation.domain.model.User;
import com.spring.implementation.domain.service.UserService;
import com.spring.implementation.dto.domain.UserDto;
import com.spring.implementation.dto.save.SaveUserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private final ModelMapper mapper;

    @PostMapping
    public UserDto createUser(@Valid @RequestBody SaveUserDto resource) {
        User user = convertToUser(resource);
        return convertToResource(userService.create(user));
    }

    @GetMapping("/{userId}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody SaveUserDto userRequest) {
        return userService.update(userId, userRequest);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
    }

    private User convertToUser(SaveUserDto resource) {
        return mapper.map(resource, User.class);
    }
    private UserDto convertToResource(User entity)
    {
        return mapper.map(entity, UserDto.class);
    }
}
