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


### NOTES:
spring boot:   
Each service is its own Spring Boot application with its own:
- Dependency Injection container (IoC container)
- HTTP server (Tomcat)
- Kafka client

labels:
- `@SpringBootApplication`
  - spring scans `com.ed.module.*` for components (`@RestController, @Service, @Component`) and automatically instantiates them
  - detect Kafka/web dependencies -> configures Kafka, starts http server
  - `SpringApplication.run(...)` - starts Spring container, wires dependencies, starts embedded server
- `@RestController` - entry point, handles http
- `@Service` - business logic
- `@Repository` - db access
- `@Component` - infrastructure (e.g. Kafka...)

kafka:
A distributed, persistent event log
- `payment-service -> Kafka -> many consumers`
- Allows for services to be decoupled and scaled, processed async


app, controller, service, model:
- `app` - entry point; starts spring boot app, scans for components, launches embedded server
  - in main: 
    - `@SpringBootApplication`
    - `SpringApplication.run(Application.class, arg);`
- `model` - data object
- `service` - business logic
  - `@Service`
- `controller` - http layer (api, endpoints part...)
    - `@RestController`
    - `@RequestMapping("/users")`, `@PostMapping, @RequestParam`, `@GetMapping("/{id}"), @PathVariable`

`HTTP request -> controller (@RestController) -> service (@Service) -> returns obj -> Spring converts to JSON`
- e.g. `POST /users?name=Bob` ->
  - `{"id": 123, "name": "Bob", ...}`


Docker:
- `User` -> `localhost:8080` (`payment-service`)
  - `payment-service` -> `kafka` (`container`)
    - `kafka` -> `fraud-service` (`container`)
    - `kafka` -> `notification-service` (`container`)

Note:
- local dev: `localhost:9092`
- docker: `kafka:9092`


TODO:
- implement (stateful) fraud detection logic
  - userId, location, timestamp
  - query db on transaction
  - if location changes too fast...
  - amount anomaly (usually 1-75, suddenly 10k)
  - risk score rather than binary
    - e.g. flag on 50-69, flag/noti/block on >= 70
- psql setup scripts
  - users
  - transactions
- demo scripts, including to fraud demo(s)
- run with docker, test...
- write README

