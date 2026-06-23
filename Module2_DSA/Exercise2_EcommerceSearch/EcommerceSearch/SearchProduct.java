package EcommerceSearch;

/**
 * Search algorithms implementation for the E-commerce platform.
 * 
 * Time Complexity Analysis:
 * 
 * Linear Search:
 *   - Best Case:  O(1)   — target found at first position
 *   - Average Case: O(n) — target found in the middle
 *   - Worst Case: O(n)   — target at end or not present
 * 
 * Binary Search:
 *   - Best Case:  O(1)      — target found at mid
 *   - Average Case: O(log n) — halving search space each step
 *   - Worst Case: O(log n)  — target at extremes or not present
 *   - Prerequisite: Array must be SORTED by productId
 */
public class SearchProduct {

    /**
     * Linear Search — scans each element sequentially.
     * Time: O(n), Space: O(1)
     */
    public static Product linearSearch(Product[] products, int targetId) {
        for (Product product : products) {
            if (product.productId == targetId) {
                return product;
            }
        }
        return null; // Not found
    }

    /**
     * Binary Search — divides search space in half each iteration.
     * Time: O(log n), Space: O(1)
     * Requires array to be sorted by productId.
     */
    public static Product binarySearch(Product[] products, int targetId) {
        int low = 0, high = products.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2; // Avoids integer overflow

            if (products[mid].productId == targetId) {
                return products[mid];
            } else if (products[mid].productId < targetId) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null; // Not found
    }
}
