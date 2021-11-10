package com.sample.sample.service;

import com.sample.sample.entity.Item;
import com.sample.sample.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    public void checkStock(Long itemId, Long amount) {
        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);
        if (item.getStock() < amount) {
            throw new IllegalStateException();
        }
    }

    @Transactional
    public void decreaseStock(Item item, Long amount) {
        itemRepository.lock(item);
        item.setStock(item.getStock() - amount);

        if (item.getStock() < 0) {
            throw new IllegalStateException();
        }

        itemRepository.save(item);
    }
}
