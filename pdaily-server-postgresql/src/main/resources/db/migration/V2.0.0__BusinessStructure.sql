CREATE TABLE IF NOT EXISTS bs_business_units
(
    tenant_id                    UUID         NOT NULL,
    id                           UUID         NOT NULL PRIMARY KEY,
    name                         VARCHAR(100) NOT NULL,
    reference                    VARCHAR(30)  NULL,
    effective_activity_threshold INT
);

CREATE TABLE IF NOT EXISTS bs_product_lines
(
    tenant_id   UUID         NOT NULL,
    id          UUID         NOT NULL PRIMARY KEY,
    business_id UUID         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    reference   VARCHAR(30)  NULL,
    FOREIGN KEY (business_id) REFERENCES bs_business_units (id)
);
CREATE TABLE IF NOT EXISTS bs_product_brands
(
    tenant_id   UUID         NOT NULL,
    id          UUID         NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    business_id UUID         NOT NULL,
    FOREIGN KEY (business_id) REFERENCES bs_business_units (id)
);
CREATE TABLE IF NOT EXISTS bs_product_groups
(
    tenant_id   UUID         NOT NULL,
    id          UUID         NOT NULL PRIMARY KEY,
    business_id UUID         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    reference   VARCHAR(30)  NULL,
    FOREIGN KEY (business_id) REFERENCES bs_business_units (id)
);
CREATE TABLE IF NOT EXISTS bs_products
(
    tenant_id   UUID         NOT NULL,
    id          UUID         NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    reference   VARCHAR(30)  NULL,
    business_id UUID         NOT NULL,
    brand_id    UUID         NULL,
    line_id     UUID         NULL,
    group_id    UUID         NULL,
    FOREIGN KEY (business_id) REFERENCES bs_business_units (id),
    FOREIGN KEY (brand_id) REFERENCES bs_product_brands (id),
    FOREIGN KEY (line_id) REFERENCES bs_product_lines (id),
    FOREIGN KEY (group_id) REFERENCES bs_product_groups (id)
);
CREATE TABLE IF NOT EXISTS bs_subsidiaries
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    reference VARCHAR(30)  NULL
);
CREATE TABLE IF NOT EXISTS bs_sales_offices
(
    tenant_id     UUID         NOT NULL,
    id            UUID         NOT NULL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    reference     VARCHAR(30)  NULL,
    subsidiary_id UUID         NOT NULL,
    FOREIGN KEY (subsidiary_id) REFERENCES bs_subsidiaries (id)
);
CREATE TABLE IF NOT EXISTS bs_zones
(
    tenant_id   UUID         NOT NULL,
    id          UUID         NOT NULL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    reference   VARCHAR(30)  NULL,
    business_id UUID         NOT NULL,
    FOREIGN KEY (business_id) REFERENCES bs_business_units (id)
);
CREATE TABLE IF NOT EXISTS bs_sales_reps
(
    tenant_id     UUID        NOT NULL,
    id            UUID        NOT NULL PRIMARY KEY,
    reference     VARCHAR(30) NULL,
    subsidiary_id UUID        NOT NULL,
    FOREIGN KEY (subsidiary_id) REFERENCES bs_subsidiaries (id)
);
CREATE TABLE IF NOT EXISTS bs_customer_holding_companies
(
    tenant_id     UUID         NOT NULL,
    id            UUID         NOT NULL PRIMARY KEY,
    subsidiary_id UUID         NOT NULL,
    name          VARCHAR(100) NOT NULL,
    FOREIGN KEY (subsidiary_id) REFERENCES bs_subsidiaries (id)
);
CREATE TABLE IF NOT EXISTS bs_geo_countries
(
    id   UUID         NOT NULL PRIMARY KEY,
    code VARCHAR(2)   NOT NULL,
    name VARCHAR(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS bs_geo_states
(
    id         UUID         NOT NULL PRIMARY KEY,
    country_id UUID         NOT NULL,
    reference  VARCHAR(30)  NULL,
    name       VARCHAR(100) NOT NULL,
    FOREIGN KEY (country_id) REFERENCES bs_geo_countries (id)
);
CREATE TABLE IF NOT EXISTS bs_geo_cities
(
    id        UUID         NOT NULL PRIMARY KEY,
    state_id  UUID         NOT NULL,
    reference VARCHAR(30)  NULL,
    name      VARCHAR(100) NOT NULL,
    FOREIGN KEY (state_id) REFERENCES bs_geo_states (id)
);
CREATE TABLE IF NOT EXISTS bs_addresses
(
    tenant_id     UUID         NOT NULL,
    id            UUID         NOT NULL PRIMARY KEY,
    referenced_id UUID         NOT NULL,
    description   VARCHAR(100) NULL,
    is_main       BOOLEAN,
    street_line   VARCHAR(100) NOT NULL,
    street_line2  VARCHAR(255) NULL,
    city_id       UUID         NOT NULL,
    latitude      REAL,
    longitude     REAL
);
CREATE TABLE IF NOT EXISTS bs_customers
(
    tenant_id     UUID         NOT NULL,
    id            UUID         NOT NULL PRIMARY KEY,
    subsidiary_id UUID         NOT NULL,
    holding_id    UUID         NULL,
    tax_id        VARCHAR(25)  NULL UNIQUE,
    reference     VARCHAR(30)  NULL,
    main_email    VARCHAR(100) NULL,
    name          VARCHAR(100) NOT NULL,
    state         CHAR(1)      NOT NULL,
    type          CHAR(1)      NOT NULL,
    FOREIGN KEY (subsidiary_id) REFERENCES bs_subsidiaries (id),
    FOREIGN KEY (holding_id) REFERENCES bs_customer_holding_companies (id)
);
CREATE TABLE IF NOT EXISTS bs_customers_business_view
(
    tenant_id    UUID NOT NULL,
    customer_id  UUID NOT NULL,
    business_id  UUID NOT NULL,
    sales_rep_id UUID NOT NULL,
    zone_id      UUID NULL,
    PRIMARY KEY (customer_id, business_id),
    FOREIGN KEY (customer_id) REFERENCES bs_customers (id),
    FOREIGN KEY (business_id) REFERENCES bs_business_units (id),
    FOREIGN KEY (sales_rep_id) REFERENCES bs_sales_reps (id),
    FOREIGN KEY (zone_id) REFERENCES bs_zones (id)
);
CREATE TABLE IF NOT EXISTS bs_contact_roles
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL PRIMARY KEY,
    name      VARCHAR(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS bs_contacts
(
    tenant_id      UUID         NOT NULL,
    customer_id    UUID         NOT NULL,
    id             UUID         NOT NULL PRIMARY KEY,
    role_id        UUID         NULL,
    name           VARCHAR(100) NOT NULL,
    email          VARCHAR(100) NOT NULL,
    landline_phone VARCHAR(10)  NULL,
    mobile_phone   VARCHAR(10)  NULL,
    sales_contact  BOOLEAN,
    FOREIGN KEY (customer_id) REFERENCES bs_customers (id),
    FOREIGN KEY (role_id) REFERENCES bs_contact_roles (id)
);

CREATE TABLE IF NOT EXISTS bs_business_units_sellers
(
    sales_rep_id UUID NOT NULL,
    business_id  UUID NOT NULL,
    FOREIGN KEY (sales_rep_id) REFERENCES bs_sales_reps (id),
    FOREIGN KEY (business_id) REFERENCES bs_business_units (id)
);