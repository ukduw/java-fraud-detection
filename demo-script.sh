#!/bin/bash

for i in {1..10}
do
  curl -X POST http://localhost:8080/payments \
    -H "Content-Type: application/json" \
    -d "{
      \"transactionId\": \"tx-$i\",
      \"userId\": \"user-$i\",
      \"amount\": $((RANDOM % 20000))
    }"
done


