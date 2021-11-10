package com.sample.sample.service;

import com.sample.sample.domain.order.OrderWithDetails;
import com.sample.sample.domain.order.PaymentMethod;
import com.sample.sample.entity.*;
import com.sample.sample.repository.OrderDetailRepository;
import com.sample.sample.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderService {

    private final CartService cartService;
    private final ItemService itemService;

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Transactional
    public OrderWithDetails paymentOneItem(User user, PaymentMethod method, Item item, Long amount, Optional<String> memo) {
        Order order = saveOrder(user.getId(), method, item.getPrice() * amount, memo);
        itemService.decreaseStock(item, amount);
        OrderDetail detail = saveOrderDetails(item, user.getId(), order.getId(), amount);
        return new OrderWithDetails(order, List.of(detail));
    }

    @Transactional
    public OrderWithDetails paymentAllInCart(User user, PaymentMethod method, Optional<String> memo) {
        List<Cart> carts = cartService.getByUser(user);
        Long total = carts.stream()
                .map(cart -> cart.getItem().getPrice() * cart.getAmount())
                .reduce(0L, Long::sum);

        Order order = saveOrder(user.getId(), method, total, memo);

        List<OrderDetail> details = carts.stream().map(cart -> {
            itemService.decreaseStock(cart.getItem(), cart.getAmount());
            cartService.dropFromCart(cart);
            return saveOrderDetails(cart.getItem(), user.getId(), order.getId(), cart.getAmount());
        }).collect(Collectors.toList());
        return new OrderWithDetails(order, details);
    }

    private Order saveOrder(Long userId, PaymentMethod paymentMethod, Long total, Optional<String> memo) {
        Order order = new Order();
        order.setUserId(userId);
        order.setMethod(paymentMethod);
        order.setTotal(total);
        memo.ifPresent(order::setMemo);

        return orderRepository.save(order);
    }

    private OrderDetail saveOrderDetails(Item item, Long userId, Long orderId, Long amount) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setUserId(userId);
        orderDetail.setOrderId(orderId);
        orderDetail.setName(item.getName());
        orderDetail.setPrice(item.getPrice());
        orderDetail.setAmount(amount);

        return orderDetailRepository.save(orderDetail);
    }
}
