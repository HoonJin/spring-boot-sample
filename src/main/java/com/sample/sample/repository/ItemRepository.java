package com.sample.sample.repository;

import com.sample.sample.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, RepositoryCustom {

    Optional<Item> findByName(String name);
}
