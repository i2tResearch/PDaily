CREATE TABLE IF NOT EXISTS subs_accounts
(
    id     UUID         NOT NULL PRIMARY KEY,
    tax_id VARCHAR(15)  NOT NULL,
    name   VARCHAR(100) NOT NULL
) WITH "template=partitioned, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS subs_licenses
(
    id   UUID         NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
) WITH "template=partitioned, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS subs_users
(
    id         UUID         NOT NULL,
    given_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    username   VARCHAR(25)  NOT NULL,
    password   VARCHAR(400) NOT NULL,
    account_id UUID         NOT NULL,
    license_id UUID         NULL,
    PRIMARY KEY (account_id, id)
) WITH "template=partitioned, affinity_key=account_id, atomicity=TRANSACTIONAL_SNAPSHOT";
