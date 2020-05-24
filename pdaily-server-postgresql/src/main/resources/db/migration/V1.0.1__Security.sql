CREATE TABLE IF NOT EXISTS security_users
(
    id              UUID         NOT NULL PRIMARY KEY,
    given_name      VARCHAR(100) NOT NULL,
    last_name       VARCHAR(100) NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    username        VARCHAR(25)  NOT NULL UNIQUE,
    hash_iterations INT          NOT NULL,
    salt            VARCHAR(400) NOT NULL,
    password        VARCHAR(400) NOT NULL,
    type            CHAR(1)      NOT NULL,
    account_id      UUID         NULL,
    license_id      UUID         NULL,
    FOREIGN KEY (account_id) REFERENCES subs_accounts (id)
);
CREATE TABLE IF NOT EXISTS security_auth_groups
(
    id   VARCHAR(36)  NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS security_auth_activities
(
    id          VARCHAR(36)  NOT NULL,
    group_id    VARCHAR(36)  NOT NULL,
    description VARCHAR(100) NOT NULL,
    PRIMARY KEY (group_id, id),
    FOREIGN KEY (group_id) REFERENCES security_auth_groups (id)
);
CREATE TABLE IF NOT EXISTS security_auth_user_permissions
(
    user_id     UUID        NOT NULL,
    tenant_id   UUID        NOT NULL,
    activity_id VARCHAR(36) NOT NULL,
    granted_on  TIMESTAMP   NOT NULL,
    granted_by  VARCHAR(25) NOT NULL,
    PRIMARY KEY (user_id, tenant_id, activity_id)
);
