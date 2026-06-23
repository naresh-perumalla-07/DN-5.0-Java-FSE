package EcommerceSearch;

/**
 * Exercise 2: E-commerce Platform Search Function
 * 
 * Product class with attributes for searching:
 * productId, productName, and category.
 */
public class Product {
    int productId;
    String productName;
    String category;

    public Product(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{id=" + productId + ", name='" + productName + "', category='" + category + "'}";
    }
}
