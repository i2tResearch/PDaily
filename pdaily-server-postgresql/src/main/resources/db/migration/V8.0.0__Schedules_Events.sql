CREATE TABLE IF NOT EXISTS programming_food_schedules
(
    tenant_id           UUID        NOT NULL,
    id                  UUID        NOT NULL    PRIMARY KEY ,
    patient_id          UUID        NOT NULL,
    schedule            VARCHAR     NOT NULL
);

CREATE TABLE IF NOT EXISTS programming_levodopa_schedules
(
    tenant_id           UUID        NOT NULL,
    id                  UUID        NOT NULL    PRIMARY KEY ,
    patient_id          UUID        NOT NULL,
    medicine_id         UUID        NOT NULL,
    schedule            VARCHAR     NOT NULL,
    pills_dose          INT         NOT NULL,
    FOREIGN KEY (medicine_id) REFERENCES medicine_levodopa (id)
);

CREATE TABLE IF NOT EXISTS programming_routine_schedules
(
    tenant_id           UUID        NOT NULL,
    id                  UUID        NOT NULL    PRIMARY KEY ,
    patient_id          UUID        NOT NULL,
    type_id             UUID        NOT NULL,
    schedule            VARCHAR     NOT NULL,
    FOREIGN KEY (type_id) REFERENCES cb_routine_types(id)
);