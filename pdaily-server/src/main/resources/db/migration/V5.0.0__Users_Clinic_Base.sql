CREATE TABLE IF NOT EXISTS medicine_levodopa_types
(
    id        UUID         NOT NULL,
    label      VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) WITH "template=partitioned, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS medicine_levodopa
(
    tenant_id  UUID         NOT NULL,
    id         UUID         NOT NULL,
    name       VARCHAR(100) NOT NULL,
    type_id    UUID         NOT NULL,
    dose_value INT          NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS cb_animic_types
(
    id          UUID         NOT NULL,
    label       VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) WITH "template=partitioned, atomicity=TRANSACTIONAL_SNAPSHOT";