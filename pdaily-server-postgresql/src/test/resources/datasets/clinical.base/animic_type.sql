INSERT INTO cb_animic_types(id, label, intensity)
VALUES ('022F4EB0-7C18-4EAE-80A8-C33C80E39BC7', 'EXISTENT', 10),
       ('96B7795A-958A-442C-81EF-1554089DF981', 'TO_UPDATE', 9),
       ('6941540D-5615-432B-96DA-DA71B528C882', 'TO_DELETE', 8),

       ('EA78846B-C7AF-4B7C-B4E9-09419C19FB7A', '¡Super bien! No hay sintomas', 9), -- verde
       ('32E7CCD6-F9C7-4BF3-B438-0CB3CD5EB7F6', '¡Muy bien! Casi no tengo sintomas', 8),
       ('8C01D50B-782F-40B2-A32C-D5CE18ABB1F2', '¡Bien! Algunos sintomas pero mejorando', 7),
       ('92989229-D349-4D3B-B52D-F3E4731061E4', 'Siento mejora leve en los sintomas', 6),
       ('65FEC440-5460-4B9B-9BD4-03D5EE4EA240', 'Neutral', 5),
       ('77179F06-CACB-4211-BB72-BDD3EA1B859C', 'Los sintomas estan empezando a empeorar', 4), -- amarillo
       ('6663F543-5735-468A-8464-2841A9741916', '¡Regular!', 3), -- rosado
       ('3431B67A-3CB2-4FDB-A5A2-BB73F450B7AD', '¡Mal!', 2),
       ('661D90AE-88B8-4305-BA60-A4BA54BBF757', '¡Muy mal!', 1) -- rojo

ON CONFLICT DO NOTHING;