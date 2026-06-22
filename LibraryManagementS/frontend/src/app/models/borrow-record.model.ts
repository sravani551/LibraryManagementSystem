// BorrowRecord model matching backend DTO
export interface BorrowRecord {
  id: number;
  memberName: string;
  bookTitle: string;
  borrowDate: string; // ISO date
  returnDate?: string | null; // null when not returned
}

