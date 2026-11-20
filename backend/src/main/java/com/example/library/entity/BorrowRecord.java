package com.example.library.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * BorrowRecord entity representing a book borrow transaction.
 * Records when a member borrows a book and when it's returned.
 */
@Entity
@Table(name = "borrow_records")
public class BorrowRecord {
    // Primary key, auto-generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-one relationship with Member
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // Many-to-one relationship with Book
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // Date when the book was borrowed, set on creation
    @Column(nullable = false, updatable = false)
    private LocalDate borrowDate;

    // Date when the book was returned (nullable until book is returned)
    @Column(nullable = true, updatable = true)
    private LocalDate returnDate;

    // Default constructor for JPA
    public BorrowRecord() {
    }

    // Constructor for creating a new borrow record
    public BorrowRecord(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.returnDate = null; // Initially no return date
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    // Setter for returnDate (updated when book is returned)
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}

