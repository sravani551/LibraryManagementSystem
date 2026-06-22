# Library Management System - API Testing Examples

This file contains ready-to-use cURL commands for testing all API endpoints.

---

## 🔧 Prerequisites

Make sure the application is running on http://localhost:8080

---

## 👥 Member Endpoints

### 1. Create a Member

```bash
curl -X POST http://localhost:8080/members \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com"
  }'
```

**Expected Response**: 200 OK
```json
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "membershipDate": "2026-06-21"
}
```

### 2. Create Another Member

```bash
curl -X POST http://localhost:8080/members \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "email": "jane@example.com"
  }'
```

### 3. Get Member by ID

```bash
curl http://localhost:8080/members/1
```

### 4. Get All Members (Paginated)

```bash
# Get first page with 10 members sorted by name
curl "http://localhost:8080/members?page=0&size=10&sortBy=name"

# Get second page with 5 members sorted by email
curl "http://localhost:8080/members?page=1&size=5&sortBy=email"
```

### 5. Test Validation Error - Blank Name

```bash
curl -X POST http://localhost:8080/members \
  -H "Content-Type: application/json" \
  -d '{
    "name": "",
    "email": "test@example.com"
  }'
```

**Expected Response**: 400 Bad Request
```json
{
    "name": "Name cannot be blank"
}
```

### 6. Test Validation Error - Invalid Email

```bash
curl -X POST http://localhost:8080/members \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "invalid-email"
  }'
```

**Expected Response**: 400 Bad Request
```json
{
    "email": "Email should be valid"
}
```

### 7. Test Error - Duplicate Email

```bash
curl -X POST http://localhost:8080/members \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Another John",
    "email": "john@example.com"
  }'
```

**Expected Response**: 409 Conflict
```json
{
    "error": "Email is already registered"
}
```

### 8. Test Error - Member Not Found

```bash
curl http://localhost:8080/members/999
```

**Expected Response**: 404 Not Found
```json
{
    "error": "Member not found with id: 999"
}
```

---

## 📚 Book Endpoints

### 1. Create a Book

```bash
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Harry Potter and the Philosopher'\''s Stone",
    "author": "J.K. Rowling",
    "isbn": "978-0747532699",
    "totalCopies": 5
  }'
```

**Expected Response**: 200 OK
```json
{
    "id": 1,
    "title": "Harry Potter and the Philosopher's Stone",
    "author": "J.K. Rowling",
    "isbn": "978-0747532699",
    "availableCopies": 5
}
```

### 2. Create More Books

```bash
# Book 2
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Hobbit",
    "author": "J.R.R. Tolkien",
    "isbn": "978-0547928227",
    "totalCopies": 3
  }'

# Book 3
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Harry Potter and the Chamber of Secrets",
    "author": "J.K. Rowling",
    "isbn": "978-0747538493",
    "totalCopies": 4
  }'
```

### 3. Search All Books (Paginated)

```bash
curl "http://localhost:8080/books?page=0&size=10"
```

### 4. Search Books by Title

```bash
curl "http://localhost:8080/books?title=Harry&page=0&size=10"
```

### 5. Search Books - Case Insensitive

```bash
curl "http://localhost:8080/books?title=POTTER&page=0&size=10"
```

### 6. Test Validation - Missing Title

```bash
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "",
    "author": "Some Author",
    "isbn": "123-456-789",
    "totalCopies": 5
  }'
```

**Expected Response**: 400 Bad Request
```json
{
    "title": "Title cannot be blank"
}
```

### 7. Test Validation - Non-Positive Copies

```bash
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Book",
    "author": "Test Author",
    "isbn": "999-999-999",
    "totalCopies": -1
  }'
```

**Expected Response**: 400 Bad Request
```json
{
    "totalCopies": "Total copies must be positive"
}
```

---

## 📖 Borrow/Return Endpoints

### 1. Borrow a Book

```bash
# Borrow book 1 for member 1
curl -X POST http://localhost:8080/members/1/borrow/1
```

**Expected Response**: 200 OK
```json
{
    "id": 1,
    "memberName": "John Doe",
    "bookTitle": "Harry Potter and the Philosopher's Stone",
    "borrowDate": "2026-06-21",
    "returnDate": null
}
```

### 2. Borrow Another Book

```bash
curl -X POST http://localhost:8080/members/1/borrow/2
```

### 3. Member 2 Borrows a Book

```bash
curl -X POST http://localhost:8080/members/2/borrow/2
```

### 4. Get Borrow History for a Member

```bash
curl http://localhost:8080/members/1/borrow-records
```

**Expected Response**: 200 OK
```json
[
    {
        "id": 1,
        "memberName": "John Doe",
        "bookTitle": "Harry Potter and the Philosopher's Stone",
        "borrowDate": "2026-06-21",
        "returnDate": null
    },
    {
        "id": 2,
        "memberName": "John Doe",
        "bookTitle": "The Hobbit",
        "borrowDate": "2026-06-21",
        "returnDate": null
    }
]
```

### 5. Return a Book

```bash
curl -X POST http://localhost:8080/borrow-records/1/return
```

