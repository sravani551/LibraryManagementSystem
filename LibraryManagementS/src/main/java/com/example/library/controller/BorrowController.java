package com.example.library.controller;

import com.example.library.dto.BorrowRecordResponse;
import com.example.library.entity.BorrowRecord;
import com.example.library.service.BorrowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for book borrowing-related endpoints.
 * Handles HTTP requests for borrow and return operations.
 */
@RestController
@RequestMapping("/members")
public class BorrowController {
    private final BorrowService borrowService;

    // Constructor-based dependency injection
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    /**
     * Borrow a book for a member.
     * @param memberId the ID of the member as path variable
     * @param bookId the ID of the book as path variable
     * @return response entity with borrow record data
     */
    @PostMapping("/{memberId}/borrow/{bookId}")
    public ResponseEntity<BorrowRecordResponse> borrowBook(
            @PathVariable Long memberId,
            @PathVariable Long bookId) {
        
        BorrowRecord borrowRecord = borrowService.borrowBook(memberId, bookId);
        return ResponseEntity.ok(new BorrowRecordResponse(borrowRecord));
    }

    /**
     * Get borrow records for a specific member.
     * @param memberId the ID of the member as path variable
     * @return response entity with list of borrow records
     */
    @GetMapping("/{memberId}/borrow-records")
    public ResponseEntity<List<BorrowRecordResponse>> getBorrowRecordsForMember(@PathVariable Long memberId) {
        List<BorrowRecord> borrowRecords = borrowService.getBorrowRecordsForMember(memberId);
        
        // Convert to response DTOs
        List<BorrowRecordResponse> responses = borrowRecords.stream()
                .map(BorrowRecordResponse::new)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }
}

/**
 * REST controller for returning books.
 * Separated into its own mapping for clarity.
 */
@RestController
@RequestMapping("/borrow-records")
class ReturnBookController {
    private final BorrowService borrowService;

    // Constructor-based dependency injection
    public ReturnBookController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    /**
     * Return a borrowed book.
     * @param id the ID of the borrow record as path variable
     * @return response entity with updated borrow record data
     */
    @PostMapping("/{id}/return")
    public ResponseEntity<BorrowRecordResponse> returnBook(@PathVariable Long id) {
        BorrowRecord borrowRecord = borrowService.returnBook(id);
        return ResponseEntity.ok(new BorrowRecordResponse(borrowRecord));
    }
}

