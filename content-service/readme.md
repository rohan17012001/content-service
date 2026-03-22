# Content Service

## 📌 Overview

The **Content Service** is the entry point of the AI Platform. It is responsible for accepting user-generated content, persisting it reliably, and publishing events to downstream services for further processing.

This service follows a **microservices + event-driven architecture**, where it focuses solely on **data ingestion and event production**, without performing any business intelligence or notification logic.

---

## 🎯 Responsibilities

The Content Service is responsible for:

- Accepting content via REST APIs
- Validating and normalizing input data
- Persisting raw content in MongoDB
- Publishing events to Kafka (`content.created`)
- Acting as the source of truth for all incoming content

---

## ❌ Non-Responsibilities

This service does **NOT**:

- Perform AI processing
- Send notifications
- Manage users or authentication
- Handle downstream workflows

---

## 🏗️ Architecture Role

```
Client → Content Service → Kafka → AI Orchestrator → Notification Service
```

This service acts as the **producer** in the event-driven pipeline.

---

## 🧱 Tech Stack

| Component  | Technology          |
| ---------- | ------------------- |
| Language   | Java 17             |
| Framework  | Spring Boot         |
| Database   | MongoDB             |
| Messaging  | Apache Kafka        |
| Build Tool | Maven               |
| API Docs   | Swagger (Springdoc) |

---

## 📂 Project Structure

```
src/main/java/com/rohan/content_service

├── controller      # REST endpoints
├── service         # Business logic
├── repository      # MongoDB access
├── model           # MongoDB documents
├── event           # Kafka event models
├── config          # Configuration classes
└── ContentServiceApplication.java
```

---

## ⚙️ Configuration

### application.yml

```yaml
server:
  port: 8081

spring:
  application:
    name: content-service

  data:
    mongodb:
      uri: mongodb://localhost:27017/contentdb

  kafka:
    bootstrap-servers: localhost:29092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
```

---

## 🚀 How to Run

### 1️⃣ Start Infrastructure

From `ai-platform-infra`:

```bash
docker compose up -d
```

This starts:

- Kafka
- MongoDB
- Redis
- PostgreSQL

---

### 2️⃣ Run Service

```bash
mvn spring-boot:run
```

---

### 3️⃣ Verify Service

```bash
GET http://localhost:8081/actuator/health
```

Expected:

```json
{
  "status": "UP"
}
```

---

## 📡 API Endpoints

### ➤ Create Content

```http
POST /content
```

#### Request Body

```json
{
  "type": "INCIDENT",
  "rawText": "Redis latency spike detected",
  "authorId": "user1",
  "teamId": "team1"
}
```

#### Response

```json
{
  "id": "...",
  "contentId": "...",
  "type": "INCIDENT",
  "rawText": "...",
  "authorId": "...",
  "teamId": "...",
  "createdAt": "..."
}
```

---

## 🗄️ Database Schema (MongoDB)

### Collection: `content`

```json
{
  "_id": "ObjectId",
  "contentId": "UUID",
  "type": "INCIDENT | DECISION | QUESTION",
  "rawText": "String",
  "authorId": "String",
  "teamId": "String",
  "createdAt": "Timestamp"
}
```

---

## 📬 Kafka Integration

### Topic: `content.created`

This service publishes events whenever new content is created.

---

### Event Schema

```json
{
  "eventType": "CONTENT_CREATED",
  "eventId": "UUID",
  "occurredAt": "TIMESTAMP",
  "payload": {
    "contentId": "UUID",
    "contentType": "INCIDENT",
    "authorId": "String",
    "teamId": "String"
  }
}
```

---

## 🔄 Data Flow

```
POST /content
   ↓
Validate request
   ↓
Save to MongoDB
   ↓
Publish Kafka event (content.created)
   ↓
Return response to client
```

---

## 🧪 Testing the Flow

### 1. Send Request

```bash
curl -X POST http://localhost:8081/content \
-H "Content-Type: application/json" \
-d '{
  "type": "INCIDENT",
  "rawText": "Redis latency spike detected",
  "authorId": "user1",
  "teamId": "team1"
}'
```

---

### 2. Verify MongoDB

```bash
docker exec -it mongo mongosh
```

```js
use contentdb
db.content.find().pretty()
```

---

### 3. Verify Kafka

Open:

```
http://localhost:8085
```

Check topic:

```
content.created
```

---

## ⚠️ Failure Handling

| Scenario      | Behavior                   |
| ------------- | -------------------------- |
| MongoDB down  | Request fails              |
| Kafka down    | Data saved, event may fail |
| Invalid input | 400 response               |

---

## 🧠 Design Principles

- **Single Responsibility**: Only handles content ingestion
- **Event-Driven**: Publishes events for async processing
- **Loose Coupling**: No direct dependency on downstream services
- **Durability First**: Data is stored before event emission

---

## 📈 Future Enhancements

- Add request validation (DTO layer)
- Add API versioning
- Add authentication (via API Gateway)
- Add retry mechanism for Kafka failures
- Add metrics and tracing

---

## 🏁 Summary

The Content Service is the **foundation of the platform**.
It ensures that all incoming data is:

- Captured reliably
- Stored safely
- Distributed efficiently via Kafka

Without this service, no downstream processing can occur.

---
