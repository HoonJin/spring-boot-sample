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

@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;

    @Autowired UserRepository userRepository;

    @AfterEach
    void after() {
        userRepository.deleteAll();
    }

    @Test
    void createUser_success() {
        String email = "test@test.com";
        User user = userService.createUser(email);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getId()).isNotNull();
    }

    @Test
    void createUser_duplicateException() {
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            userService.createUser("test@test.com");
            userService.createUser("test@test.com");
        });
    }

    @Test
    void findByEmail_success() {
        String email = "test@test.com";
        userService.createUser(email);
        User user = userService.findByEmail(email);

        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    void findByEmail_illegalArgumentException() {
        String email = "not_exist@test.com";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            userService.findByEmail(email);
        });
    }

}