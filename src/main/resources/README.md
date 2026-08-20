# Backend — Excavation Management System

Spring Boot REST API for the AI-Enhanced Road Work Coordination system.

## Requirements

- Java 21+
- Maven
- PostgreSQL 14+
- Ollama (optional — only needed for the AI features)

## Setup

1. Create the database:
   ```sql
   CREATE DATABASE excavation_db;
   ```

2. Copy the example config and fill in your local values:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

3. Update `application.properties` with your PostgreSQL credentials and Gmail app password.

4. Run the app:
   ```bash
   mvn spring-boot:run
   ```

   Liquibase creates the schema and seeds default departments and road segments automatically on first run.

5. (Optional) Enable AI features:
   ```bash
   ollama pull phi3 model
   ollama serve
   ```

## API overview

| Area | Base path |
|---|---|
| Auth | /api/auth |
| Work requests | /api/work-requests |
| Admin | /api/admin |
| Public / citizen | /api/public |
| AI features | /api/ai |

Full endpoint reference: ../docs/API.md

## Running tests

```bash
mvn test
```