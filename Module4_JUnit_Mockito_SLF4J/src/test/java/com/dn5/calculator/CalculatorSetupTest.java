package com.dn5.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Exercise 1: Setting Up JUnit
 *
 * Demonstrates:
 *  - Creating a test class
 *  - Writing basic @Test methods
 *  - Running tests with JUnit 5
 */
public class CalculatorSetupTest {

    @Test
    void testAddition() {
        Calculator calc = new Calculator();
        int result = calc.add(2, 3);
        assertEquals(5, result, "2 + 3 should equal 5");
    }

    @Test
    void testSubtraction() {
        Calculator calc = new Calculator();
        int result = calc.subtract(10, 4);
        assertEquals(6, result, "10 - 4 should equal 6");
    }

    @Test
    void testMultiplication() {
        Calculator calc = new Calculator();
        int result = calc.multiply(3, 7);
        assertEquals(21, result, "3 * 7 should equal 21");
    }

    @Test
    void testDivision() {
        Calculator calc = new Calculator();
        double result = calc.divide(10, 2);
        assertEquals(5.0, result, "10 / 2 should equal 5.0");
    }
}
