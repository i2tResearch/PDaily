SELECT to_char(A.ACTIVITY_DATE, 'dd-MM-yyyy')   as date,
       C.NAME                                   AS buyer,
       SP.NAME                                  AS supplier,
       CONCAT(SR.LAST_NAME, ' ', SR.GIVEN_NAME) AS sales_rep,
       PUR.NAME                                 AS purpose,
       CAM.NAME                                 AS marketing_campaign,
       TAS.NAME                                 AS task
FROM BS_CUSTOMERS C
         INNER JOIN SALES_ACTIVITIES A ON A.BUYER_ID = C.ID
         INNER JOIN BS_CUSTOMERS SP ON SP.ID = A.SUPPLIER_ID
         INNER JOIN SECURITY_USERS SR ON SR.ID = A.SALES_REP_ID
         INNER JOIN BS_CUSTOMERS_BUSINESS_VIEW BS ON BS.CUSTOMER_ID = A.BUYER_ID
         INNER JOIN SALES_ACTIVITIES_PURPOSES PUR ON PUR.ID = A.PURPOSE_ID
         LEFT JOIN SALES_ACTIVITIES_CAMPAIGN_DETAILS CAMD ON CAMD.ACTIVITY_ID = A.ID
         LEFT JOIN MARKETING_CAMPAIGNS CAM ON CAM.ID = CAMD.CAMPAIGN_ID
         LEFT JOIN SALES_ACTIVITIES_TASKS_DETAILS TASD on TASD.ACTIVITY_ID = A.ID
         LEFT JOIN SALES_ACTIVITIES_TASKS TAS ON TAS.ID = TASD.TASK_ID
WHERE A.TENANT_ID = '{{tenant_id}}'
  AND ('{{doctorId}}' IS NULL OR A.SALES_REP_ID = '{{doctorId}}')
  AND ('{{businessId}}' IS NULL OR BS.BUSINESS_ID = '{{businessId}}')
  AND A.ACTIVITY_DATE BETWEEN '{{startDate}}' AND '{{endDate}}'
ORDER BY date ASC
