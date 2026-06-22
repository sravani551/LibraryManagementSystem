package com.example.library.controller;

import com.example.library.dto.BookRequest;
import com.example.library.dto.BookResponse;
import com.example.library.entity.Book;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for book-related endpoints.
 * Handles HTTP requests for book operations.
 * this class handles controller.
 */
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    // Constructor-based dependency injection
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Create a new book.
     * @param request the book request data with validation
     * @return response entity with created book data
     */
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        Book book = bookService.createBook(request);
        return ResponseEntity.ok(new BookResponse(book));
    }

    /**
     * Search for books by title with pagination.
     * @param title optional title to search for (case-insensitive)
     * @param page the page number (0-indexed), default 0
     * @param size the page size, default 10
     * @return response entity with paginated book data
     */
    @GetMapping
    public ResponseEntity<Page<BookResponse>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Page<Book> bookPage = bookService.searchBooks(title, page, size);
        
        // Convert Book entities to BookResponse DTOs
        List<BookResponse> responses = bookPage.getContent()
                .stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
        
        // Create a new Page with the converted DTOs
        Page<BookResponse> responsePage = new PageImpl<>(responses, bookPage.getPageable(), bookPage.getTotalElements());
        
        return ResponseEntity.ok(responsePage);
    }
}

