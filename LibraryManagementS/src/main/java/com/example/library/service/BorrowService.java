package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.BorrowRecord;
import com.example.library.entity.Member;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowRecordRepository;
import com.example.library.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class for managing Book borrowing operations.
 * Uses constructor-based dependency injection.
 */
@Service
public class BorrowService {
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    // Constructor-based dependency injection
    public BorrowService(MemberRepository memberRepository, 
                        BookRepository bookRepository,
                        BorrowRecordRepository borrowRecordRepository) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.borrowRecordRepository = borrowRecordRepository;
    }

    /**
     * Borrow a book for a member.
     * Decrements available copies and creates a borrow record.
     * @param memberId the ID of the member
     * @param bookId the ID of the book
     * @return the created borrow record
     * @throws NoSuchElementException if member or book not found
     * @throws IllegalStateException if no copies available
     */
    @Transactional
    public BorrowRecord borrowBook(Long memberId, Long bookId) {
        // Find member, throw exception if not found
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("Member not found with id: " + memberId));

        // Find book, throw exception if not found
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + bookId));

        // Check if copies are available
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No copies available");
        }

        // Decrement available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        // Create and save new borrow record
        BorrowRecord borrowRecord = new BorrowRecord(member, book);
        return borrowRecordRepository.save(borrowRecord);
    }

    /**
     * Return a borrowed book.
     * Sets return date and increments available copies.
     * @param borrowRecordId the ID of the borrow record
     * @return the updated borrow record
     * @throws NoSuchElementException if borrow record not found
     * @throws IllegalStateException if book already returned
     */
    @Transactional
    public BorrowRecord returnBook(Long borrowRecordId) {
        // Find borrow record, throw exception if not found
        BorrowRecord borrowRecord = borrowRecordRepository.findById(borrowRecordId)
                .orElseThrow(() -> new NoSuchElementException("Borrow record not found with id: " + borrowRecordId));

        // Check if book is already returned
        if (borrowRecord.getReturnDate() != null) {
            throw new IllegalStateException("Book already returned");
        }

        // Set return date to today
        borrowRecord.setReturnDate(LocalDate.now());

        // Increment available copies of the book
        Book book = borrowRecord.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        // Save and return updated borrow record
        return borrowRecordRepository.save(borrowRecord);
    }

    /**
     * Get all borrow records for a member.
     * @param memberId the ID of the member
     * @return a list of borrow records
     */
    public List<BorrowRecord> getBorrowRecordsForMember(Long memberId) {
        return borrowRecordRepository.findByMemberId(memberId);
    }
}

