CREATE TABLE IF NOT EXISTS market_attributes
(
    tenant_id   UUID         NOT NULL,
    id          UUID         NOT NULL PRIMARY KEY,
    business_id UUID         NOT NULL,
    label       VARCHAR(250) NOT NULL,
    subjectType VARCHAR(8)   NOT NULL,
    dataType    VARCHAR(20)  NOT NULL
);
CREATE TABLE IF NOT EXISTS market_attribute_picklist_details
(
    tenant_id    UUID         NOT NULL,
    id           UUID         NOT NULL PRIMARY KEY,
    attribute_id UUID         NOT NULL,
    list_value   VARCHAR(100) NOT NULL,
    FOREIGN KEY (attribute_id) REFERENCES market_attributes (id)
);
CREATE TABLE IF NOT EXISTS market_attribute_value
(
    tenant_id    UUID  NOT NULL,
    id           UUID  NOT NULL PRIMARY KEY,
    subject_id   UUID  NOT NULL,
    attribute_id UUID  NOT NULL,
    value        JSONB NOT NULL,
    is_deleted   BOOLEAN,
    FOREIGN KEY (attribute_id) REFERENCES market_attributes (id)
);
CREATE INDEX ON market_attribute_value USING GIN (value jsonb_path_ops);
