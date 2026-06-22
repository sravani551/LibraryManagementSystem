// Generic PagedResponse matching Spring Page<T> JSON shape
export interface PagedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number; // current page (0-based)
}

