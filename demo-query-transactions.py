import requests
import json

BASE_URL = "http://localhost:8083/transactions"

# Function to pretty-print JSON data
def print_json(data):
    print(json.dumps(data, indent=2))

# Get all transactions
print("---- ALL TRANSACTIONS ----")
response = requests.get(BASE_URL)
if response.status_code == 200:
    print_json(response.json())
else:
    print(f"Error fetching all transactions: {response.status_code}")

# Get user specific data
print("\n---- USER user-1 ----")
response = requests.get(f"{BASE_URL}/user/user-1")
if response.status_code == 200:
    print_json(response.json())
else:
    print(f"Error fetching user data for user-1: {response.status_code}")