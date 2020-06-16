SELECT B.id as id, to_char(B.date, 'dd-MM-yyyy HH:mm:ss') as date, 'Comidas' as eventType
FROM event_food_events as B
WHERE B.patient_id = '{{patientId}}'
  AND B.date BETWEEN '{{startDate}}' AND '{{endDate}}'
UNION ALL
SELECT C.id as id, to_char(C.date, 'dd-MM-yyyy HH:mm:ss') as date, 'Levodopa' as eventType
FROM event_levodopa_events as C
WHERE C.patient_id = '{{patientId}}'
  AND C.date BETWEEN '{{startDate}}' AND '{{endDate}}'
UNION ALL
SELECT D.id as id, to_char(D.initial_date, 'dd-MM-yyyy HH:mm:ss') as date, 'Eventos' as eventType
FROM event_physical_events as D
WHERE D.patient_id = '{{patientId}}'
  AND D.initial_date BETWEEN '{{startDate}}' AND '{{endDate}}'
ORDER BY date;