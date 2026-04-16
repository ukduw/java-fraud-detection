placeholder

high-level architecture:
- client
- payment service (spring boot api)
- kafka topic (transactions)
- fraud detection service (consumer)
- database service + notification service

`client -> PaymentController -> PaymentService -> KafkaProducer -> Kafka Topic -> FraudConsumer -> FraudService -> NotificationConsumer -> NotificationService`
1. A `POST` request hits `/payments`
2. Inside `payment-service`:
   1. `PaymentController` receives HTTP request
      - Spring automatically:
        - Parses JSON -> `TransactionEvent`
        - Injects `PaymentService`
   2. `PaymentService` calls:
      - `producer.sendTransaction(event);`
   3. `TransactionProducer` uses:
      - `KafkaTemplate.send(...)`
      - **Spring's job is over, Kafka takes over**
3. Inside `Kafka`:
   - Message is written to a `topic`: `"transactions"`
   - Kafka persists it
   - Any `consumer` subscribed to that `topic` gets it
4. Inside `fraud-service`:
   - Spring Kafka:
     - `@KafkaListener(...)`
     - Spring runs a background thread that continuously polls Kafka
     - When a message arrives, it calls the method (event-driven)
       - `public void consume(TransactionEvent event)`
     - Then, `Kafka -> TransactionConsumer -> FraudDetectionService`
5. `notification-service` does the same
   - Now there are two independent consumers reading the **same topic**
   - This is **fan-out architecture** via Kafka - **multiple services reacting to the same event**

3 microservices:
- payment-service (producer)
- fraud-service (consumer)
- notification-service (consumer)

Docker:
- `User` -> `localhost:8080` (`payment-service`)
  - `payment-service` -> `kafka` (`container`)
    - `kafka` -> `fraud-service` (`container`)
    - `kafka` -> `notification-service` (`container`)

Note:
- local dev: `localhost:9092`
- docker: `kafka:9092`


TODO:
- FINALLY WORKS... just have to:
  - fix demo scripts (bad request; json fields probably don't match expected; not saved to db)
    - transactions request script working fine (demo-query-transactions)
    - the sql script is local, so ./script.sql no longer makes sense within a docker exec line...?
      - may be easier to just have a full copy-paste-able line in the README
  - write README

i removed notification-service module because it was becoming spaghetti...
- not ideal, but notifications are in fraud-service


- mvn clean package
- docker compose up --build

- run demo script(s) in separate terminal window
    - python3 demo-script.py
    - python3 demo-query-transactions.py
    - or,
    - ./demo-script.sh
    - ./demo-query-transactions.sh
- OR:
- docker compose logs
- or, docker compose logs -f fraud-service (send requests then watch logs live...)

- run sql query script
    - docker exec -it postgres psql -U postgres -d transactionsdb -f ./demo-db-query.sql

- docker compose down -v
- (clean reset, no db persistence - db seeded anew next run...)

