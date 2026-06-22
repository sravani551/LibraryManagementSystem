package com.example.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * MemberRequest DTO for creating or updating a member.
 * Used in API request bodies.
 */
public class MemberRequest {
    // Required field: member name
    @NotBlank(message = "Name cannot be blank")
    private String name;

    // Required field: valid email address
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    // Default constructor for deserialization
    public MemberRequest() {
    }

    // Constructor with parameters
    public MemberRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

