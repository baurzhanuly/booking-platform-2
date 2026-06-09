# Booking Platform

Система бронирования ресурсов (столы в ресторане, книги в библиотеке, билеты)
с оркестрацией бизнес-процессов через **Camunda 8**.

## Стек

| Слой | Технологии |
|---|---|
| Backend | Java 21, Spring Boot 3.2, Spring Data JPA |
| Процессы | Camunda 8 (Zeebe Engine) |
| Security | Spring Security, Keycloak 24, OAuth2/JWT |
| Messaging | Apache Kafka |
| Cache | Redis |
| DB | PostgreSQL 16 + Flyway |
| Gateway | Spring Cloud Gateway |
| Observability | Prometheus, Grafana, Zipkin |
| Testing | JUnit 5, Testcontainers, Mockito |

## Запуск инфраструктуры

```bash
cd docker
docker-compose up -d
```

### UI endpoints после запуска

| Сервис | URL | Credentials |
|---|---|---|
| Keycloak Admin | http://localhost:8080 | admin / admin |
| Camunda Operate | http://localhost:8081 | demo / demo |
| Kafka UI | http://localhost:8090 | — |
| Grafana | http://localhost:3000 | admin / admin |
| Zipkin | http://localhost:9411 | — |

## Структура модулей

```
booking-platform/
├── booking-core/          # Общие DTO, exceptions, enums
├── booking-service/       # REST API бронирований + Zeebe client
├── resource-service/      # CRUD ресурсов (столы/книги/билеты)
├── notification-service/  # Email/SMS уведомления (Kafka consumer)
├── camunda-workers/       # @JobWorker компоненты + BPMN файлы
├── booking-gateway/       # Spring Cloud Gateway + Rate Limiting
└── docker/                # docker-compose, prometheus, keycloak realm
```

## Сборка

```bash
# Сборка всех модулей
mvn clean install

# Запуск конкретного сервиса
cd booking-service
mvn spring-boot:run
```

## BPMN процесс

Основной процесс бронирования `booking-process.bpmn`:

```
[Start] → [Validate Request] → [Check Availability]
       → [Reserve Resource]  → [Send Confirmation]
       → [Wait Payment / Timeout 30min]
       → ✓ [Confirm Booking] → [Notify User] → [End]
       → ✗ [Release Resource] → [Notify Cancellation] → [End]
```
