package com.dn5.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SLF4J Exercise 1: Logging Error Messages and Warning Levels
 *
 * Demonstrates all SLF4J logging levels:
 *   TRACE < DEBUG < INFO < WARN < ERROR
 *
 * Also demonstrates parameterized logging (avoids string concatenation cost).
 */
public class LoggingExample {

    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public static void main(String[] args) {
        // Basic logging at different levels
        logger.error("This is an ERROR message — something went wrong!");
        logger.warn("This is a WARN message — potential issue detected");
        logger.info("This is an INFO message — application started successfully");
        logger.debug("This is a DEBUG message — variable x = {}", 42);
        logger.trace("This is a TRACE message — entering method calculate()");

        // Parameterized logging (SLF4J best practice)
        String userName = "Naresh";
        int itemCount = 5;
        logger.info("User '{}' added {} items to cart", userName, itemCount);

        // Logging exceptions
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            logger.error("Division by zero error occurred", e);
        }

        // Conditional logging
        if (logger.isDebugEnabled()) {
            logger.debug("Debug mode is enabled — additional diagnostics available");
        }

        // Simulating business logic with logging
        processOrder("ORD-001", 250.75);
    }

    public static void processOrder(String orderId, double amount) {
        logger.info("Processing order: {} with amount: ${}", orderId, amount);

        if (amount > 1000) {
            logger.warn("High-value order detected: {} (${}) — requires manual review", orderId, amount);
        }

        if (amount <= 0) {
            logger.error("Invalid order amount for {}: ${}", orderId, amount);
            return;
        }

        logger.info("Order {} processed successfully", orderId);
    }
}
