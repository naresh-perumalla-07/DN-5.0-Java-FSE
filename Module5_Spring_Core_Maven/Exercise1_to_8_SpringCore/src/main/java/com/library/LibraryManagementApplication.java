package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LibraryManagementApplication {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("=== Exercise 1 & 5: Spring IoC Container ===");
        System.out.println("Spring context loaded successfully.\n");

        // Exercise 2: Setter injection (XML-based)
        System.out.println("=== Exercise 2: Setter Injection ===");
        BookService setterService = (BookService) context.getBean("bookServiceSetter");
        System.out.println("Books (setter-injected): " + setterService.getAllBooks());

        // Exercise 7: Constructor injection (XML-based)
        System.out.println("\n=== Exercise 7: Constructor Injection ===");
        BookService constructorService = (BookService) context.getBean("bookServiceConstructor");
        System.out.println("Books (constructor-injected): " + constructorService.getAllBooks());

        // Exercise 6: Annotation-based bean (component scan)
        System.out.println("\n=== Exercise 6: Annotation-based Configuration ===");
        BookService annotatedService = (BookService) context.getBean("bookService");
        annotatedService.addBook("Design Patterns");
        System.out.println("Books after add: " + annotatedService.getAllBooks());

        // Exercise 3 & 8: AOP logging is automatically triggered
        System.out.println("\n=== Exercise 3 & 8: AOP Logging ===");
        annotatedService.removeBook("Clean Code");
        System.out.println("Books after remove: " + annotatedService.getAllBooks());

        ((ClassPathXmlApplicationContext) context).close();
    }
}
