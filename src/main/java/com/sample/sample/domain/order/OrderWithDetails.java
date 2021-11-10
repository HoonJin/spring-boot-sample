package com.sample.sample.domain.order;

import com.sample.sample.entity.Order;
import com.sample.sample.entity.OrderDetail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Setter(AccessLevel.NONE)
@AllArgsConstructor
public class OrderWithDetails {
    private Order order;
    private List<OrderDetail> details;
}
