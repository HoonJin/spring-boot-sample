package com.sample.sample.repository;

import com.sample.sample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, RepositoryCustom {

    Optional<User> findByEmail(String email);
}
