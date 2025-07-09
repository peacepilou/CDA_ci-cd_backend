package com.backend_project_template.integration;

import com.backend_project_template.domain.Order;
import com.backend_project_template.domain.User;
import com.backend_project_template.exposition.dtos.CreateOrderRequest;
import com.backend_project_template.exposition.dtos.CreateUserDto;
import com.backend_project_template.exposition.dtos.OrderItemDto;
import com.backend_project_template.mappers.order.OrderMapper;
import com.backend_project_template.mappers.user.UserMapper;
import com.backend_project_template.persistence.entities.OrderEntity;
import com.backend_project_template.persistence.repositories.OrderRepository;
import com.backend_project_template.persistence.repositories.UserRepository;
import com.backend_project_template.services.AuthService;
import com.backend_project_template.services.OrderService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class OrderServiceIT {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    private Long userId;

    @BeforeEach
    void setup() {
        CreateUserDto dto = new CreateUserDto("alice@test.com", "1234", "Alice");
        authService.createUser(dto);
        userId = userRepository.findByEmail("alice@test.com").orElseThrow().getId();
    }

    @Test
    void shouldCreateOrder() {
        userId = userRepository.findByEmail("alice@test.com").orElseThrow().getId();

        List<OrderItemDto> items = List.of(
                new OrderItemDto("item1", 10, 2),
                new OrderItemDto("item2", 5, 3)
        );
        CreateOrderRequest request = new CreateOrderRequest(userId, items);

        orderService.createOrder(request);

        List<OrderEntity> orders = orderRepository.findAll();
        assertEquals(1, orders.size());

        OrderEntity order = orders.getFirst();
        User user = UserMapper.toDomain(userRepository.findById(userId).orElseThrow());

        assertEquals("alice@test.com", order.getUserEntity().getEmail());
        assertEquals(2, order.getItems().size());
        assertEquals("item1", order.getItems().getFirst().getProductName());
        assertEquals(35.0, OrderMapper.toDomain(order, user).getTotal());
    }

    @Test
    void shouldRetrieveOrderByIdAndEmail() {
        List<OrderItemDto> items = List.of(
                new OrderItemDto("item1", 2, 5),
                new OrderItemDto("item2", 1, 10)
        );
        orderService.createOrder(new CreateOrderRequest(userId, items));

        OrderEntity savedOrder = orderRepository.findAll().getFirst();

        Order retrievedOrder = orderService.getOrderById(savedOrder.getId(), "alice@test.com");

        assertEquals(savedOrder.getId(), retrievedOrder.getId());
        assertEquals(2, retrievedOrder.getItems().size());
        assertEquals(20.0, retrievedOrder.getTotal());
    }

    @Test
    void shouldRetrieveAllOrdersForUser() {
        orderService.createOrder(new CreateOrderRequest(userId, List.of(
                new OrderItemDto("item1", 2, 5)
        )));
        orderService.createOrder(new CreateOrderRequest(userId, List.of(
                new OrderItemDto("item2", 1, 10)
        )));

        List<Order> result = orderService.getOrdersByUserEmail("alice@test.com");

        assertEquals(2, result.size());
        assertEquals("item1", result.get(0).getItems().getFirst().getProductName());
        assertEquals("item2", result.get(1).getItems().getFirst().getProductName());
    }
}