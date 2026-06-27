package com.api.controller;

import com.api.model.Book;
import com.api.repository.BookRepository;
import com.api.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private String token;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        Book book = new Book("Clean Code", "Robert Martin", "978-0132350", 39.99);
        bookRepository.save(book);
        token = jwtUtil.generateToken("admin");
    }

    @Test
    void getAllBooks_returnsOk() throws Exception {
        mockMvc.perform(get("/api/books")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Clean Code"));
    }

    @Test
    void getBookById_returnsBook() throws Exception {
        Long id = bookRepository.findAll().get(0).getId();
        mockMvc.perform(get("/api/books/" + id)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("Robert Martin"));
    }

    @Test
    void createBook_withValidData_returnsCreated() throws Exception {
        String json = "{\"title\":\"Effective Java\",\"author\":\"Joshua Bloch\",\"isbn\":\"978-013\",\"price\":45.0}";
        mockMvc.perform(post("/api/books")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }

    @Test
    void createBook_withInvalidData_returnsBadRequest() throws Exception {
        String json = "{\"title\":\"\",\"author\":\"\"}";
        mockMvc.perform(post("/api/books")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void unauthorizedAccess_returns401() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isForbidden());
    }
}
