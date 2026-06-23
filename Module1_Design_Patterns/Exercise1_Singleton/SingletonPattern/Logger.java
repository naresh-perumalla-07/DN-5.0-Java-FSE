package SingletonPattern;

/**
 * Exercise 1: Implementing the Singleton Pattern
 * 
 * Scenario: You are tasked with implementing a logging utility for an application.
 * Use the Singleton Pattern to ensure only one instance of the Logger exists.
 */
public class Logger {
    // Single instance - volatile for thread safety
    private static volatile Logger instance;

    // Private constructor prevents external instantiation
    private Logger() {
        System.out.println("Logger initialized.");
    }

    // Double-checked locking for thread-safe lazy initialization
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[LOG] " + message);
    }

    public void warn(String message) {
        System.out.println("[WARN] " + message);
    }

    public void error(String message) {
        System.out.println("[ERROR] " + message);
    }
}
