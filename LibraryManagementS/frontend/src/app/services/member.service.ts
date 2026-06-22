import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Member } from '../models/member.model';
import { PagedResponse } from '../models/paged-response.model';
import { API_BASE } from '../app.config';

// Service for calling member-related backend endpoints
@Injectable({ providedIn: 'root' })
export class MemberService {
  constructor(private http: HttpClient) {}

  // Create a new member
  createMember(payload: { name: string; email: string }): Observable<Member> {
    return this.http.post<Member>(`${API_BASE}/members`, payload);
  }

  // Get single member
  getMember(id: number): Observable<Member> {
    return this.http.get<Member>(`${API_BASE}/members/${id}`);
  }

  // Get paginated members with optional sorting
  getMembers(page = 0, size = 10, sortBy = 'name'): Observable<PagedResponse<Member>> {
    return this.http.get<PagedResponse<Member>>(
      `${API_BASE}/members?page=${page}&size=${size}&sortBy=${encodeURIComponent(sortBy)}`
    );
  }
}

