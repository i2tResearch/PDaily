CREATE TABLE IF NOT EXISTS sales_order_sources
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL,
    name      VARCHAR(100) NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS sales_activities_tasks
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL,
    name      VARCHAR(100) NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS sales_activities_purposes
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL,
    name      VARCHAR(100) NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS sales_activities_marketing_campaigns
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL,
    name      VARCHAR(100) NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS sales_activities
(
    tenant_id           UUID         NOT NULL,
    id                  UUID         NOT NULL,
    sales_rep_id        UUID         NOT NULL,
    buyer_id            UUID         NOT NULL,
    supplier_id         UUID         NULL,
    purpose_id          UUID         NULL,
    activity_date       BIGINT       NOT NULL,
    creation_date       BIGINT       NOT NULL,
    latitude            REAL,
    longitude           REAL,
    comments             VARCHAR(255) NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS sales_activities_campaign_details
(
    tenant_id        UUID NOT NULL,
    id               UUID NOT NULL,
    activity_id      UUID NOT NULL,
    campaign_id      UUID NOT NULL,
    details          VARCHAR(255) NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS sales_activities_tasks_details
(
    tenant_id        UUID NOT NULL,
    id               UUID NOT NULL,
    activity_id      UUID NOT NULL,
    task_id          UUID NOT NULL,
    details          VARCHAR(255) NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";