package com.sample.sample.service;

import com.sample.sample.entity.Cart;
import com.sample.sample.entity.Item;
import com.sample.sample.entity.User;
import com.sample.sample.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CartService {

    private final CartRepository cartRepository;

    private final ItemService itemService;

    public List<Cart> getByUser(User user) {
        return cartRepository.getByUserId(user.getId());
    }

    @Transactional
    public Cart addToCart(User user, Item item, Long amount) {
        itemService.checkStock(item.getId(), amount);

        Cart cart = new Cart();
        cart.setAmount(amount);
        cart.setUserId(user.getId());
        cart.setItemId(item.getId());
        return cartRepository.save(cart);
    }

    @Transactional
    public void dropFromCart(Cart cart) {
        cart.setDeletedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.saveAndFlush(cart);
    }
}
