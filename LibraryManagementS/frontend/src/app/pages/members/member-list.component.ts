import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MemberService } from '../../services/member.service';
import { Member } from '../../models/member.model';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-member-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './member-list.component.html',
  styleUrls: ['./member-list.component.css']
})
export class MemberListComponent implements OnInit {
  members: Member[] = [];
  page = 0;
  size = 10;
  totalPages = 0;
  totalElements = 0;
  sortBy = 'name';
  errorMsg: string | null = null;
  isLoading = true;

  constructor(private memberService: MemberService) {}

  ngOnInit(): void {
    this.loadMembers();
  }

  loadMembers(): void {
    this.errorMsg = null;
    this.isLoading = true;
    this.memberService.getMembers(this.page, this.size, this.sortBy).subscribe({
      next: (res) => {
        this.members = res.content;
        this.totalPages = res.totalPages;
        this.totalElements = res.totalElements;
        this.isLoading = false;
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMsg = err?.error?.message || 'Failed to load members';
        console.error('Error loading members:', err);
      }
    });
  }

  nextPage(): void {
    if (this.page < this.totalPages - 1) {
      this.page++;
      this.loadMembers();
    }
  }

  prevPage(): void {
    if (this.page > 0) {
      this.page--;
      this.loadMembers();
    }
  }

  setSort(sortKey: string): void {
    this.sortBy = sortKey;
    this.page = 0;
    this.loadMembers();
  }
}
