package com.example.library.dto;

import com.example.library.entity.Book;

/**
 * BookResponse DTO for returning book data in API responses.
 * Maps Book entity data to a flattened structure.
 */
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private int availableCopies;

    // Default constructor for deserialization
    public BookResponse() {
    }

    // Constructor that takes a Book entity and maps its fields
    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.isbn = book.getIsbn();
        this.availableCopies = book.getAvailableCopies();
    }

    // Constructor with all parameters
    public BookResponse(Long id, String title, String author, String isbn, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.availableCopies = availableCopies;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }
}

