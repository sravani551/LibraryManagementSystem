import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { MemberService } from '../../services/member.service';
import { Router } from '@angular/router';
import { ErrorBannerComponent } from '../../components/error-banner/error-banner.component';

// Add Member page: has a form and shows backend validation errors inline
@Component({
  selector: 'app-add-member',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ErrorBannerComponent, RouterLink],
  templateUrl: './add-member.component.html',
  styleUrls: ['./add-member.component.css']
})
export class AddMemberComponent {
  form = this.fb.group({
    name: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]]
  });

  fieldErrors: { [key: string]: string } = {};
  generalError: string | null = null;

  constructor(private fb: FormBuilder, private memberService: MemberService, private router: Router) {}

  submit(): void {
    this.fieldErrors = {};
    this.generalError = null;

    if (this.form.invalid) {
      this.generalError = 'Please fix form errors';
      return;
    }

    // Cast form.value to the expected payload shape to satisfy TypeScript strict checks
    const payload: { name: string; email: string } = {
      name: this.form.value.name as string,
      email: this.form.value.email as string
    };

    this.memberService.createMember(payload).subscribe({
      next: () => this.router.navigate(['/members']),
      error: (err) => {
        // Backend may return field-level validation errors or a message
        if (err.status === 400 && typeof err.error === 'object') {
          // If it's a map of field -> message
          this.fieldErrors = err.error;
        } else if (err.status === 409) {
          this.generalError = err.error?.message || 'Conflict error';
        } else {
          this.generalError = err.error?.message || 'An error occurred';
        }
      }
    });
  }
}


