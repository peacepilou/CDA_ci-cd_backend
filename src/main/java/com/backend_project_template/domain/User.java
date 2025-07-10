package com.backend_project_template.domain;

import com.backend_project_template.exceptions.UserDomainException;
import com.backend_project_template.persistence.entities.OrderStatus;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

  private Long id;
  private String name;
  private String email;
  private String password;

  private List<Order> orders = new ArrayList<>();

  public User(Long id, String name, String email) {
    validate(name, email);
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public User(String name, String email, String password) {
    validate(name, email);
    this.name = name;
    this.email = email;
    this.password = password;
  }

  private void validate(String name, String email) {
    if (name == null || name.isBlank()) {
      throw new UserDomainException("User name must not be blank");
    }
    if (email == null || !email.contains("@")) {
      throw new UserDomainException("Email is invalid");
    }
  }

  public void placeOrder(List<OrderItem> items) {
    Order order = new Order(this, items, Instant.now(), OrderStatus.PENDING);
    this.orders.add(order);
  }

  public Order getOrderById(Long orderId) {
    return orders
      .stream()
      .filter(order -> order.getId().equals(orderId))
      .findFirst()
      .orElseThrow(() -> new UserDomainException("Order not found for ID: " + orderId));
  }

  public void cancelOrder(Long orderId) {
    getOrderById(orderId).cancel();
  }
}
