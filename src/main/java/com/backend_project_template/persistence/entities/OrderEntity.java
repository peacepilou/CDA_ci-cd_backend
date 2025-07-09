package com.backend_project_template.persistence.entities;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "`orders`") // "order" est un mot réservé en SQL
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

  @Id
  @GeneratedValue
  private Long id;

  private Instant orderDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @Setter
  private UserEntity userEntity;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItemEntity> items;

  public OrderEntity(List<OrderItemEntity> items, UserEntity userEntity, OrderStatus status, Instant orderDate) {
    this.items = items != null ? items : new ArrayList<>();
    this.userEntity = userEntity;
    this.status = status;
    this.orderDate = orderDate;
    this.items.forEach(item -> item.setOrder(this));
  }

  public boolean isOwnedBy(String userEmail) {
    return userEntity.getEmail().equalsIgnoreCase(userEmail);
  }
}
