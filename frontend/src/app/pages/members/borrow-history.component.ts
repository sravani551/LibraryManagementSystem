import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BorrowService } from '../../services/borrow.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { BorrowRecord } from '../../models/borrow-record.model';
import { ErrorBannerComponent } from '../../components/error-banner/error-banner.component';

// Member borrow history page. Shows borrow records and allows returning books.
@Component({
  selector: 'app-borrow-history',
  standalone: true,
  imports: [CommonModule, ErrorBannerComponent, RouterLink],
  templateUrl: './borrow-history.component.html',
  styleUrls: ['./borrow-history.component.css']
})
export class BorrowHistoryComponent implements OnInit {
  records: BorrowRecord[] = [];
  memberId!: number;
  errorMsg: string | null = null;
  successMsg: string | null = null;

  constructor(private borrowService: BorrowService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.memberId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadRecords();
  }

  loadRecords(): void {
    this.errorMsg = null;
    this.borrowService.getBorrowRecordsForMember(this.memberId).subscribe({
      next: (res) => (this.records = res),
      error: (err) => (this.errorMsg = err?.error?.message || 'Unable to load borrow records')
    });
  }

  returnRecord(record: BorrowRecord): void {
    this.successMsg = null;
    this.errorMsg = null;
    this.borrowService.returnBook(record.id).subscribe({
      next: (updated) => {
        this.successMsg = 'Book returned successfully';
        // update the local record to show returned date
        const idx = this.records.findIndex(r => r.id === updated.id);
        if (idx >= 0) this.records[idx] = updated;
      },
      error: (err) => (this.errorMsg = err?.error?.message || 'Failed to return book')
    });
  }

  // Calculate count of returned records
  getReturnedCount(): number {
    return this.records.filter(r => r.returnDate).length;
  }

  // Calculate count of currently borrowed records
  getBorrowedCount(): number {
    return this.records.filter(r => !r.returnDate).length;
  }
}

