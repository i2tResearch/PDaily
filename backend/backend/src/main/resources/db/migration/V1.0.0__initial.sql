CREATE TABLE doctors
(
    id              UUID PRIMARY KEY,
    name            VARCHAR(255),
    mail            VARCHAR(255),
    role            VARCHAR(50),
    createdOn       TIMESTAMP WITHOUT TIME ZONE,
    UNIQUE (mail)
)

CREATE TABLE patients
(
    id              UUID PRIMARY KEY,
    name            VARCHAR(255),
    document        VARCHAR(255),
    age             INT
    gender          VARCHAR(50),
    createdOn       TIMESTAMP WITHOUT TIME ZONE,
    UNIQUE (document)
)

CREATE TABLE doctors_patients
(
    doctor_id       UUID    NOT NULL,
    patient_id      UUID    NOT NULL
)
