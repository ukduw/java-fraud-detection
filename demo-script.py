import requests
import random

for i in range(10):
    requests.post("http://localhost:8080/payments", json={
        "transactionId": f"tx-{i}",
        "userId": f"user-{i}",
        "qty": random.randint(100, 20000),
        "location": "London" # generate randomly...?
    })