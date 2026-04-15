CREATE TABLE users (
                       user_id VARCHAR PRIMARY KEY,
                       name VARCHAR NOT NULL,
                       home_location VARCHAR,
                       created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE transactions (
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
    ('user-1', 'Alice', 'London', 'London'),
    ('user-2', 'Bob', 'Paris', 'Paris'),
    ('user-3', 'Charlie', 'New York', 'New York');

INSERT INTO transactions (
    transaction_id, user_id, qty, location, timestamp, status, risk_score
) VALUES
      ('tx-100', 'user-1', 200, 'London', NOW() - INTERVAL '1 day', 'APPROVED', 0),

      ('tx-101', 'user-1', 15000, 'Tokyo', NOW() - INTERVAL '2 hours', 'FLAGGED', 140),

      ('tx-102', 'user-2', 50, 'Paris', NOW() - INTERVAL '3 hours', 'APPROVED', 0);


--