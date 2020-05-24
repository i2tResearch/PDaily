CREATE VIEW analytics_market_subject_attributes AS
SELECT v.id,
       v.attribute_id,
       jsonb_typeof(v.value)                           as type,
       v.subject_id,
       REPLACE(CAST(v.value as VARCHAR(200)), '"', '') AS value
FROM market_attribute_value v
WHERE jsonb_typeof(v.value) <> 'array'
UNION ALL
SELECT v.id,
       v.attribute_id,
       'array' as type,
       v.subject_id,
       PL.list_value
FROM (SELECT v.id,
             v.attribute_id,
             v.subject_id,
             REPLACE(CAST(jsonb_array_elements(v.value) as VARCHAR(200)), '"', '') as value
      FROM market_attribute_value v
      WHERE jsonb_typeof(v.value) = 'array') V
         INNER JOIN market_attribute_picklist_details PL ON V.value::uuid = PL.id;

CREATE VIEW analytics_market_customer_attributes AS
SELECT MAV.subject_id, C.name as subject, MA.id attr_id, MAV.type as type, MA.label, MAV.value
FROM market_attributes MA
         INNER JOIN analytics_market_subject_attributes MAV ON MAV.attribute_id = MA.id
         INNER JOIN bs_customers C ON MAV.subject_id = C.id
ORDER BY MAV.subject_id;

CREATE VIEW analytics_market_contact_attributes AS
SELECT MAV.subject_id, C.name as subject, MA.id attr_id, MAV.type as type, MA.label, MAV.value
FROM market_attributes MA
         INNER JOIN analytics_market_subject_attributes MAV ON MAV.attribute_id = MA.id
         INNER JOIN bs_contacts C ON MAV.subject_id = C.id
ORDER BY MAV.subject_id;