package com.example.library.service;

import com.example.library.dto.MemberRequest;
import com.example.library.entity.Member;
import com.example.library.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Service class for managing Member operations.
 * Uses constructor-based dependency injection.
 */
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    // Constructor-based dependency injection
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * Create a new member.
     * Validates that the email is not already registered.
     * @param request the member request data
     * @return the created member
     * @throws IllegalStateException if email is already registered
     */
    public Member createMember(MemberRequest request) {
        // Check if email is already registered
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Email is already registered");
        }

        // Create and save new member
        Member member = new Member(request.getName(), request.getEmail());
        return memberRepository.save(member);
    }

    /**
     * Get a member by ID.
     * @param id the member ID
     * @return the member
     * @throws NoSuchElementException if member not found
     */
    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Member not found with id: " + id));
    }

    /**
     * Get paginated and sorted list of members.
     * @param page the page number (0-indexed)
     * @param size the page size
     * @param sortBy the field to sort by
     * @return a page of members
     */
    public Page<Member> getMembers(int page, int size, String sortBy) {
        // Create pageable with sorting
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return memberRepository.findAll(pageable);
    }
}

