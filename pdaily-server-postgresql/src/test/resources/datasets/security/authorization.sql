INSERT INTO security_auth_groups(ID, NAME)
VALUES ('existent', 'TEST existent Auth group'),
       ('to_remove', 'TEST Auth group for removal'),
       ('to_update', 'TEST to update Auth group')
ON CONFLICT DO NOTHING;
INSERT INTO security_auth_activities(ID, GROUP_ID, DESCRIPTION)
VALUES ('existent', 'existent', 'TEST Existent activity'),
       ('for_permission', 'existent', 'TEST Activity for permission'),
       ('for_permission_delete', 'existent', 'TEST Activity for permission delete'),
       ('activity_to_update_group', 'to_update', 'TEST Activity for group update'),
       ('activity_to_remove_group', 'to_remove', 'TEST Activity for group removal'),
       ('existent2', 'existent', 'TEST Existent activity 2')
ON CONFLICT DO NOTHING;
TRUNCATE TABLE security_auth_user_permissions CASCADE;
INSERT INTO security_auth_user_permissions(user_id, tenant_id, activity_id, granted_on, granted_by)
VALUES ('5d7bad11-3721-4e56-b17f-cdf91b598fbd', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'for_permission',
        '2020-05-03T02:49:23', 'test_user'),
       ('5d7bad11-3721-4e56-b17f-cdf91b598fbd', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'for_permission_delete',
        '2020-05-03T02:49:23', 'test_user')
ON CONFLICT DO NOTHING;