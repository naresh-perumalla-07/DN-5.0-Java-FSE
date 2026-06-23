package com.dn5.logging;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SLF4J Exercise 1: Logging Error Messages and Warning Levels
 *
 * Demonstrates:
 *  - Creating a logger with LoggerFactory
 *  - Logging at ERROR, WARN, INFO, DEBUG levels
 *  - Parameterized logging (avoids string concatenation overhead)
 *  - Logging exceptions with stack traces
 *  - Verifying logger is not null and correct class
 */
public class LoggingTest {

    private static final Logger logger = LoggerFactory.getLogger(LoggingTest.class);

    @Test
    void testLoggerCreation() {
        // Verify logger is properly created
        assertNotNull(logger, "Logger should not be null");
    }

    @Test
    void testErrorLogging() {
        // This should output to console and app.log
        logger.error("Test ERROR: Something critical happened!");
        // No assertion needed — just verify no exception thrown
    }

    @Test
    void testWarnLogging() {
        logger.warn("Test WARN: Potential issue detected in module {}", "Payment");
    }

    @Test
    void testInfoLogging() {
        logger.info("Test INFO: User {} logged in successfully", "admin");
    }

    @Test
    void testDebugLogging() {
        logger.debug("Test DEBUG: Processing item #{} with value={}", 42, 99.5);
    }

    @Test
    void testParameterizedLogging() {
        // SLF4J best practice: use {} placeholders instead of string concatenation
        String user = "Naresh";
        int attempts = 3;
        logger.info("User '{}' attempted login {} times", user, attempts);
        // This is more efficient than: logger.info("User '" + user + "' attempted...")
    }

    @Test
    void testExceptionLogging() {
        try {
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            // Log exception with full stack trace
            logger.error("Arithmetic error occurred during calculation", e);
        }
    }

    @Test
    void testConditionalLogging() {
        // Check if debug is enabled before expensive operations
        if (logger.isDebugEnabled()) {
            logger.debug("Conditional debug: expensive operation result = {}", expensiveComputation());
        }
        assertTrue(true, "Conditional logging should not throw");
    }

    private String expensiveComputation() {
        return "computed-value-" + System.currentTimeMillis();
    }
}
