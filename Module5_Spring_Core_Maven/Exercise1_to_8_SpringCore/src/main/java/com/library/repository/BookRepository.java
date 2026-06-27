package com.library.repository;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {

    private final List<String> books = new ArrayList<>();

    public BookRepository() {
        books.add("Spring in Action");
        books.add("Effective Java");
        books.add("Clean Code");
    }

    public List<String> findAll() {
        return new ArrayList<>(books);
    }

    public void addBook(String book) {
        books.add(book);
    }

    public boolean removeBook(String book) {
        return books.remove(book);
    }

    public boolean exists(String book) {
        return books.contains(book);
    }
}
