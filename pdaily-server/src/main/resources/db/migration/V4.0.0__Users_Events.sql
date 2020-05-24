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

CREATE TABLE IF NOT EXISTS event_physical_events
(
    tenant_id           UUID        NOT NULL,
    id                  UUID        NOT NULL,
    patient_id          UUID        NOT NULL,
    intensity           INT         NOT NULL,
    injury_type_id      UUID        NOT NULL,
    initial_date        BIGINT      NOT NULL,
    final_date          BIGINT      NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS event_physical_body_details
(
    id                  UUID        NOT NULL,
    physical_event_id   UUID        NOT NULL,
    body_part_id        UUID        NOT NULL,
    PRIMARY KEY (id)
)  WITH "template=partitioned, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS event_food_events
(
    tenant_id           UUID        NOT NULL,
    id                  UUID        NOT NULL,
    patient_id          UUID        NOT NULL,
    date                BIGINT      NOT NULL,
    PRIMARY KEY (tenant_id, id)
)  WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS event_animic_events
(
    tenant_id           UUID        NOT NULL,
    id                  UUID        NOT NULL,
    patient_id          UUID        NOT NULL,
    type                UUID        NOT NULL,
    ocurrence_date      BIGINT      NOT NULL,
    PRIMARY KEY (tenant_id, id)
)  WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";