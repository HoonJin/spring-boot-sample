package com.sample.sample.service;

import com.sample.sample.entity.Item;
import com.sample.sample.exception.NotFoundException;
import com.sample.sample.exception.UnprocessableEntityException;
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
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("not_found", "상품이 존재하지 않습니다."));
        if (item.getStock() < amount) {
            throw new UnprocessableEntityException("exceed_amount", "재고 초과하였습니다.");
        }
    }

    @Transactional
    public void decreaseStock(Item item, Long amount) {
        itemRepository.lockAndRefresh(item);
        item.setStock(item.getStock() - amount);

        if (item.getStock() < 0) {
            throw new UnprocessableEntityException("exceed_amount", "재고를 초과하였습니다.");
        }

        itemRepository.save(item);
    }
}
