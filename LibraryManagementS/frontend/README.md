# Library Management Frontend (Angular)

This is a minimal Angular 17+ standalone components frontend that consumes the existing Spring Boot API at http://localhost:8080.

Quick start (after installing Node.js & Angular CLI):

1. cd frontend
2. npm install
3. ng serve --open

Notes:
- Uses standalone components (no NgModules).
- HttpClient is provided via provideHttpClient() in app config.
- Router is configured via provideRouter().

Files of interest:
- src/main.ts — bootstrap application
- src/app/app.config.ts — API base and provider list
- src/app/models — TypeScript interfaces
- src/app/services — HttpClient-based services
- src/app/components — navbar, error banner
- src/app/pages — pages for members/books/borrow history


