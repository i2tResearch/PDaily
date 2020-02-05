CREATE TABLE IF NOT EXISTS bs_business_units
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL,
    name      VARCHAR(100) NOT NULL,
    reference VARCHAR(30)  NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";

CREATE TABLE IF NOT EXISTS bs_product_lines
(
    tenant_id   UUID         NOT NULL,
    id          UUID         NOT NULL,
    business_id UUID         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    reference   VARCHAR(30)  NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_product_brands
(
    tenant_id   UUID         NOT NULL,
    id          UUID         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    business_id UUID         NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_product_groups
(
    tenant_id   UUID         NOT NULL,
    id          UUID         NOT NULL,
    business_id UUID         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    reference   VARCHAR(30)  NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_products
(
    id          UUID         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    reference   VARCHAR(30)  NULL,
    business_id UUID         NOT NULL,
    brand_id    UUID         NULL,
    line_id     UUID         NULL,
    group_id    UUID         NULL,
    tenant_id   UUID         NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_subsidiaries
(
    tenant_id UUID         NOT NULL,
    id        UUID         NOT NULL,
    name      VARCHAR(100) NOT NULL,
    reference VARCHAR(30)  NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_sales_offices
(
    tenant_id     UUID         NOT NULL,
    id            UUID         NOT NULL,
    name          VARCHAR(100) NOT NULL,
    reference     VARCHAR(30)  NULL,
    subsidiary_id UUID         NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_zones
(
    tenant_id        UUID         NOT NULL,
    id               UUID         NOT NULL,
    name             VARCHAR(100) NOT NULL,
    reference        VARCHAR(30)  NULL,
    business_unit_id UUID         NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_sales_reps
(
    tenant_id     UUID        NOT NULL,
    id            UUID        NOT NULL,
    reference     VARCHAR(30) NULL,
    subsidiary_id UUID        NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_customer_holding_companies
(
    tenant_id     UUID         NOT NULL,
    id            UUID         NOT NULL,
    subsidiary_id UUID         NOT NULL,
    name          VARCHAR(100) NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_geo_countries
(
    id   UUID         NOT NULL PRIMARY KEY,
    code VARCHAR(2)   NOT NULL,
    name VARCHAR(100) NOT NULL,
) WITH "template=partitioned, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_geo_states
(
    id         UUID         NOT NULL,
    country_id UUID         NOT NULL,
    reference  VARCHAR(30)  NULL,
    name       VARCHAR(100) NOT NULL,
    PRIMARY KEY (country_id, id)
) WITH "template=partitioned, affinity_key=country_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_geo_cities
(
    id        UUID         NOT NULL,
    state_id  UUID         NOT NULL,
    reference VARCHAR(30)  NULL,
    name      VARCHAR(100) NOT NULL,
    PRIMARY KEY (state_id, id)
) WITH "template=partitioned, affinity_key=state_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_addresses
(
    tenant_id     UUID         NOT NULL,
    id            UUID         NOT NULL,
    referenced_id UUID         NOT NULL,
    description   VARCHAR(100) NULL,
    is_main       BOOLEAN,
    street_line   VARCHAR(100) NOT NULL,
    street_line2  VARCHAR(100) NULL,
    city_id       UUID         NOT NULL,
    latitude      REAL,
    longitude     REAL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_customers
(
    tenant_id     UUID         NOT NULL,
    id            UUID         NOT NULL,
    subsidiary_id UUID         NOT NULL,
    holding_id    UUID         NULL,
    tax_id        VARCHAR(25)  NULL,
    reference     VARCHAR(30)  NULL,
    main_email    VARCHAR(100) NULL,
    name          VARCHAR(100) NOT NULL,
    state         CHAR(1)      NOT NULL,
    type          CHAR(1)      NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_customers_business_view
(
    tenant_id        UUID NOT NULL,
    customer_id      UUID NOT NULL,
    business_unit_id UUID NOT NULL,
    sales_rep_id     UUID NOT NULL,
    zone_id          UUID NULL,
    PRIMARY KEY (tenant_id, customer_id, business_unit_id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_contacts
(
    tenant_id       UUID         NOT NULL,
    id              UUID         NOT NULL,
    name            VARCHAR(100) NOT NULL,
    email           VARCHAR(100) NOT NULL,
    landline_phone  VARCHAR(10)  NULL,
    mobile_phone    VARCHAR(10)  NULL,
    role_id         UUID         NULL,
    customer_id     UUID         NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
CREATE TABLE IF NOT EXISTS bs_contact_roles
(
    tenant_id   UUID         NOT NULL,
    id          UUID         NOT NULL,
    name        VARCHAR(100) NOT NULL,
    PRIMARY KEY (tenant_id, id)
) WITH "template=partitioned, affinity_key=tenant_id, atomicity=TRANSACTIONAL_SNAPSHOT";
