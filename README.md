placeholder

high-level architecture:
- client
- payment service (spring boot api)
- kafka topic (transactions)
- fraud detection service (consumer)
- database service + notification service

3 microservices:
- payment-service (producer)
- fraud-service (consumer)
- notification-service (consumer)


### NOTES:
spring boot:
- `@RestController` - entry point, handles http
- `@Service` - business logic
- `@Repository` - db access
- `@Component` - infrastructure (e.g. Kafka...)

kafka:
- placeholder...



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


