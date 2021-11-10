package com.sample.sample.repository;

import com.sample.sample.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, RepositoryCustom {

    List<Cart> getByUserId(Long userId);
}
