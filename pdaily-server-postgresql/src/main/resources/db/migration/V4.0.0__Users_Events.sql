CREATE TABLE IF NOT EXISTS event_physical_events
(
    tenant_id           UUID            NOT NULL,
    id                  UUID            NOT NULL    PRIMARY KEY ,
    patient_id          UUID            NOT NULL,
    intensity           INT             NOT NULL,
    injury_type_id      UUID            NOT NULL,
    initial_date        TIMESTAMP       NOT NULL,
    final_date          TIMESTAMP       NOT NULL,
    FOREIGN KEY (injury_type_id) REFERENCES event_physical_injury_types (id)
);

CREATE TABLE IF NOT EXISTS event_physical_body_details
(
    id                  UUID        NOT NULL    PRIMARY KEY ,
    physical_event_id   UUID        NOT NULL,
    body_part_id        UUID        NOT NULL,
    FOREIGN KEY (physical_event_id) REFERENCES event_physical_events (id),
    FOREIGN KEY (body_part_id) REFERENCES event_physical_body_parts (id)
);

CREATE TABLE IF NOT EXISTS event_food_events
(
    tenant_id           UUID            NOT NULL,
    id                  UUID            NOT NULL    PRIMARY KEY ,
    patient_id          UUID            NOT NULL,
    date                TIMESTAMP       NOT NULL
);

CREATE TABLE IF NOT EXISTS event_animic_events
(
    tenant_id           UUID            NOT NULL,
    id                  UUID            NOT NULL    PRIMARY KEY ,
    patient_id          UUID            NOT NULL,
    type                UUID            NOT NULL,
    ocurrence_date      TIMESTAMP       NOT NULL,
    FOREIGN KEY (type) REFERENCES cb_animic_types (id)
);

CREATE TABLE IF NOT EXISTS event_levodopa_events
(
    tenant_id           UUID            NOT NULL,
    id                  UUID            NOT NULL    PRIMARY KEY ,
    patient_id          UUID            NOT NULL,
    levodopa_id         UUID            NOT NULL,
    date                TIMESTAMP       NOT NULL,
    FOREIGN KEY (levodopa_id) REFERENCES medicine_levodopa(id)
);