package com.dn5.calculator;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Exercise 4: Arrange-Act-Assert (AAA) Pattern,
 * Test Fixtures, Setup and Teardown Methods
 *
 * Demonstrates:
 *  - AAA pattern in each test
 *  - @BeforeAll / @AfterAll — run once per test class
 *  - @BeforeEach / @AfterEach — run before/after every test
 *  - Test fixtures (shared setup via instance fields)
 */
public class AAAPatternTest {

    private Calculator calc;

    // ========== Setup & Teardown ==========

    @BeforeAll
    static void setupAll() {
        System.out.println(">>> @BeforeAll: Test suite starting...");
    }

    @AfterAll
    static void teardownAll() {
        System.out.println(">>> @AfterAll: Test suite finished.");
    }

    @BeforeEach
    void setup() {
        // Create a fresh Calculator instance before each test
        calc = new Calculator();
        System.out.println("  @BeforeEach: New Calculator created");
    }

    @AfterEach
    void teardown() {
        // Clean up after each test
        calc = null;
        System.out.println("  @AfterEach: Calculator cleaned up");
    }

    // ========== AAA Pattern Tests ==========

    @Test
    @DisplayName("Test addition using AAA pattern")
    void testAdditionAAA() {
        // Arrange
        int a = 10;
        int b = 20;
        int expected = 30;

        // Act
        int actual = calc.add(a, b);

        // Assert
        assertEquals(expected, actual, "Addition of 10 + 20 should be 30");
    }

    @Test
    @DisplayName("Test subtraction using AAA pattern")
    void testSubtractionAAA() {
        // Arrange
        int a = 50;
        int b = 15;
        int expected = 35;

        // Act
        int actual = calc.subtract(a, b);

        // Assert
        assertEquals(expected, actual, "Subtraction of 50 - 15 should be 35");
    }

    @Test
    @DisplayName("Test division with exception using AAA pattern")
    void testDivisionByZeroAAA() {
        // Arrange
        int numerator = 10;
        int denominator = 0;

        // Act & Assert
        assertThrows(ArithmeticException.class, () -> {
            calc.divide(numerator, denominator);
        }, "Division by zero should throw ArithmeticException");
    }

    @Test
    @DisplayName("Test isEven using AAA pattern")
    void testIsEvenAAA() {
        // Arrange
        int evenNumber = 42;
        int oddNumber = 17;

        // Act
        boolean resultEven = calc.isEven(evenNumber);
        boolean resultOdd = calc.isEven(oddNumber);

        // Assert
        assertTrue(resultEven, "42 should be even");
        assertFalse(resultOdd, "17 should not be even");
    }

    @Test
    @DisplayName("Test greeting using AAA pattern")
    void testGreetingAAA() {
        // Arrange
        String name = "Cognizant";
        String expectedGreeting = "Hello, Cognizant!";

        // Act
        String actual = calc.greet(name);

        // Assert
        assertNotNull(actual, "Greeting should not be null");
        assertEquals(expectedGreeting, actual);
    }
}
