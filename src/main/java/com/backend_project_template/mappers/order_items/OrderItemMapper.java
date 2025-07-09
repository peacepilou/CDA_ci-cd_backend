package com.backend_project_template.mappers.order_items;

import com.backend_project_template.domain.OrderItem;
import com.backend_project_template.exposition.dtos.OrderItemDto;

import java.util.List;

public class OrderItemMapper {

    public static List<OrderItem> toDomain(List<OrderItemDto> dtos) {
        return dtos.stream()
                .map(dto -> new OrderItem(dto.productName(), dto.quantity(), dto.unitPrice()))
                .toList();
    }
}
