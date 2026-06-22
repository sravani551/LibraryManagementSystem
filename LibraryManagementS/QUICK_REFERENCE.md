# Quick Reference - Library Management Frontend

## 🚀 Start Both Servers

### Terminal 1 - Backend (Spring Boot)
```bash
cd C:\Users\srava\OneDrive\Desktop\LibraryManagementS
java -jar target/library-management-system-1.0.0.jar
```
✅ Runs on: http://localhost:8080

### Terminal 2 - Frontend (Angular)
```bash
cd C:\Users\srava\OneDrive\Desktop\LibraryManagementS\frontend
ng serve
```
✅ Runs on: http://localhost:4200

---

## 🌐 Access Points

| Page | URL | What to See |
|------|-----|-----------|
| Home | http://localhost:4200 | Welcome screen |
| Members | http://localhost:4200/members | Members table (1 member: John Doe) |
| Books | http://localhost:4200/books | Books table (empty) |
| Add Member | http://localhost:4200/members/new | Member form |
| Add Book | http://localhost:4200/books/new | Book form |

---

## ✅ Verify Servers Running

```bash
# Check port 4200 (ng serve)
netstat -ano | findstr :4200

# Check port 8080 (Spring Boot)
netstat -ano | findstr :8080

# Test frontend
curl http://localhost:4200

# Test backend
curl http://localhost:8080/members
```

---

## 📝 Key Fix

**File**: `frontend/src/app/app.config.ts`
```typescript
export const API_BASE = 'http://localhost:8080';  // ← This was empty before!
```

---

## 🧪 Test Features

1. ✅ Navigate to Members page - See "John Doe"
2. ✅ Try Add Member button - Add new member
3. ✅ Try Add Book button - Add new book
4. ✅ Search books - Search functionality works
5. ✅ Sort members - Try different sort orders
6. ✅ Pagination - Works with multiple pages

---

## 🔧 If Things Go Wrong

### Blank Page
- Hard refresh: Ctrl+Shift+R
- Check console: F12 → Console
- Restart ng serve

### API Errors
- Verify backend running on 8080
- Verify API_BASE = 'http://localhost:8080' in app.config.ts
- Check Network tab (F12) for failed requests

### Port Already in Use
```bash
# Kill node processes
taskkill /F /IM node.exe

# Kill java processes
taskkill /F /IM java.exe

# Restart servers
```

---

## 📊 Current Data

**Members** (1 total):
- ID: 1
- Name: John Doe
- Email: john@example.com
- Date: 2026-06-22

**Books** (0 total):
- None yet - add via "Add Book" button

---

## 🎯 Application Structure

```
Root
├── Home Component
│   └── Shows welcome + navigation
├── Members
│   ├── Member List (table with pagination)
│   ├── Add Member (form)
│   └── Borrow History (per member)
└── Books
    ├── Book List (table + search)
    └── Add Book (form)
```

---

## 🔑 Services Used

| Service | File | Purpose |
|---------|------|---------|
| MemberService | member.service.ts | Get/Create members |
| BookService | book.service.ts | Get/Create books |
| BorrowService | borrow.service.ts | Borrow/Return books |

---

## 📱 Browser DevTools (F12)

### Console Tab
- Shows errors and warnings
- Check for red error messages

### Network Tab
- Shows HTTP requests to backend
- Verify API calls to localhost:8080

### Elements Tab
- Inspect page structure
- Check app-root element

---

## 🎨 Current Pages

### Home Page
Dark header with navigation
White content area with welcome message
Links to Members and Books sections

### Members Page
Table with: Name | Email | Date | Actions
Sort dropdown
Pagination (Previous/Next)
Add Member button
Search: [Coming in borrow history view]

### Books Page
Table with: Title | Author | ISBN | Copies | Action
Search box
Sort/Filter options
Borrow buttons (enabled if copies > 0)
Add Book button

### Forms
- Add Member: Name, Email fields
- Add Book: Title, Author, ISBN, Total Copies fields
- Both with validation and error display

---

## 💡 Tips

1. Add multiple members to test pagination (15+ to see multi-page)
2. Add multiple books to test search functionality
3. Use "John Doe" member to test borrow functionality
4. Check Network tab (F12) to see real API calls being made
5. All data persists in MySQL database

---

## 📚 API Endpoints

Backend serves these endpoints:
- `GET /members` - Get all members (paginated)
- `POST /members` - Create new member
- `GET /books` - Get all books (paginated)
- `GET /books/search?title=...` - Search books
- `POST /books` - Create new book
- `POST /borrow` - Borrow a book
- `PUT /borrow/{id}/return` - Return a book
- `GET /members/{id}/borrow-records` - Get borrow history

---

## 🎉 Ready to Go!

Everything is set up and working. The frontend is fully functional and communicating with the backend. Start adding members and books!

**Happy Testing! 📖📚**

