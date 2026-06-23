package com.dn5.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit Exercise 3: Assertions in JUnit
 *
 * Demonstrates all major JUnit 5 assertions:
 *  - assertEquals / assertNotEquals
 *  - assertTrue / assertFalse
 *  - assertNull / assertNotNull
 *  - assertThrows
 *  - assertArrayEquals
 *  - assertAll (grouped assertions)
 */
public class AssertionsTest {

    private final Calculator calc = new Calculator();

    @Test
    void testAssertEquals() {
        assertEquals(5, calc.add(2, 3), "2 + 3 should be 5");
        assertNotEquals(4, calc.add(2, 3), "2 + 3 should NOT be 4");
    }

    @Test
    void testAssertTrue() {
        assertTrue(calc.isPositive(5), "5 should be positive");
        assertTrue(calc.isEven(4), "4 should be even");
    }

    @Test
    void testAssertFalse() {
        assertFalse(calc.isPositive(-3), "-3 should not be positive");
        assertFalse(calc.isEven(7), "7 should not be even");
    }

    @Test
    void testAssertNull() {
        assertNull(calc.greet(null), "Greeting null name should return null");
        assertNull(calc.greet(""), "Greeting empty name should return null");
    }

    @Test
    void testAssertNotNull() {
        assertNotNull(calc.greet("Naresh"), "Greeting valid name should not be null");
        assertEquals("Hello, Naresh!", calc.greet("Naresh"));
    }

    @Test
    void testAssertThrows() {
        // Verify that dividing by zero throws ArithmeticException
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> calc.divide(10, 0),
            "Division by zero should throw ArithmeticException"
        );
        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    @Test
    void testAssertArrayEquals() {
        int[] expected = {2, 4, 6};
        int[] actual = {
            calc.add(1, 1),
            calc.add(2, 2),
            calc.add(3, 3)
        };
        assertArrayEquals(expected, actual, "Arrays should be equal");
    }

    @Test
    void testAssertAll() {
        // Grouped assertions — all run even if one fails
        assertAll("Calculator operations",
            () -> assertEquals(5, calc.add(2, 3)),
            () -> assertEquals(6, calc.subtract(10, 4)),
            () -> assertEquals(21, calc.multiply(3, 7)),
            () -> assertEquals(5.0, calc.divide(10, 2))
        );
    }
}
