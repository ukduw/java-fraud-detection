#!/bin/bash

BASE_URL="http://localhost:8083/transactions"

echo "---- ALL TRANSACTIONS ----"
curl -s $BASE_URL | jq

echo ""
echo "---- USER user-1 ----"
curl -s $BASE_URL/user/user-1 | jq

