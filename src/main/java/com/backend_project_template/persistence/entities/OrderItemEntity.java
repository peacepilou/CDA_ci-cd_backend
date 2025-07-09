package com.backend_project_template.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "`order_item`") // "order" est un mot réservé en SQL
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity {

  @Id
  @GeneratedValue
  private Long id;

  private String productName;
  private int quantity;
  private double unitPrice;

  @ManyToOne
  @JoinColumn(name = "order_id")
  @Setter
  private OrderEntity order;

  public OrderItemEntity(String productName, int quantity, double unitPrice, OrderEntity order) {
    this.productName = productName;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.order = order;
  }
}
