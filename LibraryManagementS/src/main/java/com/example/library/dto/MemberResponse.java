package com.example.library.dto;

import com.example.library.entity.Member;
import java.time.LocalDate;

/**
 * MemberResponse DTO for returning member data in API responses.
 * Maps Member entity data to a flattened structure.
 */
public class MemberResponse {
    private Long id;
    private String name;
    private String email;
    private LocalDate membershipDate;

    // Default constructor for deserialization
    public MemberResponse() {
    }

    // Constructor that takes a Member entity and maps its fields
    public MemberResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.membershipDate = member.getMembershipDate();
    }

    // Constructor with all parameters
    public MemberResponse(Long id, String name, String email, LocalDate membershipDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.membershipDate = membershipDate;
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

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }
}

