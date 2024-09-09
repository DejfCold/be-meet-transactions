CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE some_table
(
    id         UUID NOT NULL PRIMARY KEY,
    some_value TEXT
);