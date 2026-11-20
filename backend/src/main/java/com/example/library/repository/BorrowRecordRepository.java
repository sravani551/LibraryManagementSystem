package com.example.library.repository;

import com.example.library.entity.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    List<BorrowRecord> findByMemberId(Long memberId);
}