**Expected Response**: 200 OK
```json
{
    "id": 1,
    "memberName": "John Doe",
    "bookTitle": "Harry Potter and the Philosopher's Stone",
    "borrowDate": "2026-06-21",
    "returnDate": "2026-06-21"
}
```

### 6. Test Error - Book Not Available

```bash
# First, borrow all available copies
curl -X POST http://localhost:8080/members/1/borrow/2
curl -X POST http://localhost:8080/members/1/borrow/2
# Try to borrow when no copies are available
curl -X POST http://localhost:8080/members/2/borrow/2
```

**Expected Response**: 409 Conflict
```json
{
    "error": "No copies available"
}
```

### 7. Test Error - Member Not Found

```bash
curl -X POST http://localhost:8080/members/999/borrow/1
```

**Expected Response**: 404 Not Found
```json
{
    "error": "Member not found with id: 999"
}
```

### 8. Test Error - Book Not Found

```bash
curl -X POST http://localhost:8080/members/1/borrow/999
```

**Expected Response**: 404 Not Found
```json
{
    "error": "Book not found with id: 999"
}
```

### 9. Test Error - Already Returned

```bash
curl -X POST http://localhost:8080/borrow-records/1/return
```

**Expected Response**: 409 Conflict (after already returning)
```json
{
    "error": "Book already returned"
}
```

### 10. Test Error - Borrow Record Not Found

```bash
curl -X POST http://localhost:8080/borrow-records/999/return
```

**Expected Response**: 404 Not Found
```json
{
    "error": "Borrow record not found with id: 999"
}
```

---

## 🎯 Complete Test Scenario

Here's a complete scenario to test the entire flow:

```bash
# 1. Create members
echo "Creating members..."
curl -X POST http://localhost:8080/members -H "Content-Type: application/json" -d '{"name":"Alice","email":"alice@example.com"}'
curl -X POST http://localhost:8080/members -H "Content-Type: application/json" -d '{"name":"Bob","email":"bob@example.com"}'

# 2. Create books
echo -e "\nCreating books..."
curl -X POST http://localhost:8080/books -H "Content-Type: application/json" -d '{"title":"Book A","author":"Author A","isbn":"ISBN-001","totalCopies":2}'
curl -X POST http://localhost:8080/books -H "Content-Type: application/json" -d '{"title":"Book B","author":"Author B","isbn":"ISBN-002","totalCopies":1}'

# 3. View all members
echo -e "\nViewing all members..."
curl http://localhost:8080/members

# 4. View all books
echo -e "\nViewing all books..."
curl http://localhost:8080/books

# 5. Borrow books
echo -e "\nBorrowing books..."
curl -X POST http://localhost:8080/members/1/borrow/1
curl -X POST http://localhost:8080/members/2/borrow/1

# 6. View borrow records
echo -e "\nViewing borrow records for Alice (member 1)..."
curl http://localhost:8080/members/1/borrow-records

# 7. Return a book
echo -e "\nReturning book..."
curl -X POST http://localhost:8080/borrow-records/1/return

# 8. Verify return
echo -e "\nVerifying Alice's borrow records after return..."
curl http://localhost:8080/members/1/borrow-records
```

---

## 📊 H2 Console Access

Access the H2 Database Console at: **http://localhost:8080/h2-console**

### Connection Details
- **Saved Setting**: Generic H2 (Embedded)
- **Setting Name**: Generic H2 (Embedded)
- **Driver Class**: org.h2.Driver
- **JDBC URL**: jdbc:h2:mem:librarydb
- **User Name**: sa
- **Password**: (leave blank)

### Useful SQL Queries
```sql
-- View all members
SELECT * FROM members;

-- View all books with available copies
SELECT id, title, author, available_copies, total_copies FROM books;

-- View all borrow records
SELECT * FROM borrow_records;

-- View outstanding borrows (not returned)
SELECT * FROM borrow_records WHERE return_date IS NULL;

-- View returned books
SELECT * FROM borrow_records WHERE return_date IS NOT NULL;

-- Books with no copies available
SELECT * FROM books WHERE available_copies = 0;

-- Member's borrow history
SELECT br.*, m.name, b.title 
FROM borrow_records br
JOIN members m ON br.member_id = m.id
JOIN books b ON br.book_id = b.id
WHERE br.member_id = 1;
```

---

## 🔍 Common Issues & Solutions

### Issue: Connection Refused
**Solution**: Make sure the application is running with `mvn spring-boot:run`

### Issue: 404 Not Found for Valid ID
**Solution**: Check the database in H2 console to verify the record exists

### Issue: Invalid JSON Response
**Solution**: Make sure the Content-Type header is set to `application/json` in POST requests

### Issue: Member or Book Not Found After Creation
**Solution**: Verify the ID from the response is being used correctly in subsequent requests

---

## 📌 Notes

- All timestamps are automatically set to today's date
- Membership date cannot be changed after creation
- Once a borrow date is set, it cannot be changed
- Returning an already returned book will result in an error
- Available copies are decremented when borrowing and incremented when returning

