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
      ('tx-1', 'user-1', 20, 'London', NOW() - INTERVAL '1 day', 'APPROVED', 0),
      ('tx-2', 'user-1', 75, 'London', NOW() - INTERVAL '2 hours', 'APPROVED', 0),
      ('tx-3', 'user-1', 50, 'London', NOW() - INTERVAL '3 hours', 'APPROVED', 0),

      ('tx-4', 'user-2', 200, 'Frankfurt', NOW() - INTERVAL '2 day', 'APPROVED', 0),
      ('tx-5', 'user-2', 750, 'Frankfurt', NOW() - INTERVAL '6 hours', 'APPROVED', 0),
      ('tx-6', 'user-2', 500, 'Frankfurt', NOW() - INTERVAL '1 hours', 'APPROVED', 0),

      ('tx-7', 'user-3', 2, 'Paris', NOW() - INTERVAL '1 day', 'APPROVED', 0),
      ('tx-8', 'user-3', 7, 'Paris', NOW() - INTERVAL '5 hours', 'APPROVED', 0),
      ('tx-9', 'user-3', 5, 'Paris', NOW() - INTERVAL '4 hours', 'APPROVED', 0),

      ('tx-10', 'user-4', 2000, 'Shanghai', NOW() - INTERVAL '3 day', 'APPROVED', 0),
      ('tx-11', 'user-4', 7500, 'Shanghai', NOW() - INTERVAL '1 hours', 'APPROVED', 0),
      ('tx-12', 'user-4', 5000, 'Shanghai', NOW() - INTERVAL '8 hours', 'APPROVED', 0),

      ('tx-13', 'user-5', 20, 'New York', NOW() - INTERVAL '1 day', 'APPROVED', 0),
      ('tx-14', 'user-5', 75, 'New York', NOW() - INTERVAL '3 hours', 'APPROVED', 0),
      ('tx-15', 'user-5', 50, 'New York', NOW() - INTERVAL '9 hours', 'APPROVED', 0),

      ('tx-16', 'user-6', 200, 'Dublin', NOW() - INTERVAL '1 day', 'APPROVED', 0),
      ('tx-17', 'user-6', 7, 'Dublin', NOW() - INTERVAL '2 hours', 'APPROVED', 0),
      ('tx-18', 'user-6', 500, 'Dublin', NOW() - INTERVAL '2 hours', 'APPROVED', 0),
ON CONFLICT (transaction_id) DO NOTHING;
