package com.microservices.order.client;

import com.microservices.order.dto.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProductClientFallback implements ProductClient {

    private static final Logger log = LoggerFactory.getLogger(ProductClientFallback.class);

    @Override
    public ProductResponse getProductById(Long id) {
        log.warn("Fallback triggered for product id: {}", id);
        ProductResponse fallback = new ProductResponse();
        fallback.setId(id);
        fallback.setName("Product Unavailable");
        fallback.setDescription("Product Service is currently unavailable");
        fallback.setPrice(0.0);
        fallback.setQuantity(0);
        return fallback;
    }
}
