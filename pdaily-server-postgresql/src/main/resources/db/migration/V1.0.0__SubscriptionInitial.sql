CREATE TABLE IF NOT EXISTS subs_accounts
(
    id     UUID         NOT NULL PRIMARY KEY,
    tax_id VARCHAR(15)  NOT NULL UNIQUE,
    name   VARCHAR(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS subs_licenses
(
    id   UUID         NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);
