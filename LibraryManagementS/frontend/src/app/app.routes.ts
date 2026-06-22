import { Routes } from '@angular/router';
import { MemberListComponent } from './pages/members/member-list.component';
import { AddMemberComponent } from './pages/members/add-member.component';
import { BorrowHistoryComponent } from './pages/members/borrow-history.component';
import { BookListComponent } from './pages/books/book-list.component';
import { AddBookComponent } from './pages/books/add-book.component';
import { HomeComponent } from './pages/home/home.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'members', component: MemberListComponent },
  { path: 'members/new', component: AddMemberComponent },
  { path: 'members/:id/borrow-records', component: BorrowHistoryComponent },
  { path: 'books', component: BookListComponent },
  { path: 'books/new', component: AddBookComponent }
];

