package com.backend_project_template.domain;

import com.backend_project_template.exceptions.OrderDomainException;
import com.backend_project_template.persistence.entities.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class Order {

    @Setter
    private Long id;
    private Instant orderDate;
    private OrderStatus status;
    private User user;
    private List<OrderItem> items;

    public Order(User user, List<OrderItem> items, Instant orderDate, OrderStatus status) {
        if (user == null) {
            throw new OrderDomainException("Order must be linked to a user");
        }
        if (items == null || items.isEmpty()) {
            throw new OrderDomainException("Order must contain at least one item");
        }
        this.user = user;
        this.items = items;
        this.orderDate = orderDate;
        this.status = status;
    }

    public double getTotal() {
        return items.stream().mapToDouble(OrderItem::totalPrice).sum();
    }

    public void cancel() {
        if (status != OrderStatus.PENDING) {
            throw new OrderDomainException("Only pending orders can be cancelled");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public boolean containsProduct(String productName) {
        return items.stream()
                .anyMatch(item -> item.getProductName().equalsIgnoreCase(productName));
    }
}





