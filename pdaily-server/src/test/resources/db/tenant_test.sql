CREATE TABlE IF NOT EXISTS test_tenant_entity
(
    id   UUID PRIMARY KEY NOT NULL,
    name VARCHAR(200),
    tenant_id UUID NOT NULL
)WITH "atomicity=TRANSACTIONAL_SNAPSHOT";