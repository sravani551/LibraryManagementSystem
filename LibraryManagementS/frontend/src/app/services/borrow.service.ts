import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BorrowRecord } from '../models/borrow-record.model';
import { API_BASE } from '../app.config';

@Injectable({ providedIn: 'root' })
export class BorrowService {
  constructor(private http: HttpClient) {}

  // Borrow a book for a member
  borrowBook(memberId: number, bookId: number): Observable<BorrowRecord> {
    return this.http.post<BorrowRecord>(`${API_BASE}/members/${memberId}/borrow/${bookId}`, null as any);
  }

  // Return a borrowed book
  returnBook(borrowRecordId: number): Observable<BorrowRecord> {
    return this.http.post<BorrowRecord>(`${API_BASE}/borrow-records/${borrowRecordId}/return`, null as any);
  }

  // Get borrow records for a member
  getBorrowRecordsForMember(memberId: number): Observable<BorrowRecord[]> {
    return this.http.get<BorrowRecord[]>(`${API_BASE}/members/${memberId}/borrow-records`);
  }
}

