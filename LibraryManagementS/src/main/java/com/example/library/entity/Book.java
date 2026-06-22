package com.example.library.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * Book entity representing a book in the library.
 * Each book can have multiple borrow records.
 */
@Entity
@Table(name = "books")
public class Book {
    // Primary key, auto-generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Required field: book title
    @Column(nullable = false)
    private String title;

    // Required field: book author
    @Column(nullable = false)
    private String author;

    // Required field: ISBN, must be unique
    @Column(nullable = false, unique = true)
    private String isbn;

    // Total number of copies in the library
    @Column(nullable = false)
    private int totalCopies;

    // Number of copies currently available for borrowing
    @Column(nullable = false)
    private int availableCopies;

    // One-to-many relationship with BorrowRecord
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowRecord> borrowRecords;

    // Default constructor for JPA
    public Book() {
    }

    // Constructor for creating a new book
    public Book(String title, String author, String isbn, int totalCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies; // Initially all copies are available
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

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public List<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    // Setters for fields that can be updated
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }
}

