DELETE FROM SUBS_USERS;
INSERT INTO SUBS_USERS(ID, GIVEN_NAME, LAST_NAME, EMAIL, USERNAME, PASSWORD, ACCOUNT_ID, LICENSE_ID)
VALUES ('B667B7EE-1211-4A8B-BBC6-B98E7BA5CDA0', 'TO_UPDATE', 'TO_UPDATE', 'update@haruk.co', 'to_update', 'dmf8x90iB7K5jDCMwZFNxQ==',
        'F8523E75-9070-4A60-991A-BF22A46F0866', NULL),
       ('5D7BAD11-3721-4E56-B17F-CDF91B598FBD', 'EXISTENT', 'EXISTENT', 'existent@haruk.co', 'existent', 'dmf8x90iB7K5jDCMwZFNxQ==',
        'F8523E75-9070-4A60-991A-BF22A46F0866', NULL),
       ('CC2F3984-67D0-4A17-9E16-F053DCFBED79', 'TO_DELETE', 'TO_DELETE', 'delete@haruk.co', 'to_delete', 'dmf8x90iB7K5jDCMwZFNxQ==',
        'F8523E75-9070-4A60-991A-BF22A46F0866', NULL);