INSERT INTO SUBS_LICENSES(ID, NAME)
VALUES ('BF6A83ED-9961-4DAD-A004-D230B252F4F4', 'TO_UPDATE'),
       ('5D409DAD-5C34-4C04-BB00-51B33C879B43', 'EXISTENT'),
       ('A6CC5EB3-F35A-404F-90D8-5B0FD045E036', 'PROFESIONAL'),
       ('7184CC62-D43A-4E2B-AAC9-C2A9D4C15C86', 'TO DELETE')
ON CONFLICT DO NOTHING;