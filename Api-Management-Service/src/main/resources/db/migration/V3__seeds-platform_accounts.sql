INSERT INTO management_users (
    organization_id, role_id, username, password_hash, enabled, created_at, updated_at
)
SELECT NULL, roles.id, 'platform-admin',
       '$2a$10$3DvkiCqg7.qWdZzqb.Weg.c9mz8y3W8ltC1wB0DM2EQKtAnhJplyi',
       TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM roles WHERE roles.name = 'PLATFORM_ADMIN';


INSERT INTO management_users (
    organization_id, role_id, username, password_hash, enabled, created_at, updated_at
)
SELECT NULL, roles.id, 'gateway-service',
       '$2a$10$enWkS6HQIVrg6qENT6CVcezqF58gYIj/dIbrPV07i39QqWDWa3B.C',
       TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
FROM roles WHERE roles.name = 'GATEWAY_SERVICE';