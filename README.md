# HabitTracker

HabitTracker — REST API для управления привычками и отслеживания их выполнения.

## Технологии

- Java 26
- Spring Boot
- Spring Web
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- Liquibase
- Maven
- Swagger / OpenAPI
- JUnit
- Mockito
- MockMvc

## Запуск проекта

### 1. Запустить PostgreSQL через Docker

```bash
docker run --name habit-postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=habit_tracker \
  -e POSTGRES_USER=postgres \
  -p 5432:5432 \
  -d postgres:15