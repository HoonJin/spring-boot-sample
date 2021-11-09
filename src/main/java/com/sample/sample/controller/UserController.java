package com.sample.sample.controller;

import com.sample.sample.domain.CreateUserDto;
import com.sample.sample.domain.GetUserDto;
import com.sample.sample.entity.User;
import com.sample.sample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @GetMapping
    public GetUserDto getUser(User user) {
        return GetUserDto.of(user);
    }

    @PostMapping
    public GetUserDto createUser(@RequestBody CreateUserDto createUserDto) {
        User user = userService.createUser(createUserDto.getEmail());
        return GetUserDto.of(user);
    }
}
