package com.backend_project_template.unit;

import com.backend_project_template.domain.OrderItem;
import com.backend_project_template.exceptions.OrderItemDomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderItemTest {

    @Test
    void shouldCreateOrderItemWithValidData() {
        OrderItem item = new OrderItem("Macbook", 2, 1500.0);

        assertEquals("Macbook", item.getProductName());
        assertEquals(2, item.getQuantity());
        assertEquals(1500.0, item.getUnitPrice());
    }

    @Test
    void shouldThrowExceptionIfProductNameIsBlank() {
        assertThrows(OrderItemDomainException.class, () ->
                new OrderItem("  ", 1, 100.0)
        );
    }

    @Test
    void shouldThrowExceptionIfProductNameIsNull() {
        assertThrows(OrderItemDomainException.class, () ->
                new OrderItem(null, 1, 100.0)
        );
    }

    @Test
    void shouldThrowExceptionIfQuantityIsZeroOrNegative() {
        assertThrows(OrderItemDomainException.class, () ->
                new OrderItem("Macbook", 0, 100.0)
        );
        assertThrows(OrderItemDomainException.class, () ->
                new OrderItem("Macbook", -5, 100.0)
        );
    }

    @Test
    void shouldThrowExceptionIfUnitPriceIsNegative() {
        assertThrows(OrderItemDomainException.class, () ->
                new OrderItem("Macbook", 1, -100.0)
        );
    }

    @Test
    void shouldCalculateTotalPriceCorrectly() {
        OrderItem item = new OrderItem("Macbook", 2, 1500.0);

        double total = item.totalPrice();

        assertEquals(3000.0, total);
    }

    @Test
    void shouldReturnZeroTotalForZeroPrice() {
        OrderItem item = new OrderItem("Mouse", 5, 0.0);

        assertEquals(0.0, item.totalPrice());
    }
}
