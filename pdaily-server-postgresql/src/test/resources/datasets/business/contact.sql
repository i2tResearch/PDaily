INSERT INTO BS_CONTACTS(ID, NAME, EMAIL, LANDLINE_PHONE, MOBILE_PHONE, ROLE_ID, TENANT_ID)
VALUES ('430F5FE2-1807-4EB8-836D-213C6FA24684', 'EXISTENT', 'info@contact.co', '4445566', '3213453434',
        'D6D9FC09-5D06-4125-AD0A-570539192AFA',
        'F8523E75-9070-4A60-991A-BF22A46F0866'),
       ('4A93DC7B-A556-4AC8-8DDD-C76F57C6292B', 'TO_UPDATE', 'info@contact2.co', '4445567', '3213453435',
        'D6D9FC09-5D06-4125-AD0A-570539192AFA',
        'F8523E75-9070-4A60-991A-BF22A46F0866'),
       ('F4F7DFBF-5193-4B83-9826-2D5E7FF99536', 'TO_DELETE', 'info@contact3.co', '4445568', '3213453436',
        'D6D9FC09-5D06-4125-AD0A-570539192AFA',
        'F8523E75-9070-4A60-991A-BF22A46F0866'),
       ('720B3580-74B6-42B6-910F-EF7BBCA9F539', 'TO_SALES_ORDER', 'info@contact4.co', '4445567', '3213453435',
        'D6D9FC09-5D06-4125-AD0A-570539192AFA',
        'F8523E75-9070-4A60-991A-BF22A46F0866'),
       ('DA767BCB-CB38-4E22-A53E-885BFF4F7960', 'TO_SALES_CONTACT', 'info@contact5.co', '4445567', '3213453435',
        'D6D9FC09-5D06-4125-AD0A-570539192AFA',
        'F8523E75-9070-4A60-991A-BF22A46F0866'),
       ('96608184-E4EE-498F-862B-86A92A8B5B4D', 'CONTACT_MARKET_ATTRIBUTE', 'info@contact6.co', '4445567', '3213453435',
        'D6D9FC09-5D06-4125-AD0A-570539192AFA',
        'F8523E75-9070-4A60-991A-BF22A46F0866')
ON CONFLICT DO NOTHING;

