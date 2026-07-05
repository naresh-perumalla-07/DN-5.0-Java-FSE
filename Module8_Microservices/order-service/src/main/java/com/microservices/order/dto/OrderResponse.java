package com.microservices.order.dto;

import com.microservices.order.model.Order;
import java.time.LocalDateTime;

public class OrderResponse {

    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private double totalPrice;
    private String status;
    private LocalDateTime orderDate;

    public OrderResponse() {}

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.productId = order.getProductId();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.orderDate = order.getOrderDate();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
}
