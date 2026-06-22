# Library Management System - Spring Boot REST API

A comprehensive Spring Boot 3.x REST API for managing a library system with members, books, and borrow records.

## Project Overview

This project implements a complete Library Management System with the following features:
- **Member Management**: Register and manage library members
- **Book Management**: Add and search books in the library
- **Book Borrowing**: Track book borrowing and returning
- **Database**: H2 in-memory database with automatic schema creation
- **Validation**: Input validation using Jakarta Validation
- **Exception Handling**: Centralized error handling with meaningful responses

## Technology Stack

- **Framework**: Spring Boot 3.3.0
- **Language**: Java 17
- **Build Tool**: Maven
- **Database**: H2 (in-memory)
- **ORM**: Spring Data JPA with Hibernate
- **Validation**: Jakarta Validation
- **API**: RESTful Web Services

## Project Structure

```
com.example.library/
├── entity/                 # JPA Entity classes
│   ├── Member.java        # Member entity with one-to-many relationship to BorrowRecord
│   ├── Book.java          # Book entity with one-to-many relationship to BorrowRecord
│   └── BorrowRecord.java  # Borrow record entity with many-to-one relationships
├── dto/                    # Data Transfer Objects
│   ├── MemberRequest.java      # Request DTO for creating members
│   ├── MemberResponse.java     # Response DTO for members
│   ├── BookRequest.java        # Request DTO for creating books
│   ├── BookResponse.java       # Response DTO for books
│   └── BorrowRecordResponse.java # Response DTO for borrow records
├── repository/             # Spring Data JPA Repositories
│   ├── MemberRepository.java     # Repository with findByEmail method
│   ├── BookRepository.java       # Repository with findByTitleContainingIgnoreCase method
│   └── BorrowRecordRepository.java # Repository with findByMemberId method
├── service/                # Business Logic Services
│   ├── MemberService.java    # Member management logic
│   ├── BookService.java      # Book management logic
│   └── BorrowService.java    # Book borrowing logic (transactional)
├── controller/             # REST Controllers
│   ├── MemberController.java    # Member REST endpoints
│   ├── BookController.java      # Book REST endpoints
│   └── BorrowController.java    # Borrow/Return REST endpoints
├── exception/              # Exception Handling
│   └── GlobalExceptionHandler.java # Centralized exception handler
└── LibraryManagementApplication.java # Main application class
```

## Database Schema

### Members Table
```sql
CREATE TABLE members (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    membership_date DATE NOT NULL
);
```

### Books Table
```sql
CREATE TABLE books (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL UNIQUE,
    total_copies INT NOT NULL,
    available_copies INT NOT NULL
);
```

### Borrow Records Table
```sql
CREATE TABLE borrow_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    borrow_date DATE NOT NULL,
    return_date DATE,
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);
```

## API Endpoints

### Member Endpoints

#### Create a Member
```http
POST /members
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john@example.com"
}

Response: 200 OK
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "membershipDate": "2026-06-21"
}
```

#### Get Member by ID
```http
GET /members/1

Response: 200 OK
{
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "membershipDate": "2026-06-21"
}
```

#### List Members (Paginated)
```http
GET /members?page=0&size=10&sortBy=name

Response: 200 OK
{
    "content": [...],
    "pageable": {...},
    "totalElements": 5,
    "totalPages": 1
}
```

### Book Endpoints

#### Create a Book
```http
POST /books
Content-Type: application/json

{
    "title": "Harry Potter",
    "author": "J.K. Rowling",
    "isbn": "978-0747532699",
    "totalCopies": 5
}

Response: 200 OK
{
    "id": 1,
    "title": "Harry Potter",
    "author": "J.K. Rowling",
    "isbn": "978-0747532699",
    "availableCopies": 5
}
```

#### Search Books
```http
GET /books?title=Harry&page=0&size=10

Response: 200 OK
{
    "content": [...],
    "pageable": {...},
    "totalElements": 3,
    "totalPages": 1
}
```

### Borrow/Return Endpoints

