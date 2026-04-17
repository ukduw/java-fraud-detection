import requests
import random
import time
from datetime import datetime, timezone

locations = ["London", "Frankfurt", "Paris", "Shanghai", "New York", "Dublin"]
fraudcations = ["Stockholm", "Canberra", "Minsk", "Buenos Aires", "Kabul", "Copenhagen"]

for i in range(6):
    requests.post("http://localhost:8080/payments", json={
        "transactionId": f"tx-{i+19}",
        "userId": f"user-{i+1}",
        "qty": random.randint(5, 100),
        "location": locations[i],
        "timestamp": datetime.now(timezone.utc).replace(microsecond=0).isoformat(),
        "status": "PENDING"
    })
time.sleep(5)


# fraud (qty, location)
for i in range(6):
    requests.post("http://localhost:8080/payments", json={
        "transactionId": f"tx-{i+25}",
        "userId": f"user-{i+1}",
        "qty": random.randint(500, 10000),
        "location": fraudcations[i],
        "timestamp": datetime.now(timezone.utc).replace(microsecond=0).isoformat(),
        "status": "PENDING"
    })
time.sleep(5)

# high velocity test
for i in range(5):
    requests.post("http://localhost:8080/payments", json={
        "transactionId": f"tx-{i+31}",
        "userId": f"user-1",
        "qty": random.randint(5, 1000),
        "location": locations[0],
        "timestamp": datetime.now(timezone.utc).replace(microsecond=0).isoformat(),
        "status": "PENDING"
    })


