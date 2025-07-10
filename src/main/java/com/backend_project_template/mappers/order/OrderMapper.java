package com.backend_project_template.mappers.order;

import com.backend_project_template.domain.Order;
import com.backend_project_template.domain.OrderItem;
import com.backend_project_template.domain.User;
import com.backend_project_template.exposition.dtos.OrderItemDto;
import com.backend_project_template.exposition.dtos.OrderResponseDto;
import com.backend_project_template.persistence.entities.OrderEntity;
import com.backend_project_template.persistence.entities.OrderItemEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record OrderMapper() {
  public static OrderResponseDto toDto(Order order) {
    List<OrderItemDto> items = order
      .getItems()
      .stream()
      .map(item -> new OrderItemDto(item.getProductName(), item.getQuantity(), item.getUnitPrice()))
      .toList();
    return new OrderResponseDto(order.getId(), order.getOrderDate(), order.getStatus(), items, order.getTotal());
  }

  public static Order toDomain(OrderEntity entity, User user) {
    List<OrderItem> items = entity
      .getItems()
      .stream()
      .map(item -> new OrderItem(item.getProductName(), item.getQuantity(), item.getUnitPrice()))
      .toList();

    Order order = new Order(user, items, entity.getOrderDate(), entity.getStatus());
    order.setId(entity.getId());
    return order;
  }

  public static OrderEntity toEntity(Order order) {
    List<OrderItemEntity> itemEntities = order
      .getItems()
      .stream()
      .map(item -> new OrderItemEntity(item.getProductName(), item.getQuantity(), item.getUnitPrice(), null))
      .collect(Collectors.toCollection(ArrayList::new));

    return new OrderEntity(itemEntities, null, order.getStatus(), order.getOrderDate());
  }
}
