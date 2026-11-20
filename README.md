# Library Management System

[![CI](https://github.com/sravani551/LibraryManagementSystem/actions/workflows/ci.yml/badge.svg)](https://github.com/sravani551/LibraryManagementSystem/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F)
![Angular](https://img.shields.io/badge/Angular-17-DD0031)

A full-stack library application: a **Spring Boot REST API** for members, books and loans, and an
**Angular** frontend (standalone components) that consumes it.

```
backend/    Spring Boot 3 API (JPA, Bean Validation, OpenAPI)
frontend/   Angular 17 SPA
```

## Features

- Register members and add books (unique email / ISBN)
- Search books by title with pagination
- Borrow and return books, with available-copy tracking and per-member loan history
- Interactive API docs (Swagger UI)

## API

| Method | Path | Description | Success |
|---|---|---|---|
| `POST` | `/members` | Register a member `{name, email}` | 201 |
| `GET` | `/members?page=&size=&sortBy=` | List members (paginated, sortable) | 200 |
| `GET` | `/members/{id}` | Get a member | 200 |
| `POST` | `/books` | Add a book `{title, author, isbn, totalCopies}` | 201 |
| `GET` | `/books?title=&page=&size=` | Search books by title | 200 |
| `POST` | `/members/{memberId}/borrow/{bookId}` | Borrow a copy | 201 |
| `POST` | `/borrow-records/{id}/return` | Return a loan | 200 |
| `GET` | `/members/{memberId}/borrow-records` | A member's loan history | 200 |

Errors: `400` with a per-field map for validation failures and malformed requests; `404` for unknown
members, books or loans; `409` when no copies are left, a loan was already returned, an ISBN or email
already exists, or a concurrent update won the race. Non-validation errors return `{"message": "..."}`.

Swagger UI: http://localhost:8080/swagger-ui.html

## Design notes

- **Layered architecture**: controllers → services (transaction boundaries) → Spring Data repositories;
  DTOs at the API boundary, so entities are never serialized directly.
- **Concurrency-safe borrowing**: `Book` uses optimistic locking (`@Version`), so when several members
  try to borrow the last copy at the same moment exactly one succeeds and the rest get `409`.
  A multi-threaded test covers this.
- **Constraints in the database**: unique ISBN and email are enforced by the schema; violations map
  to `409` rather than a generic `500`.
- **Configuration from the environment**: database URL and credentials, CORS origins
  (`CORS_ALLOWED_ORIGINS`), H2 console and SQL logging are environment variables with
  development defaults.

## Run locally

Backend (Java 17), with in-memory H2 at http://localhost:8080:

```bash
cd backend
./mvnw spring-boot:run
```

Frontend (Node 20) at http://localhost:4200:

```bash
cd frontend
npm ci
npm start
```

## Tests and CI

```bash
cd backend && ./mvnw verify
```

- API integration tests (MockMvc + H2): full borrow/return lifecycle, validation, 404/409 mapping,
  malformed input
- A concurrency test: 8 threads race for the last copy and exactly one loan is created

GitHub Actions builds and tests the backend and builds the frontend on every push and pull request.

## Roadmap

- Due dates and overdue tracking
- Authentication with roles (librarian / member)
- Flyway migrations and PostgreSQL profile
