package com.sample.sample.service;

import com.sample.sample.domain.order.OrderWithDetails;
import com.sample.sample.domain.order.PaymentMethod;
import com.sample.sample.entity.Item;
import com.sample.sample.entity.Order;
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
import java.util.List;
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
        // select
        //        item0_.id as id1_1_0_,
        //        item0_.created_at as created_2_1_0_,
        //        item0_.deleted_at as deleted_3_1_0_,
        //        item0_.updated_at as updated_4_1_0_,
        //        item0_.name as name5_1_0_,
        //        item0_.price as price6_1_0_,
        //        item0_.stock as stock7_1_0_
        //    from
        //        items item0_
        //    where
        //        item0_.id in (
        //            ?, ?
        //        )
        // default batch size로 인한 in 검색조건

        assertThat(orderWithDetails.getOrder().getUserId()).isEqualTo(user.getId());
        assertThat(orderWithDetails.getOrder().getTotal()).isEqualTo(1800000L);
        assertThat(orderWithDetails.getDetails().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    void getOrderWithDetailByFetchJoin() {
        User user = userRepository.findByEmail("test@test.com").get();
        Item item = itemRepository.findByName("macbook air").get();

        OrderWithDetails orderWithDetails = orderService.paymentOneItem(user, PaymentMethod.CARD, item, 2L, Optional.empty());

        em.flush();
        em.clear();

        List<Order> orders = orderRepository.getWithDetailsByUserId(user.getId());
        // select
        //        order0_.id as id1_3_0_,
        //        orderdetai1_.id as id1_2_1_,
        //        order0_.created_at as created_2_3_0_,
        //        order0_.deleted_at as deleted_3_3_0_,
        //        order0_.updated_at as updated_4_3_0_,
        //        order0_.memo as memo5_3_0_,
        //        order0_.method as method6_3_0_,
        //        order0_.total as total7_3_0_,
        //        order0_.user_id as user_id8_3_0_,
        //        orderdetai1_.created_at as created_2_2_1_,
        //        orderdetai1_.deleted_at as deleted_3_2_1_,
        //        orderdetai1_.updated_at as updated_4_2_1_,
        //        orderdetai1_.amount as amount5_2_1_,
        //        orderdetai1_.name as name6_2_1_,
        //        orderdetai1_.order_id as order_id7_2_1_,
        //        orderdetai1_.price as price8_2_1_,
        //        orderdetai1_.user_id as user_id9_2_1_,
        //        orderdetai1_.order_id as order_id7_2_0__,
        //        orderdetai1_.id as id1_2_0__
        //    from
        //        orders order0_
        //    inner join
        //        order_details orderdetai1_
        //            on order0_.id=orderdetai1_.order_id
        //    where
        //        order0_.user_id=?
        // fetch join으로 인한 조인 쿼리

        assertThat(orders.size()).isEqualTo(1);
        OrderWithDetails result = orders.stream()
                .map(o -> new OrderWithDetails(o, o.getOrderDetails())).findFirst().orElse(null);

        assertThat(result).isNotNull();
        assertThat(orderWithDetails.getOrder().getId()).isEqualTo(result.getOrder().getId());
        assertThat(orderWithDetails.getDetails().size()).isEqualTo(result.getDetails().size());
    }
}
