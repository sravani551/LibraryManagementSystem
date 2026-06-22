// Book model matching backend DTO
export interface Book {
  id: number;
  title: string;
  author: string;
  isbn: string;
  availableCopies: number;
}

