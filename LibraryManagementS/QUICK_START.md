# 🚀 Quick Start Guide - Library Management System

## ⚡ 30-Second Setup

```bash
# 1. Navigate to project
cd C:\Users\srava\OneDrive\Desktop\LibraryManagementS

# 2. Build the project
mvn clean install

# 3. Run the application
mvn spring-boot:run
```

The API will be available at: **http://localhost:8080**

---

## 📋 Quick API Reference

### ✅ Working Endpoints (All Implemented)

```
Members:
  POST   /members                          → Create member
  GET    /members/{id}                     → Get member
  GET    /members?page=0&size=10&sortBy=name → List members

Books:
  POST   /books                            → Create book
  GET    /books?title=...&page=0&size=10  → Search books

Borrowing:
  POST   /members/{memberId}/borrow/{bookId}      → Borrow book
  POST   /borrow-records/{id}/return              → Return book
  GET    /members/{memberId}/borrow-records       → View history

Database:
  GET    http://localhost:8080/h2-console → H2 Console
```

---

## 🧪 5-Minute Quick Test

```bash
# Test 1: Create a member
curl -X POST http://localhost:8080/members \
  -H "Content-Type: application/json" \
  -d '{"name":"John","email":"john@example.com"}'

# Test 2: Create a book
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Harry Potter","author":"J.K. Rowling","isbn":"123","totalCopies":5}'

# Test 3: Borrow the book
curl -X POST http://localhost:8080/members/1/borrow/1

# Test 4: View borrow history
curl http://localhost:8080/members/1/borrow-records

# Test 5: Return the book
curl -X POST http://localhost:8080/borrow-records/1/return
```

---

## 📁 Project Structure at a Glance

```
LibraryManagementS/
├── pom.xml                      ← Maven configuration (READY)
├── README.md                    ← Full documentation
├── PROJECT_SUMMARY.md           ← Project overview
├── API_TESTING_EXAMPLES.md      ← cURL examples
├── QUICK_START.md               ← This file
└── src/main/java/com/example/library/
    ├── LibraryManagementApplication.java  ← Main app (READY)
    ├── entity/                 ← 3 Entity classes (READY)
    ├── dto/                    ← 5 DTO classes (READY)
    ├── repository/             ← 3 Repositories (READY)
    ├── service/                ← 3 Services (READY)
    ├── controller/             ← 2 Controllers (READY)
    └── exception/              ← Exception handler (READY)
```

---

## ✨ What's Included

✅ **Spring Boot 3.3.0** with Java 17  
✅ **3 Entity Classes** with proper relationships  
✅ **5 DTOs** for request/response handling  
✅ **3 Repositories** with custom queries  
✅ **3 Services** with business logic  
✅ **2 REST Controllers** with full endpoints  
✅ **1 Global Exception Handler** for error handling  
✅ **H2 In-Memory Database** with auto-schema creation  
✅ **Input Validation** with @NotBlank, @Email, @Positive  
✅ **Constructor-Based Dependency Injection** throughout  
✅ **Transactional Operations** for data consistency  
✅ **Complete Documentation** (README, examples, summary)

---

## 🔧 System Requirements

- Java 17 or higher
- Maven 3.6 or higher
- Any REST client (cURL, Postman, Insomnia, etc.)

---

## 📊 Database Info

**H2 Console**: http://localhost:8080/h2-console
- **Username**: sa
- **Password**: (blank)
- **JDBC URL**: jdbc:h2:mem:librarydb

---

## 🆘 Troubleshooting

| Issue | Solution |
|-------|----------|
| `mvn` not recognized | Install Maven or add to PATH |
| Connection refused | Make sure app is running with `mvn spring-boot:run` |
| 404 errors | Check endpoint spelling and ID values |
| Validation errors | Check JSON format and required fields |

---

## 📚 Documentation Files

1. **README.md** - Complete API documentation with all endpoints
2. **PROJECT_SUMMARY.md** - Project overview and verification checklist
3. **API_TESTING_EXAMPLES.md** - Ready-to-use cURL commands for all scenarios
4. **QUICK_START.md** - This quick reference guide

---

## ✅ Verification Checklist

- [x] All 3 entities created with proper relationships
- [x] All 5 DTOs for request/response handling
- [x] All 3 repositories with custom queries
- [x] All 3 services with business logic
- [x] All REST controllers implemented
- [x] Global exception handler configured
- [x] H2 database configured
- [x] Maven pom.xml ready
- [x] Application properties configured
- [x] Complete documentation provided

---

## 🎯 Next Steps

1. **Build**: `mvn clean install`
2. **Run**: `mvn spring-boot:run`
3. **Test**: Use API_TESTING_EXAMPLES.md for cURL commands
4. **Explore**: Visit H2 console to see data
5. **Customize**: Modify code as needed for your requirements

---

**Status**: ✅ **PROJECT COMPLETE AND READY TO USE**

All files are generated, tested, and ready for deployment. Start coding!

