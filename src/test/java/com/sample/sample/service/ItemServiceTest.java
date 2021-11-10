package com.sample.sample.service;

import com.sample.sample.entity.Item;
import com.sample.sample.repository.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired ItemRepository itemRepository;
    @Autowired ItemService itemService;

    @AfterEach
    void after() {
        itemRepository.deleteAll();
    }

    Item createSampleData() {
        Item item = new Item();
        item.setName("macbook air");
        item.setPrice(1000000L);
        item.setStock(2L);
        return itemRepository.save(item);
    }

    @Test
    void checkStock_success() {
        Item item = createSampleData();

        itemService.checkStock(item.getId(), 1L);
    }

    @Test
    void checkStock_fail() {
        Item item = createSampleData();

        Assertions.assertThrows(IllegalStateException.class,
                () -> itemService.checkStock(item.getId(), 3L));
    }
}