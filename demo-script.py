import requests
import random

locations = ["London", "Frankfurt", "Paris", "Shanghai", "New York", "Dublin"]

for i in range(10):
    requests.post("http://localhost:8080/payments", json={
        "transactionId": f"tx-{i}",
        "userId": f"user-{i}",
        "qty": random.randint(100, 20000),
        "location": locations[random.randint(0, 5)]
    })


# random location doesn't make sense
# maybe small chance of location being changed - only that transaction should be flagged
# or, write separate scripts to demonstrate different fraud logic