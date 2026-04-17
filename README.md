# Fraud Detection System (Event-Driven Microservices)

---
<!-- TOC -->
* [Fraud Detection System (Event-Driven Microservices)](#fraud-detection-system-event-driven-microservices)
  * [Overview](#overview)
  * [Prerequisites](#prerequisites)
  * [Tech Stack](#tech-stack)
    * [Backend Services](#backend-services)
    * [Messaging / Streaming](#messaging--streaming)
    * [Databases](#databases)
    * [Demo Scripting / Simulation](#demo-scripting--simulation)
    * [Build Tools](#build-tools)
    * [Infrastructure](#infrastructure)
  * [Architecture / Data Flow](#architecture--data-flow)
  * [Use](#use)
    * [Build services:](#build-services)
    * [Start infrastructure:](#start-infrastructure)
    * [Run demo and query script](#run-demo-and-query-script)
    * [View logs per container](#view-logs-per-container)
    * [Clean shutdown](#clean-shutdown)
<!-- TOC -->

---
## Overview
This project is a distributed, event-driven fraud detection system built within a transaction processing pipeline. It simulates payment transactions, evaluates them for fraud risk in real-time, and persists transaction and risk assessment data via PostgreSQL.

The system is run as a set of microservices by Docker, communicating asynchronously via Kafka, with persistence handled via relational databases and service-specific logic in Java (Spring Boot).

Fraud detection is based on risk scoring and the `FraudDetectionService` may query the database to use historical data as a means of comparison to the current event.


## Prerequisites
- Java 17+
- Maven 3.8+
- Python 3.10+
- Docker + Docker Compose

---
## Tech Stack
### Backend Services
- Java 17+
- Spring Boot
- Spring Kafka (event streaming)
- Spring Data JPA (persistence layer)
- Hibernate ORM

### Messaging / Streaming
- Apache Kafka 

### Databases
- PostgreSQL

### Demo Scripting / Simulation
- Python 3.x (transaction generator / demo load scripts)

### Build Tools
- Maven

### Infrastructure
Docker / Docker Compose

---
## Architecture / Data Flow
1. `client` (Python script) generates transaction requests (POST) to `/payments`
    - And another Python script to generate (GET) requests to the `/transactions` endpoint, to return SQL tables
2. `transaction-service`:
   - Receives request
   - Stores initial record
   - Emits Kafka event
3. `fraud-service`:
   - Consumes event
   - Computes risk score
   - Stores enriched transaction result
   - Publishes decision event
4. `transaction-service`:
   - Consumes fraud result event
   - Updates transaction status (`APPROVED`/ `FLAGGED` / `BLOCKED`)

A `notification-service` module was also planned, but it ended up being removed as the code was becoming spaghetti
- This is not ideal, but notifications have been moved to the `fraud-service`
- The `fraud-service` still has a Kafka producer, so the `notification-service` module can still be added back in at a later date



---
## Use
### Build services:
`mvn clean package`

### Start infrastructure:
`docker compose up --build`
- Expect this step to take several minutes...

### Run demo and query script
In a separate terminal window:   
`python3 demo-script.py`   
`python3 demo-query-transactions.py`

### View logs per container
In the original terminal window:   
`docker compose logs -f fraud-service`

### Clean shutdown
`docker compose down -v`
- Clean reset, no database persistence, seeded anew next run


---
## Future improvements:
- Replace rules-based risk scoring with statistical/ML model
- Add robust error handling and dead-letter queue for Kafka failures
- Add modularised `notification-service` back in

