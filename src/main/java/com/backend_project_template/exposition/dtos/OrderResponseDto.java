package com.backend_project_template.exposition.dtos;

import com.backend_project_template.persistence.entities.OrderStatus;

import java.time.Instant;
import java.util.List;

public record OrderResponseDto(
        Long id,
        Instant orderDate,
        OrderStatus status,
        List<OrderItemDto> items,
        double total
) {
}
