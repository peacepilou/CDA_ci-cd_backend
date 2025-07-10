package com.backend_project_template.domain;

import com.backend_project_template.exceptions.OrderItemDomainException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {

  private String productName;
  private int quantity;
  private double unitPrice;

  public OrderItem(String productName, int quantity, double unitPrice) {
    if (productName == null || productName.isBlank()) {
      throw new OrderItemDomainException("Product name must not be blank");
    }
    if (quantity <= 0) {
      throw new OrderItemDomainException("Quantity must be > 0");
    }
    if (unitPrice < 0) {
      throw new OrderItemDomainException("Unit price must be >= 0");
    }
    this.productName = productName;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public double totalPrice() {
    return quantity * unitPrice;
  }
}
