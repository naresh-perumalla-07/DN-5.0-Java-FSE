package com.dn5.calculator;

/**
 * Simple Calculator class used for JUnit Exercise 1 (Setting Up JUnit)
 * and Exercise 3 (Assertions).
 */
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return (double) a / b;
    }

    public boolean isEven(int number) {
        return number % 2 == 0;
    }

    public boolean isPositive(int number) {
        return number > 0;
    }

    public String greet(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return "Hello, " + name + "!";
    }
}
