package com.example.library.service;

import com.example.library.dto.BookRequest;
import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Book operations.
 * Uses constructor-based dependency injection.
 */
@Service
public class BookService {
    private final BookRepository bookRepository;

    // Constructor-based dependency injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Create a new book.
     * Sets availableCopies equal to totalCopies.
     * @param request the book request data
     * @return the created book
     */
    public Book createBook(BookRequest request) {
        // Create new book with availableCopies = totalCopies
        Book book = new Book(request.getTitle(), request.getAuthor(), 
                             request.getIsbn(), request.getTotalCopies());
        return bookRepository.save(book);
    }

    /**
     * Search for books by title with pagination.
     * If title is empty or null, returns all books.
     * @param title the title to search for (optional)
     * @param page the page number (0-indexed)
     * @param size the page size
     * @return a page of books
     */
    public Page<Book> searchBooks(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // If title is provided and not empty, search by title
        if (title != null && !title.trim().isEmpty()) {
            return bookRepository.findByTitleContainingIgnoreCase(title, pageable);
        }

        // Otherwise return all books
        return bookRepository.findAll(pageable);
    }
}

