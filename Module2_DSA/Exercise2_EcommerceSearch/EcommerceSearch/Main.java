package EcommerceSearch;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Exercise 2: E-commerce Platform Search Function
 * 
 * Demonstrates Linear Search vs Binary Search on product data.
 * 
 * Analysis:
 * - Linear Search is suitable for small or unsorted datasets.
 * - Binary Search is more suitable for large, sorted datasets (O(log n)).
 * - For an e-commerce platform with millions of products, Binary Search
 *   (or hash-based lookup) is strongly preferred.
 */
public class Main {
    public static void main(String[] args) {
        Product[] products = {
            new Product(101, "Laptop", "Electronics"),
            new Product(205, "Shirt", "Clothing"),
            new Product(150, "Watch", "Accessories"),
            new Product(303, "Mobile", "Electronics"),
            new Product(89,  "Shoes", "Footwear"),
            new Product(420, "Headphones", "Electronics"),
            new Product(55,  "Backpack", "Accessories")
        };

        // Sort array by productId for binary search
        Arrays.sort(products, Comparator.comparingInt(p -> p.productId));

        System.out.println("Sorted Products:");
        for (Product p : products) {
            System.out.println("  " + p);
        }

        int searchId = 150;
        System.out.println("\n--- Searching for Product ID: " + searchId + " ---");

        // Linear Search
        long start = System.nanoTime();
        Product result1 = SearchProduct.linearSearch(products, searchId);
        long linearTime = System.nanoTime() - start;
        System.out.println("\nLinear Search Result: " + (result1 != null ? result1 : "Product not found"));
        System.out.println("Linear Search Time: " + linearTime + " ns");

        // Binary Search
        start = System.nanoTime();
        Product result2 = SearchProduct.binarySearch(products, searchId);
        long binaryTime = System.nanoTime() - start;
        System.out.println("\nBinary Search Result: " + (result2 != null ? result2 : "Product not found"));
        System.out.println("Binary Search Time: " + binaryTime + " ns");

        // Analysis
        System.out.println("\n--- Complexity Analysis ---");
        System.out.println("Linear Search: O(n)     — Best for small/unsorted data");
        System.out.println("Binary Search: O(log n) — Best for large/sorted data");
        System.out.println("For an e-commerce platform, Binary Search is preferred for its efficiency.");

        // Test with non-existent product
        System.out.println("\n--- Searching for non-existent Product ID: 999 ---");
        Product result3 = SearchProduct.linearSearch(products, 999);
        Product result4 = SearchProduct.binarySearch(products, 999);
        System.out.println("Linear Search: " + (result3 != null ? result3 : "Not found"));
        System.out.println("Binary Search: " + (result4 != null ? result4 : "Not found"));
    }
}
