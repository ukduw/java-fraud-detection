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

