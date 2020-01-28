CREATE TABLE IF NOT EXISTS event_physical_body_parts
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL,
    name      VARCHAR(100) NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS event_physical_injury_types
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL,
    name      VARCHAR(100) NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";