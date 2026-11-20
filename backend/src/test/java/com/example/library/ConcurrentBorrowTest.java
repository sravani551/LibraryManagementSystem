package com.example.library;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.example.library.entity.Book;
import com.example.library.entity.Member;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowRecordRepository;
import com.example.library.repository.MemberRepository;
import com.example.library.service.BorrowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Many members try to borrow the last copy at the same moment. Exactly one may succeed:
 * without optimistic locking (@Version on Book) concurrent transactions can each read
 * availableCopies = 1 and both create a borrow record.
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ConcurrentBorrowTest {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BookRepository books;
    @Autowired
    private MemberRepository members;
    @Autowired
    private BorrowRecordRepository records;

    @Test
    void lastCopyCanOnlyBeBorrowedOnce() throws Exception {
        Book book = books.save(new Book("Refactoring", "Martin Fowler", "978-0134757599", 1));
        int contenders = 8;
        List<Long> memberIds = new ArrayList<>();
        for (int i = 0; i < contenders; i++) {
            Member m = new Member("Member " + i, "member" + i + "@example.com");
            memberIds.add(members.save(m).getId());
        }

        ExecutorService pool = Executors.newFixedThreadPool(contenders);
        CountDownLatch start = new CountDownLatch(1);
        List<Future<Boolean>> results = new ArrayList<>();
        for (Long memberId : memberIds) {
            results.add(pool.submit(() -> {
                start.await();
                try {
                    borrowService.borrowBook(memberId, book.getId());
                    return true;
                } catch (RuntimeException e) {
                    return false; // "No copies available" or optimistic-lock conflict
                }
            }));
        }
        start.countDown();
        int successes = 0;
        for (Future<Boolean> r : results) {
            if (r.get(30, TimeUnit.SECONDS)) {
                successes++;
            }
        }
        pool.shutdown();

        assertThat(successes).isEqualTo(1);
        assertThat(records.count()).isEqualTo(1);
        assertThat(books.findById(book.getId()).orElseThrow().getAvailableCopies()).isZero();
    }
}
