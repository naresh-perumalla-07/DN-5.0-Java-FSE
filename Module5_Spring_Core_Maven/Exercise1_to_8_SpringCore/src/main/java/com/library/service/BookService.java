package com.library.service;

import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    // Default constructor
    public BookService() {}

    // Exercise 7: Constructor injection
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Exercise 2 & 7: Setter injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<String> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addBook(String book) {
        bookRepository.addBook(book);
        System.out.println("Book added: " + book);
    }

    public void removeBook(String book) {
        if (bookRepository.removeBook(book)) {
            System.out.println("Book removed: " + book);
        } else {
            System.out.println("Book not found: " + book);
        }
    }
}
