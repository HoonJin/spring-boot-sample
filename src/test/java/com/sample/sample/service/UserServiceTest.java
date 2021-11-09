package com.sample.sample.service;

import com.sample.sample.entity.User;
import com.sample.sample.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;

;

@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;

    @Autowired UserRepository userRepository;

    @AfterEach
    void after() {
        userRepository.deleteAll();
    }

    @Test
    void createUser_successfully() {
        String email = "test@test.com";
        User user = userService.createUser("test@test.com");
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getId()).isNotNull();
    }

    @Test
    void duplicateException() {
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            userService.createUser("test@test.com");
            userService.createUser("test@test.com");
        });
    }

}