package com.example.library.repository;

import com.example.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Book entity.
 * Provides CRUD operations and custom queries.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * Search for books by title (case-insensitive) with pagination support.
     * @param title the title to search for
     * @param pageable pagination information
     * @return a page of books matching the title
     */
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}

