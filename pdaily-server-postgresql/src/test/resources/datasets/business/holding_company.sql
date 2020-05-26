INSERT INTO BS_SUBSIDIARIES(id, name, reference, tenant_id)
VALUES ('D425804C-5144-49E9-B934-99D5A7DBE441', 'FOR_HOLDING', 'FOR_HOLDING', 'F8523E75-9070-4A60-991A-BF22A46F0866')
ON CONFLICT DO NOTHING;
INSERT INTO BS_CUSTOMER_HOLDING_COMPANIES(id, subsidiary_id, name, tenant_id)
VALUES ('425394D1-28F7-4358-A54C-CE25DF162A02', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', 'EXISTENT',
        'f8523e75-9070-4a60-991a-bf22a46f0866'),
       ('E6891E43-522F-41E7-A385-15D124DA3250', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', 'TO_DELETE',
        'f8523e75-9070-4a60-991a-bf22a46f0866'),
       ('68345FAB-68AB-4222-9E61-0F7ECE44407E', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', 'TO_UPDATE',
        'f8523e75-9070-4a60-991a-bf22a46f0866'),
       ('2C58F15F-22D3-4EA6-AEB9-1EBC72BDB5B9', 'D425804C-5144-49E9-B934-99D5A7DBE441', 'FOR_SUBSIDIARY',
        'f8523e75-9070-4a60-991a-bf22a46f0866')
ON CONFLICT DO NOTHING;