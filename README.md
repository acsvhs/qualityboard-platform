# QualityBoard Platform

QualityBoard is an independent portfolio project for registering and reviewing automated test runs through a Spring Boot API and a React dashboard.

It demonstrates:
- Java 21, Spring Boot, Maven and embedded Tomcat
- REST APIs documented with OpenAPI and Swagger UI
- PostgreSQL, SQL migrations, JPA and Hibernate
- JUnit and Mockito tests
- JavaScript, React, Node.js, npm, HTML, CSS and Vite
- PowerShell development scripts and GitHub Actions
- Sonar-compatible project configuration

This project was created as an original portfolio exercise. It contains no proprietary code, data, naming or internal implementation from any employer.

## Run locally

Requirements: JDK 21, Maven, Node.js 24+, npm and Docker Desktop.

1. Start PostgreSQL:

   docker compose up -d db

2. Start the API:

   cd backend
   mvn spring-boot:run

3. In another terminal, start the frontend:

   cd frontend
   npm install
   npm run dev

Open http://localhost:5173. Swagger UI is available at http://localhost:8080/swagger-ui.html.

On Windows:

   powershell -ExecutionPolicy Bypass -File scripts/dev.ps1

## Verify

Backend:

   cd backend
   mvn test

Frontend:

   cd frontend
   npm install
   npm run build

Sonar, after configuring a server and token:

   cd backend
   mvn verify sonar:sonar

## API

- GET /api/runs
- POST /api/runs
- PATCH /api/runs/{id}/status?value=PASSED
- DELETE /api/runs/{id}

The application registers run metadata; it does not claim to execute Selenium, Playwright or Appium. Those technologies are demonstrated in the companion laboratories.
