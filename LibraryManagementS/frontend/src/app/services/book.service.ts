import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from '../models/book.model';
import { PagedResponse } from '../models/paged-response.model';
import { API_BASE } from '../app.config';

@Injectable({ providedIn: 'root' })
export class BookService {
  constructor(private http: HttpClient) {}

  createBook(payload: { title: string; author: string; isbn: string; totalCopies: number }): Observable<Book> {
    return this.http.post<Book>(`${API_BASE}/books`, payload);
  }

  // Search books by optional title with pagination
  searchBooks(title: string | null, page = 0, size = 10): Observable<PagedResponse<Book>> {
    const titleParam = title ? `title=${encodeURIComponent(title)}&` : '';
    return this.http.get<PagedResponse<Book>>(`${API_BASE}/books?${titleParam}page=${page}&size=${size}`);
  }
}

