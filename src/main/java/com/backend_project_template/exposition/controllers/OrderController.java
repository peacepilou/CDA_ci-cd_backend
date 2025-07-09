package com.backend_project_template.exposition.controllers;


import com.backend_project_template.domain.Order;
import com.backend_project_template.exposition.dtos.CreateOrderRequest;

import com.backend_project_template.exposition.dtos.OrderResponseDto;
import com.backend_project_template.mappers.order.OrderMapper;
import com.backend_project_template.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id, @RequestParam String userEmail) {
        Order order = orderService.getOrderById(id, userEmail);
        OrderResponseDto response = OrderMapper.toDto(order);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/by-email")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUserEmail(@RequestParam String email) {
        List<Order> orders = orderService.getOrdersByUserEmail(email);
        List<OrderResponseDto> response = orders.stream()
                .map(OrderMapper::toDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id, @RequestParam String userEmail) {
        orderService.cancelOrder(id, userEmail);
        return ResponseEntity.noContent().build();
    }

}