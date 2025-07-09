package com.backend_project_template.unit;

import com.backend_project_template.domain.Order;
import com.backend_project_template.domain.OrderItem;
import com.backend_project_template.domain.User;
import com.backend_project_template.exceptions.UserDomainException;
import com.backend_project_template.persistence.entities.OrderStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void shouldCreateUserWithValidData() {
        User user = new User("Alice", "alice@mail.com", "hashedPwd");

        assertEquals("Alice", user.getName());
        assertEquals("alice@mail.com", user.getEmail());
        assertEquals("hashedPwd", user.getPassword());
    }

    @Test
    void shouldThrowIfNameIsBlank() {
        assertThrows(UserDomainException.class, () -> new User("  ", "test@mail.com", "pwd"));
    }

    @Test
    void shouldThrowIfEmailIsInvalid() {
        assertThrows(UserDomainException.class, () -> new User("Alice", "invalidemail", "pwd"));
    }

    @Test
    void shouldAddOrderToUserWhenPlacingOrder() {
        User user = new User("Alice", "alice@mail.com", "pwd");
        OrderItem item = new OrderItem("Book", 1, 30.0);

        user.placeOrder(List.of(item));

        assertEquals(1, user.getOrders().size());
        Order order = user.getOrders().getFirst();
        assertEquals(user, order.getUser());
    }

    @Test
    void shouldRetrieveOrderById() {
        User user = new User("Alice", "alice@mail.com", "pwd");
        OrderItem item = new OrderItem("Book", 1, 30.0);
        user.placeOrder(List.of(item));

        Order order = user.getOrders().getFirst();
        order.setId(42L);

        Order retrieved = user.getOrderById(42L);

        assertSame(order, retrieved);
    }

    @Test
    void shouldThrowIfOrderNotFound() {
        User user = new User("Alice", "alice@mail.com", "pwd");

        assertThrows(UserDomainException.class, () -> user.getOrderById(99L));
    }

    @Test
    void shouldCancelOrder() {
        User user = new User("Alice", "alice@mail.com", "pwd");
        OrderItem item = new OrderItem("Book", 1, 30.0);
        user.placeOrder(List.of(item));
        Order order = user.getOrders().getFirst();
        order.setId(1L);

        user.cancelOrder(1L);

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

}
