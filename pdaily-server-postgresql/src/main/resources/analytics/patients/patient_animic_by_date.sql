SELECT A.id as id, to_char(A.ocurrence_date, 'dd-MM-yyyy HH:mm:ss') as date, TP.intensity as intensity
FROM event_animic_events as A
         INNER JOIN cb_animic_types TP ON TP.id = A.type
WHERE A.patient_id = '{{patientId}}'
  AND A.ocurrence_date BETWEEN '{{startDate}}' AND '{{endDate}}'
ORDER BY date