package com.sample.sample.service;

import com.sample.sample.entity.Cart;
import com.sample.sample.entity.Item;
import com.sample.sample.entity.User;
import com.sample.sample.exception.UnprocessableEntityException;
import com.sample.sample.repository.CartRepository;
import com.sample.sample.repository.ItemRepository;
import com.sample.sample.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CartServiceTest {

    @Autowired CartService cartService;

    @Autowired CartRepository cartRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;

    @AfterEach
    void after() {
        cartRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
    }

    User createSampleUser() {
        User user = new User();
        user.setEmail("test@test.com");
        return userRepository.save(user);
    }

    Item createSampleItem() {
        Item item = new Item();
        item.setName("macbook air");
        item.setPrice(1000000L);
        item.setStock(2L);
        return itemRepository.save(item);
    }

    @Test
    void addToCart_success() {
        User user = createSampleUser();
        Item item = createSampleItem();

        Cart cart = cartService.addToCart(user, item, 1L);

        assertThat(cart.getId()).isNotNull();
    }

    @Test
    void addToCart_fail() {
        User user = createSampleUser();
        Item item = createSampleItem();

        assertThrows(UnprocessableEntityException.class,
                () -> cartService.addToCart(user, item, 3L));
    }

    @Test
    @Transactional
    // user_id만 저장하고 user 객체를 불러올 수 있는지 테스트하기 위해 객체를 영속성 컨텍스트에서 제거후 다시 불러오기 위해 lock 사용
    void dropFromCart_success() {
        User user = createSampleUser();
        Item item = createSampleItem();

        Cart cart = cartService.addToCart(user, item, 1L);
        Cart cart1 = cartService.addToCart(user, item, 1L);

        cartService.dropFromCart(cart1);

//        cartRepository.lock(cart);
//        List<Cart> carts = cartRepository.getByUserId(cart.getUser().getId());

        userRepository.lockAndRefresh(user);
        List<Cart> carts = user.getCarts();

        assertThat(carts.size()).isEqualTo(1L);
    }
}
