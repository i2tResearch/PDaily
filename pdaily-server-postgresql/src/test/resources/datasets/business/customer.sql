INSERT INTO BS_SUBSIDIARIES(id, name, reference, tenant_id)
VALUES ('CED5EEC5-7771-402B-833D-792C411180BD', 'FOR_CUSTOMER', 'FOR_CUSTOMER', 'F8523E75-9070-4A60-991A-BF22A46F0866')
ON CONFLICT DO NOTHING;
INSERT INTO BS_CUSTOMERS(ID, SUBSIDIARY_ID, HOLDING_ID, REFERENCE, TAX_ID, MAIN_EMAIL, NAME, TENANT_ID, STATE, TYPE)
VALUES ('619E1AE0-400B-494F-8E27-30A1BA6B0828', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57',
        '425394D1-28F7-4358-A54C-CE25DF162A02', '1000000000', '999999991-0', 'info@customer.co', 'EXISTENT',
        'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('E82F51F2-AC18-4824-A4ED-1184098B3CCA', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000001',
        '99999999 1-1', 'info@customer2.co', 'TO_UPDATE', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('0CFEC823-5507-4D33-B3B6-9FCFD3B6EBC8', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000002',
        '999999991-2', 'info@customer3.co', 'TO_DELETE', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('D4FB76C7-0F2B-4A52-988A-866040E67EF1', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000003',
        '999999991-3', 'info@customer4.co', 'TO_DISABLE', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('557369AD-2146-4D2E-BD95-73FD32F1B96A', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000004',
        '999999991-4', 'info@customer5.co', 'TO_ENABLE', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'I', 'B'),
       ('D31B1E3A-5980-40E6-9833-2FAE6D9D9068', 'CED5EEC5-7771-402B-833D-792C411180BD', NULL, '1000000005',
        '999999991-5', 'info@customer6.co', 'FOR_SUBSIDIARY', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('88905949-3733-41DB-918C-047262D29B52', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000006',
        '999999991-6', 'info@customer7.co', 'BUYER', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('4DD576FE-F4DD-4E95-9274-0BA553D056B9', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000007',
        '999999991-7', 'info@customer8.co', 'SUPPLIER', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('E9E9B998-9006-4F6A-B563-E635B0526318', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000008',
        '999999991-9', 'info@customer9.co', 'BUYER_B', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('42ECA981-C3C3-4CF0-A943-1D351755A95B', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000009',
        '999999993-0', 'info@customer10.co', 'BUYER_C', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('75EA3E8C-AF05-4B1A-9936-676283E7A5FF', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000008',
        '999999991-8', 'info@customer9.co', 'CLIENT_MARKET_ATTRIBUTE_A', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('D3D980C3-0F4B-47E2-8F10-3678D873CDB2', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000008',
        '999999992-9', 'info@customer10.co', 'CLIENT_MARKET_ATTRIBUTE_B', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('26F09E26-4AFD-42BC-8BB8-CF46D02BB0B8', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000009',
        '999999993-1', 'info@customer11.co', 'CLIENT_MARKET_ATTRIBUTE_C', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('988A17AA-BD52-4525-9241-73B49D595703', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000010',
        '999999992-1', 'info@customer12.co', 'CLIENT_MARKET_ATTRIBUTE_D', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('5B4E12B2-7653-4CDB-BC08-5E008735012E', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000011',
        '999999992-2', 'info@customer13.co', 'CLIENT_MARKET_ATTRIBUTE_E', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('BD14CCBE-1D6C-42C7-9F0F-F6A2F1470A34', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000012',
        '999999992-3', 'info@customer14.co', 'CLIENT_MARKET_ATTRIBUTE_F', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('6BB91FBC-A3B8-423D-9338-3123DAC55446', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000013',
        '999999992-4', 'info@customer15.co', 'CLIENT_MARKET_ATTRIBUTE_G', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('03183E44-CE01-439B-91E2-6B65B15CCCAF', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000014',
        '999999992-5', 'info@customer16.co', 'CLIENT_MARKET_ATTRIBUTE_H', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('6523B7A3-0AA7-44B3-89F0-AFBA02CCDC04', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000015',
        '999999992-6', 'info@customer17.co', 'CLIENT_MARKET_ATTRIBUTE_I', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B'),
       ('2F726C02-6262-4B08-AA94-0F0874A1F5E3', 'AE37BE1D-13CC-483A-AE83-F6BF53BB8B57', NULL, '1000000016',
        '999999992-7', 'info@customer18.co', 'CLIENT_MARKET_ATTRIBUTE_J', 'F8523E75-9070-4A60-991A-BF22A46F0866', 'A', 'B')
        ON CONFLICT DO NOTHING;