#### Borrow a Book
```http
POST /members/1/borrow/1

Response: 200 OK
{
    "id": 1,
    "memberName": "John Doe",
    "bookTitle": "Harry Potter",
    "borrowDate": "2026-06-21",
    "returnDate": null
}
```

#### Return a Book
```http
POST /borrow-records/1/return

Response: 200 OK
{
    "id": 1,
    "memberName": "John Doe",
    "bookTitle": "Harry Potter",
    "borrowDate": "2026-06-21",
    "returnDate": "2026-06-21"
}
```

#### Get Member's Borrow History
```http
GET /members/1/borrow-records

Response: 200 OK
[
    {
        "id": 1,
        "memberName": "John Doe",
        "bookTitle": "Harry Potter",
        "borrowDate": "2026-06-21",
        "returnDate": "2026-06-21"
    }
]
```

## Error Handling

The API returns appropriate HTTP status codes and error messages:

### Validation Errors (400 Bad Request)
```json
{
    "name": "Name cannot be blank",
    "email": "Email should be valid"
}
```

### Conflict Errors (409 Conflict)
```json
{
    "error": "No copies available"
}
```

### Not Found Errors (404 Not Found)
```json
{
    "error": "Member not found with id: 999"
}
```

### Server Errors (500 Internal Server Error)
```json
{
    "error": "Something went wrong"
}
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Installation & Running

1. **Clone/Download the project**
   ```bash
   cd LibraryManagementS
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - API: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console

## Frontend (UI)

This repository includes a separate frontend folder with a minimal Angular 17+ standalone-components application that consumes the backend API at http://localhost:8080.

To run the frontend locally:

1. Install Node.js (>=16) and npm
2. Change into the frontend folder and install dependencies:

```bash
cd frontend
npm install
```

3. Start the dev server (uses Angular CLI):

```bash
npx @angular/cli@17 serve --open
```

The UI will open at http://localhost:4200 and will call the backend at http://localhost:8080. If your backend runs on a different host/port, update `src/app/app.config.ts` and change `API_BASE` accordingly.

### H2 Console Access

When you access http://localhost:8080/h2-console, use the following credentials:
- **URL**: jdbc:h2:mem:librarydb
- **User Name**: sa
- **Password**: (leave blank)

## Key Features

### Encapsulation
- All entity fields are private
- Getters provided for all fields
- Setters only for fields that are genuinely meant to be changed after creation
- `membershipDate` and `borrowDate` are immutable after creation

### Validation
- Email format validation using `@Email` annotation
- Required field validation using `@NotBlank` annotation
- Positive number validation using `@Positive` annotation
- Server-side validation with meaningful error messages

### Transaction Management
- Borrowing and returning operations are transactional
- Ensures data consistency when multiple operations are involved

### DTOs (Data Transfer Objects)
- Entities are never exposed directly in API responses
- DTOs flatten nested objects to prevent circular references
- Separate request and response DTOs

### Constructor-Based Dependency Injection
- All services use constructor-based injection
- Improves testability and makes dependencies explicit

## Configuration

### Application Properties
The `application.properties` file contains:
- H2 database connection details
- Hibernate DDL auto strategy (update)
- SQL logging configuration
- H2 console enablement

## Testing with cURL

### Create a Member
```bash
curl -X POST http://localhost:8080/members \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane Smith","email":"jane@example.com"}'
```

### Get a Member
```bash
curl http://localhost:8080/members/1
```

### Create a Book
```bash
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{"title":"The Hobbit","author":"J.R.R. Tolkien","isbn":"978-0547928227","totalCopies":3}'
```

### Borrow a Book
```bash
curl -X POST http://localhost:8080/members/1/borrow/1
```

### Return a Book
```bash
curl -X POST http://localhost:8080/borrow-records/1/return
```

## Notes

- The H2 database is in-memory, so all data will be lost when the application stops
- To persist data, modify the datasource URL in `application.properties` to use a file-based H2 database
- Pagination defaults: page=0, size=10
- All timestamps are automatically managed by the application

## Future Enhancements

- Add user authentication and authorization
- Implement book reservation system
- Add fine calculation for overdue books
- Generate reports on borrowing patterns
- Add API documentation using Swagger/OpenAPI
- Implement book categories and advanced search

