package com.api.controller;

import com.api.model.Book;
import com.api.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Exercise 1 & 2: GET all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    // Exercise 2: GET by ID with @PathVariable
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    // Exercise 3: POST with validation
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book saved = bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Exercise 3: PUT with validation
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        return bookService.update(id, book);
    }

    // Exercise 3: DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok("Book deleted: " + id);
    }

    // Exercise 2: GET with query parameter
    @GetMapping("/search")
    public List<Book> search(@RequestParam(required = false) String title) {
        if (title != null) {
            return bookService.findAll().stream()
                    .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .toList();
        }
        return bookService.findAll();
    }
}
