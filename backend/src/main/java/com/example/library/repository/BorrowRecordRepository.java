package com.example.library.repository;

import com.example.library.entity.BorrowRecord;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for BorrowRecord entity.
 * Provides CRUD operations and custom queries.
 */
@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    /**
     * Find all borrow records for a specific member.
     * @param memberId the ID of the member
     * @return a list of borrow records for the member
     */
    @EntityGraph(attributePaths = {"member", "book"})
    List<BorrowRecord> findByMemberId(Long memberId);

    /**
     * Loads the record with its member and book in one query. Responses read both after the
     * transaction ends (open-in-view is disabled), so they must not be lazy proxies.
     */
    @EntityGraph(attributePaths = {"member", "book"})
    Optional<BorrowRecord> findWithMemberAndBookById(Long id);
}

