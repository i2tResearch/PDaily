TRUNCATE TABLE programming_levodopa_schedules CASCADE;
INSERT INTO programming_levodopa_schedules(tenant_id, id, patient_id, medicine_id, schedule, pills_dose)
VALUES ('F8523E75-9070-4A60-991A-BF22A46F0866', '6765038A-701F-41D0-80D4-62B72FDDB231', 'DF20D5BD-F16A-48B0-9922-0D5E537DCB24', '4EDB9A68-FA61-4581-85B3-0670868E73F9', '20:00', 5),
       ('F8523E75-9070-4A60-991A-BF22A46F0866', '1BE2029A-F39F-49E8-9A67-4B2192CD5E55', 'DF20D5BD-F16A-48B0-9922-0D5E537DCB24', '4EDB9A68-FA61-4581-85B3-0670868E73F9', '21:00', 5),
       ('F8523E75-9070-4A60-991A-BF22A46F0866', 'FB5AA08B-4D29-467D-9592-FDF7A69F1921', 'DF20D5BD-F16A-48B0-9922-0D5E537DCB24', '4EDB9A68-FA61-4581-85B3-0670868E73F9', '22:00', 5)
ON CONFLICT DO NOTHING;