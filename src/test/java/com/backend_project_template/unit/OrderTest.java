package com.backend_project_template.unit;

import com.backend_project_template.domain.Order;
import com.backend_project_template.domain.OrderItem;
import com.backend_project_template.domain.User;
import com.backend_project_template.exceptions.OrderDomainException;
import com.backend_project_template.persistence.entities.OrderStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private User createDefaultUser() {
        return new User(1L, "Alice", "alice@mail.com");
    }

    private OrderItem createDefaultOrderItem() {
        return new OrderItem("Book", 1, 30.0);
    }

    private Order createDefaultOrder(OrderStatus status) {
        return new Order(createDefaultUser(), List.of(createDefaultOrderItem()), Instant.now(), status);
    }

    @Test
    void shouldCreateOrderWithValidUserAndItems() {
        Order order = createDefaultOrder(OrderStatus.PENDING);
        assertEquals(1, order.getItems().size());
    }

    @Test
    void shouldThrowExceptionIfOrderHasNoItems() {
        User user = createDefaultUser();
        assertThrows(OrderDomainException.class, () ->
                new Order(user, List.of(), Instant.now(), OrderStatus.PENDING)
        );
    }

    @Test
    void shouldThrowExceptionIfOrderHasNoUser() {
        OrderItem item = createDefaultOrderItem();
        assertThrows(OrderDomainException.class, () ->
                new Order(null, List.of(item), Instant.now(), OrderStatus.PENDING)
        );
    }

    @Test
    void shouldCancelPendingOrder() {
        Order order = createDefaultOrder(OrderStatus.PENDING);
        order.cancel();
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void shouldThrowExceptionIfCancelNotPending() {
        Order order = createDefaultOrder(OrderStatus.SHIPPED);
        assertThrows(OrderDomainException.class, order::cancel);
    }

    @Test
    void shouldCalculateTotalPrice() {
        OrderItem item1 = new OrderItem("Laptop", 1, 1200.0);
        OrderItem item2 = new OrderItem("Mouse", 2, 50.0);
        Order order = new Order(createDefaultUser(), List.of(item1, item2), Instant.now(), OrderStatus.PENDING);

        double total = order.getTotal();

        assertEquals(1300.0, total);
    }

    @Test
    void shouldContainProductByName() {
        OrderItem item = new OrderItem("iPhone", 1, 999.0);
        Order order = new Order(createDefaultUser(), List.of(item), Instant.now(), OrderStatus.PENDING);

        boolean result = order.containsProduct("iphone");

        assertTrue(result);
    }
}