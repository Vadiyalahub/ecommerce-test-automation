-- Test users for the login tests, loaded into an in-memory H2 database.
-- These are the public demo accounts listed on the saucedemo.com login page.

CREATE TABLE IF NOT EXISTS test_users (
    user_type   VARCHAR(30)  PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL,
    password    VARCHAR(50)  NOT NULL,
    description VARCHAR(200)
);

MERGE INTO test_users (user_type, username, password, description) KEY (user_type) VALUES
    ('standard', 'standard_user',   'secret_sauce', 'Normal user, everything works'),
    ('locked',   'locked_out_user', 'secret_sauce', 'User that is blocked from logging in'),
    ('problem',  'problem_user',    'secret_sauce', 'User that sees UI bugs (used for exploratory testing)');
