package com.example.library.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Member entity representing a library member.
 * Each member can borrow multiple books.
 */
@Entity
@Table(name = "members")
public class Member {
    // Primary key, auto-generated
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Required field: member name
    @Column(nullable = false)
    private String name;

    // Required field: email (must be unique)
    @Column(nullable = false, unique = true)
    private String email;

    // Membership date, set automatically on creation, never updatable
    @Column(nullable = false, updatable = false)
    private LocalDate membershipDate;

    // One-to-many relationship with BorrowRecord
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowRecord> borrowRecords;

    // Default constructor for JPA
    public Member() {
    }

    // Constructor for creating a new member
    public Member(String name, String email) {
        this.name = name;
        this.email = email;
        this.membershipDate = LocalDate.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public List<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    // Setter for name (can be updated after creation)
    public void setName(String name) {
        this.name = name;
    }

    // Setter for email (can be updated after creation)
    public void setEmail(String email) {
        this.email = email;
    }
}

