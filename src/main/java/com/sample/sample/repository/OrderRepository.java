package com.sample.sample.repository;

import com.sample.sample.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o JOIN FETCH o.orderDetails WHERE o.userId = :userId")
    List<Order> getWithDetailsByUserId(@Param("userId") Long userId);
}
