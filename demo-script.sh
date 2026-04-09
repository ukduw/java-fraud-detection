#!/bin/bash

declare -a locations=("London" "Frankfurt" "Paris" "Shanghai" "New York" "Dublin")

for i in {1..10}
do
  curl -X POST http://localhost:8080/payments \
    -H "Content-Type: application/json" \
    -d "{
      \"transactionId\": \"tx-$i\",
      \"userId\": \"user-$i\",
      \"qty\": $((RANDOM % 20000)),
      \"location\": ${location[$((RANDOM % 5))]}
    }"
done


