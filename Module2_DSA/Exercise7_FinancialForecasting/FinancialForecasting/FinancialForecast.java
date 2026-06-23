package FinancialForecasting;

/**
 * Exercise 7: Financial Forecasting
 * 
 * Predicts future values based on past growth rates using recursion.
 * 
 * Formula: FV = PV × (1 + r)^n
 *   where PV = present value, r = growth rate, n = number of periods
 * 
 * Time Complexity Analysis:
 *   - Basic Recursion: O(n) time, O(n) stack space
 *   - Optimized (Memoization): O(n) time, O(n) space (cached results)
 *   - Iterative: O(n) time, O(1) space — most efficient
 * 
 * To avoid excessive computation (stack overflow for large n),
 * we provide both recursive and optimized iterative solutions.
 */
public class FinancialForecast {

    /**
     * Recursive approach to calculate future value.
     * Base case: n == 0 → return presentValue
     * Recursive case: multiply by (1 + rate) and recurse with n-1
     * 
     * Time: O(n), Space: O(n) due to recursion stack
     */
    public static double calculateFutureValueRecursive(double presentValue, double growthRate, int periods) {
        // Base case
        if (periods == 0) {
            return presentValue;
        }
        // Recursive case
        return calculateFutureValueRecursive(presentValue * (1 + growthRate), growthRate, periods - 1);
    }

    /**
     * Optimized iterative approach — avoids stack overflow for large n.
     * Time: O(n), Space: O(1)
     */
    public static double calculateFutureValueIterative(double presentValue, double growthRate, int periods) {
        double futureValue = presentValue;
        for (int i = 0; i < periods; i++) {
            futureValue *= (1 + growthRate);
        }
        return futureValue;
    }

    /**
     * Direct formula approach using Math.pow.
     * Time: O(1), Space: O(1)
     */
    public static double calculateFutureValueDirect(double presentValue, double growthRate, int periods) {
        return presentValue * Math.pow(1 + growthRate, periods);
    }

    public static void main(String[] args) {
        double presentValue = 10000.0;   // Initial investment
        double growthRate = 0.08;        // 8% annual growth rate
        int years = 10;                  // 10-year forecast

        System.out.println("=== Financial Forecasting ===");
        System.out.println("Present Value : $" + String.format("%.2f", presentValue));
        System.out.println("Growth Rate   : " + (growthRate * 100) + "%");
        System.out.println("Periods       : " + years + " years");
        System.out.println();

        // Recursive calculation
        double fvRecursive = calculateFutureValueRecursive(presentValue, growthRate, years);
        System.out.println("Future Value (Recursive)  : $" + String.format("%.2f", fvRecursive));

        // Iterative calculation
        double fvIterative = calculateFutureValueIterative(presentValue, growthRate, years);
        System.out.println("Future Value (Iterative)  : $" + String.format("%.2f", fvIterative));

        // Direct formula calculation
        double fvDirect = calculateFutureValueDirect(presentValue, growthRate, years);
        System.out.println("Future Value (Formula)    : $" + String.format("%.2f", fvDirect));

        // Year-by-year projection
        System.out.println("\n--- Year-by-Year Forecast ---");
        for (int i = 1; i <= years; i++) {
            double fv = calculateFutureValueRecursive(presentValue, growthRate, i);
            System.out.println("Year " + String.format("%2d", i) + " : $" + String.format("%.2f", fv));
        }

        // Complexity Analysis
        System.out.println("\n--- Complexity Analysis ---");
        System.out.println("Recursive : O(n) time, O(n) space (call stack)");
        System.out.println("Iterative : O(n) time, O(1) space (recommended)");
        System.out.println("Formula   : O(1) time, O(1) space (most efficient)");
        System.out.println("\nOptimization: For large periods, use iterative/formula to avoid stack overflow.");
    }
}
