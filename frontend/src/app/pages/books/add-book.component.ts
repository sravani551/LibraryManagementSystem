import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { BookService } from '../../services/book.service';
import { Router } from '@angular/router';
import { ErrorBannerComponent } from '../../components/error-banner/error-banner.component';
import { RouterLink } from '@angular/router';

// Add Book page: form with backend validation handling
@Component({
  selector: 'app-add-book',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ErrorBannerComponent, RouterLink],
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent {
  form = this.fb.group({
    title: ['', Validators.required],
    author: ['', Validators.required],
    isbn: ['', Validators.required],
    totalCopies: [1, [Validators.required, Validators.min(1)]]
  });

  fieldErrors: { [key: string]: string } = {};
  generalError: string | null = null;

  constructor(private fb: FormBuilder, private bookService: BookService, private router: Router) {}

  submit(): void {
    this.fieldErrors = {};
    this.generalError = null;

    if (this.form.invalid) {
      this.generalError = 'Please fix form errors';
      return;
    }

    const fv = this.form.value;
    const payload = {
      title: fv.title as string,
      author: fv.author as string,
      isbn: fv.isbn as string,
      totalCopies: Number(fv.totalCopies)
    };

    this.bookService.createBook(payload).subscribe({
      next: () => this.router.navigate(['/books']),
      error: (err) => {
        if (err.status === 400 && typeof err.error === 'object') {
          this.fieldErrors = err.error;
        } else {
          this.generalError = err.error?.message || 'An error occurred';
        }
      }
    });
  }
}

