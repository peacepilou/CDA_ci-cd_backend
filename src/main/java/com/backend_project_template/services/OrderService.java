package com.backend_project_template.services;


import com.backend_project_template.domain.Order;
import com.backend_project_template.domain.OrderItem;
import com.backend_project_template.domain.User;
import com.backend_project_template.exceptions.UserNotFoundException;
import com.backend_project_template.exposition.dtos.CreateOrderRequest;
import com.backend_project_template.mappers.order_items.OrderItemMapper;
import com.backend_project_template.mappers.user.UserMapper;
import com.backend_project_template.persistence.entities.UserEntity;
import com.backend_project_template.persistence.repositories.OrderRepository;
import com.backend_project_template.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    public void createOrder(CreateOrderRequest request) {
        UserEntity userEntity = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException(request.userId()));

        User user = UserMapper.toDomain(userEntity);
        List<OrderItem> items = OrderItemMapper.toDomain(request.items());
        user.placeOrder(items);

        UserEntity updatedUserEntity = UserMapper.toEntity(user);
        userRepository.save(updatedUserEntity);
    }

    public Order getOrderById(Long orderId, String userEmail) {
        UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        User user = UserMapper.toDomain(userEntity);

        return user.getOrderById(orderId);
    }

    public List<Order> getOrdersByUserEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        User user = UserMapper.toDomain(userEntity);

        return user.getOrders();
    }

    public void cancelOrder(Long orderId, String userEmail) {
        UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

       User user = UserMapper.toDomain(userEntity);
       user.cancelOrder(orderId);
       userRepository.save(UserMapper.toEntity(user));
    }
}