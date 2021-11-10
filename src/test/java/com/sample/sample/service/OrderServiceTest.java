package com.sample.sample.service;

import com.sample.sample.domain.order.OrderWithDetails;
import com.sample.sample.domain.order.PaymentMethod;
import com.sample.sample.entity.Item;
import com.sample.sample.entity.OrderDetail;
import com.sample.sample.entity.User;
import com.sample.sample.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired CartService cartService;

    @Autowired CartRepository cartRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired UserRepository userRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired OrderDetailRepository orderDetailRepository;

    void createSampleUser() {
        User user = new User();
        user.setEmail("test@test.com");
        userRepository.save(user);
    }

    void createSampleItem() {
        Item item = new Item();
        item.setName("macbook air");
        item.setPrice(1000000L);
        item.setStock(2L);
        itemRepository.save(item);

        Item item2 = new Item();
        item2.setName("macmini");
        item2.setPrice(800000L);
        item2.setStock(2L);
        itemRepository.save(item2);
    }

    @BeforeEach
    void init() {
        createSampleUser();
        createSampleItem();
    }

    @AfterEach
    void after() {
        cartRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
        orderRepository.deleteAll();
        orderDetailRepository.deleteAll();
    }

    @Test
    @Transactional
    void paymentOneItem_success() {
        User user = userRepository.findByEmail("test@test.com").get();
        Item item = itemRepository.findByName("macbook air").get();

        OrderWithDetails orderWithDetails = orderService.paymentOneItem(user, PaymentMethod.CARD, item, 2L, Optional.empty());
        assertThat(orderWithDetails.getOrder().getUserId()).isEqualTo(user.getId());
        assertThat(orderWithDetails.getOrder().getTotal()).isEqualTo(2000000L);
        assertThat(orderWithDetails.getDetails().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void paymentOneItemWithMemo_success() {
        User user = userRepository.findByEmail("test@test.com").get();
        Item item = itemRepository.findByName("macbook air").get();

        Optional<String> memo = Optional.of("memo");

        OrderWithDetails orderWithDetails = orderService.paymentOneItem(user, PaymentMethod.CARD, item, 1L, memo);
        assertThat(orderWithDetails.getOrder().getUserId()).isEqualTo(user.getId());
        assertThat(orderWithDetails.getOrder().getMemo()).isNotNull();
        assertThat(orderWithDetails.getDetails().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    void paymentOneItem_failure() {
        User user = userRepository.findByEmail("test@test.com").get();
        Item item = itemRepository.findByName("macbook air").get();

        assertThrows(IllegalStateException.class,
                () -> orderService.paymentOneItem(user, PaymentMethod.CARD, item, item.getStock() + 1, Optional.empty()));
    }

    @Test
    @Transactional
    void paymentAllInCart_success() {
        User user = userRepository.findByEmail("test@test.com").get();
        Item macbookAir = itemRepository.findByName("macbook air").get();
        Item macmini = itemRepository.findByName("macmini").get();

        cartService.addToCart(user, macbookAir, 1L);
        cartService.addToCart(user, macmini, 1L);

        // 테스트를 위한 쿼리 플러시 & 영속성 컨텍스트 클리어
        em.flush();
        em.clear();

        OrderWithDetails orderWithDetails = orderService.paymentAllInCart(user, PaymentMethod.CASH, Optional.empty());

        assertThat(orderWithDetails.getOrder().getUserId()).isEqualTo(user.getId());
        assertThat(orderWithDetails.getOrder().getTotal()).isEqualTo(1800000L);
        assertThat(orderWithDetails.getDetails().size()).isEqualTo(2);

    }
}