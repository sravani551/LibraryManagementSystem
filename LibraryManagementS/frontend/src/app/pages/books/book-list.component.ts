import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BookService } from '../../services/book.service';
import { Book } from '../../models/book.model';
import { BorrowService } from '../../services/borrow.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  books: Book[] = [];
  page = 0;
  size = 10;
  totalPages = 0;
  searchTerm: string | null = null;
  errorMsg: string | null = null;
  successMsg: string | null = null;
  isLoading = true;

  constructor(private bookService: BookService, private borrowService: BorrowService) {}

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks(): void {
    this.errorMsg = null;
    this.successMsg = null;
    this.isLoading = true;
    this.bookService.searchBooks(this.searchTerm, this.page, this.size).subscribe({
      next: (res) => {
        this.books = res.content;
        this.totalPages = res.totalPages;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMsg = err?.error?.message || 'Failed to load books';
      }
    });
  }

  search(): void {
    this.page = 0;
    this.loadBooks();
  }

  nextPage(): void {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadBooks();
    }
  }

  prevPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadBooks();
    }
  }

  borrow(book: Book): void {
    const memberIdString = prompt('Enter member ID to borrow this book:');
    if (!memberIdString) return;
    const memberId = Number(memberIdString);
    if (isNaN(memberId)) {
      this.errorMsg = 'Invalid member ID';
      return;
    }

    this.borrowService.borrowBook(memberId, book.id).subscribe({
      next: (res) => {
        this.successMsg = `✓ Borrowed: "${res.bookTitle}" by ${res.memberName}`;
        this.loadBooks();
      },
      error: (err) => {
        this.errorMsg = err?.error?.message || 'Failed to borrow book';
      }
    });
  }
}
