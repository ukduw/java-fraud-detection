#!/bin/bash

declare -a locations=("London" "Frankfurt" "Paris" "Shanghai" "New York" "Dublin")
declare -a fraudcations=("Stockholm" "Canberra" "Minsk" "Buenos Aires" "Kabul" "Copenhagen")

for i in {1..6}
do
  curl -X POST http://localhost:8080/payments \
    -H "Content-Type: application/json" \
    -d "{
      \"transactionId\": \"tx-$i\",
      \"userId\": \"user-$i\",
      \"qty\": $(shuf -i 5-100 -n 1),
      \"location\": ${location[$((i-1))]}
    }"
done
sleep 5


# fraud (qty, location)
for i in {1..6}
do
  curl -X POST http://localhost:8080/payments \
    -H "Content-Type: application/json" \
    -d "{
      \"transactionId\": \"tx-$((i+6))\",
      \"userId\": \"user-$i\",
      \"qty\": $(shuf -i 500-10000 -n 1),
      \"location\": ${fraudcations[$((i-1))]}
    }"
done
sleep 5

# high velocity test
for i in {1..6}
do
  curl -X POST http://localhost:8080/payments \
    -H "Content-Type: application/json" \
    -d "{
      \"transactionId\": \"tx-$((i+12))\",
      \"userId\": \"user-1\",
      \"qty\": $(shuf -i 5-1000 -n 1),
      \"location\": ${locations[1]}
    }"
done
