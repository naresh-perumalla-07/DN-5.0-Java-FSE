package com.microservices.order.service;

import com.microservices.order.client.ProductClient;
import com.microservices.order.dto.OrderRequest;
import com.microservices.order.dto.OrderResponse;
import com.microservices.order.dto.ProductResponse;
import com.microservices.order.exception.ResourceNotFoundException;
import com.microservices.order.model.Order;
import com.microservices.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository repository;
    private final ProductClient productClient;

    public OrderService(OrderRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    public List<OrderResponse> getAllOrders() {
        return repository.findAll().stream()
                .map(this::enrichOrderResponse)
                .toList();
    }

    public OrderResponse getOrderById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return enrichOrderResponse(order);
    }

    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creating order for product id: {}", request.getProductId());
        ProductResponse product = productClient.getProductById(request.getProductId());

        double totalPrice = product.getPrice() * request.getQuantity();

        Order order = new Order(
                request.getProductId(),
                request.getQuantity(),
                totalPrice,
                "PLACED"
        );

        Order saved = repository.save(order);
        OrderResponse response = new OrderResponse(saved);
        response.setProductName(product.getName());
        return response;
    }

    public OrderResponse updateOrderStatus(Long id, String status) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        order.setStatus(status);
        return enrichOrderResponse(repository.save(order));
    }

    public void deleteOrder(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private OrderResponse enrichOrderResponse(Order order) {
        OrderResponse response = new OrderResponse(order);
        try {
            ProductResponse product = productClient.getProductById(order.getProductId());
            response.setProductName(product.getName());
        } catch (Exception e) {
            response.setProductName("Unknown Product");
        }
        return response;
    }
}
