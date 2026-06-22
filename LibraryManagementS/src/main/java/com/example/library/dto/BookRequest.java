package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * BookRequest DTO for creating a new book.
 * Used in API request bodies.
 */
public class BookRequest {
    // Required field: book title
    @NotBlank(message = "Title cannot be blank")
    private String title;

    // Required field: book author
    @NotBlank(message = "Author cannot be blank")
    private String author;

    // Required field: ISBN
    @NotBlank(message = "ISBN cannot be blank")
    private String isbn;

    // Required field: total copies (must be positive)
    @Positive(message = "Total copies must be positive")
    private int totalCopies;

    // Default constructor for deserialization
    public BookRequest() {
    }

    // Constructor with parameters
    public BookRequest(String title, String author, String isbn, int totalCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }
}

