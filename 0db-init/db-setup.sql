CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR PRIMARY KEY,
    name VARCHAR NOT NULL,
    home_location VARCHAR,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id VARCHAR PRIMARY KEY,
    user_id VARCHAR REFERENCES users(user_id),
    qty NUMERIC,
    location VARCHAR,
    timestamp TIMESTAMP,
    status VARCHAR,
    risk_score INT,
    created_at TIMESTAMP DEFAULT NOW()
);

INSERT INTO users (user_id, name, home_location)
VALUES
    ('user-1', 'Alice', 'London'),
    ('user-2', 'Bob', 'Frankfurt'),
    ('user-3', 'Charlie', 'Paris'),
    ('user-4', 'Malice', 'Shanghai'),
    ('user-5', 'Mob', 'New York'),
    ('user-6', 'Mharlie', 'Dublin')
ON CONFLICT (user_id) DO NOTHING;

INSERT INTO transactions (transaction_id, user_id, qty, location, timestamp, status, risk_score)
VALUES
      ('tx-1', 'user-1', 200, 'London', NOW() - INTERVAL '1 day', 'APPROVED', 0),
      ('tx-2', 'user-1', 75, 'London', NOW() - INTERVAL '2 hours', 'APPROVED', 0),
      ('tx-3', 'user-2', 50, 'Frankfurt', NOW() - INTERVAL '3 hours', 'APPROVED', 0)
ON CONFLICT (transaction_id) DO NOTHING;
