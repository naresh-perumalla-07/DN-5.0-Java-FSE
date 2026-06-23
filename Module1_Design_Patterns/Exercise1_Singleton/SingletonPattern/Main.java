package SingletonPattern;

/**
 * Test class to demonstrate the Singleton Pattern.
 * 
 * Both log1 and log2 refer to the SAME Logger instance,
 * proving that the Singleton pattern works correctly.
 */
public class Main {
    public static void main(String[] args) {
        // Get two references to the Logger
        Logger log1 = Logger.getInstance();
        Logger log2 = Logger.getInstance();

        // Both should point to the same instance
        log1.log("First log message from log1");
        log2.log("Second log message from log2");

        // Verify both references point to the same object
        System.out.println("\nAre log1 and log2 the same instance?");
        System.out.println("log1 == log2 : " + (log1 == log2));
        System.out.println("log1.hashCode() : " + log1.hashCode());
        System.out.println("log2.hashCode() : " + log2.hashCode());

        // Demonstrate different log levels
        log1.warn("This is a warning message");
        log1.error("This is an error message");
    }
}
