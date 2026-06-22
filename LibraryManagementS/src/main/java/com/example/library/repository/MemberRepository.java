package com.example.library.repository;

import com.example.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Member entity.
 * Provides CRUD operations and custom queries.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    /**
     * Find a member by email address.
     * @param email the email to search for
     * @return an Optional containing the member if found
     */
    Optional<Member> findByEmail(String email);
}

