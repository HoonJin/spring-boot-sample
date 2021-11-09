package com.sample.sample.service;

import com.sample.sample.entity.User;
import com.sample.sample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(String email) {
        User user = new User();
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public User lockUser(User user) {
        userRepository.lock(user);
        return userRepository.getById(user.getId());
    }
}
