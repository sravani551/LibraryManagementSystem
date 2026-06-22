package com.example.library.controller;

import com.example.library.dto.MemberRequest;
import com.example.library.dto.MemberResponse;
import com.example.library.entity.Member;
import com.example.library.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for member-related endpoints.
 * Handles HTTP requests for member operations.
 */
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    // Constructor-based dependency injection
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * Create a new member.
     * @param request the member request data with validation
     * @return response entity with created member data and 200 status
     */
    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberRequest request) {
        Member member = memberService.createMember(request);
        return ResponseEntity.ok(new MemberResponse(member));
    }

    /**
     * Get a member by ID.
     * @param id the member ID as path variable
     * @return response entity with member data
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
        Member member = memberService.getMember(id);
        return ResponseEntity.ok(new MemberResponse(member));
    }

    /**
     * Get paginated list of members with sorting.
     * @param page the page number (0-indexed), default 0
     * @param size the page size, default 10
     * @param sortBy the field to sort by, default "name"
     * @return response entity with paginated member data
     */
    @GetMapping
    public ResponseEntity<Page<MemberResponse>> getMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        
        Page<Member> memberPage = memberService.getMembers(page, size, sortBy);
        
        // Convert Member entities to MemberResponse DTOs
        List<MemberResponse> responses = memberPage.getContent()
                .stream()
                .map(MemberResponse::new)
                .collect(Collectors.toList());
        
        // Create a new Page with the converted DTOs
        Page<MemberResponse> responsePage = new PageImpl<>(responses, memberPage.getPageable(), memberPage.getTotalElements());
        
        return ResponseEntity.ok(responsePage);
    }
}

