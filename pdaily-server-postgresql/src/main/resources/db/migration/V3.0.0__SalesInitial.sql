CREATE TABLE IF NOT EXISTS sales_order_sources
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS sales_activities_tasks
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS sales_activities_purposes
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS marketing_campaigns
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS sales_activities
(
    tenant_id     UUID         NOT NULL,
    id            UUID         NOT NULL PRIMARY KEY,
    sales_rep_id  UUID         NOT NULL,
    buyer_id      UUID         NOT NULL,
    supplier_id   UUID         NULL,
    purpose_id    UUID         NULL,
    activity_date DATE         NOT NULL,
    creation_date TIMESTAMP    NOT NULL,
    latitude      REAL,
    longitude     REAL,
    is_effective  BOOLEAN,
    comments      VARCHAR(255) NULL,
    FOREIGN KEY (sales_rep_id) REFERENCES bs_sales_reps (id),
    FOREIGN KEY (buyer_id) REFERENCES bs_customers (id),
    FOREIGN KEY (supplier_id) REFERENCES bs_customers (id),
    FOREIGN KEY (purpose_id) REFERENCES sales_activities_purposes (id)
);

CREATE TABLE IF NOT EXISTS sales_activities_campaign_details
(
    tenant_id   UUID         NOT NULL,
    id          UUID         NOT NULL PRIMARY KEY,
    activity_id UUID         NOT NULL,
    campaign_id UUID         NOT NULL,
    details     VARCHAR(255) NULL,
    FOREIGN KEY (activity_id) REFERENCES sales_activities (id),
    FOREIGN KEY (campaign_id) REFERENCES marketing_campaigns (id)
);

CREATE TABLE IF NOT EXISTS sales_activities_tasks_details
(
    tenant_id   UUID         NOT NULL,
    id          UUID         NOT NULL,
    activity_id UUID         NOT NULL,
    task_id     UUID         NOT NULL,
    details     VARCHAR(255) NULL,
    PRIMARY KEY (tenant_id, id),
    FOREIGN KEY (activity_id) REFERENCES sales_activities (id),
    FOREIGN KEY (task_id) REFERENCES sales_activities_tasks (id)
);

CREATE TABLE IF NOT EXISTS sales_orders
(
    tenant_id     UUID         NOT NULL,
    id            UUID         NOT NULL PRIMARY KEY,
    source_id     UUID         NOT NULL,
    sales_rep_id  UUID         NOT NULL,
    buyer_id      UUID         NOT NULL,
    order_date    DATE         NOT NULL,
    creation_date TIMESTAMP    NOT NULL,
    comments      VARCHAR(255) NULL,
    FOREIGN KEY (source_id) REFERENCES sales_order_sources (id),
    FOREIGN KEY (sales_rep_id) REFERENCES bs_sales_reps (id),
    FOREIGN KEY (buyer_id) REFERENCES bs_customers (id)
);

CREATE TABLE IF NOT EXISTS sales_order_details
(
    tenant_id             UUID    NOT NULL,
    sales_order_id        UUID    NOT NULL,
    id                    UUID    NOT NULL PRIMARY KEY,
    supplier_id           UUID    NULL,
    supplier_sales_rep_id UUID    NULL,
    product_id            UUID    NOT NULL,
    quantity              REAL    NOT NULL,
    is_bonification       BOOLEAN NOT NULL,
    FOREIGN KEY (sales_order_id) REFERENCES sales_orders (id),
    FOREIGN KEY (product_id) REFERENCES bs_products (id),
    FOREIGN KEY (supplier_id) REFERENCES bs_customers (id),
    FOREIGN KEY (supplier_sales_rep_id) REFERENCES bs_contacts (id)
);
