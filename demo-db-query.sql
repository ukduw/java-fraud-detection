SELECT u.user_id, u.home_location, t.qty
FROM users u
         JOIN transactions t ON u.user_id = t.user_id;

SELECT *
FROM transactions
WHERE location NOT IN (
    SELECT home_location FROM users WHERE users.user_id = transactions.user_id
);