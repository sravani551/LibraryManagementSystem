package com.example.library.dto;

import com.example.library.entity.BorrowRecord;
import java.time.LocalDate;

/**
 * BorrowRecordResponse DTO for returning borrow record data in API responses.
 * Maps BorrowRecord entity data to a flattened structure,
 * avoiding nested entity objects to prevent infinite recursion.
 */
public class BorrowRecordResponse {
    private Long id;
    private String memberName;
    private String bookTitle;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    // Default constructor for deserialization
    public BorrowRecordResponse() {
    }

    // Constructor that takes a BorrowRecord entity and flattens its data
    public BorrowRecordResponse(BorrowRecord borrowRecord) {
        this.id = borrowRecord.getId();
        this.memberName = borrowRecord.getMember().getName();
        this.bookTitle = borrowRecord.getBook().getTitle();
        this.borrowDate = borrowRecord.getBorrowDate();
        this.returnDate = borrowRecord.getReturnDate();
    }

    // Constructor with all parameters
    public BorrowRecordResponse(Long id, String memberName, String bookTitle, LocalDate borrowDate, LocalDate returnDate) {
        this.id = id;
        this.memberName = memberName;
        this.bookTitle = bookTitle;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}

