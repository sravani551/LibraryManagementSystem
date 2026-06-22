import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

// Reusable alert/banner component to show success or error messages
@Component({
  selector: 'app-error-banner',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './error-banner.component.html',
  styleUrls: ['./error-banner.component.css']
})
export class ErrorBannerComponent {
  @Input() message: string | null = null;
  @Input() type: 'error' | 'success' = 'error';
}

