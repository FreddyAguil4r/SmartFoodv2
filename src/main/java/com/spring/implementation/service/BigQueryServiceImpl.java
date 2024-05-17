package com.spring.implementation.service;

import com.google.cloud.bigquery.*;
import com.spring.implementation.domain.service.BigQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BigQueryServiceImpl implements BigQueryService {

    final String QUERY_GET_DATA =
            "SELECT * FROM `smartfood-421500.rp_mysqlsmartfooddb.resultados_demanda`";
    @Autowired
    public BigQueryServiceImpl() {
    }

    @Override
    public void getQueryResult() throws Exception {

        BigQuery bigQuery = BigQueryOptions.newBuilder()
                .setProjectId("smartfood-421500")
                .build()
                .getService();

        QueryJobConfiguration queryJobConfiguration =
                QueryJobConfiguration.newBuilder(QUERY_GET_DATA).build();

        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryJobConfiguration).build());

        queryJob = queryJob.waitFor();

        if (queryJob == null) {
            throw new Exception("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            throw new Exception(queryJob.getStatus().getError().toString());
        }

        System.out.println("Query results: ");
        TableResult result = queryJob.getQueryResults();
        for (FieldValueList row : result.iterateAll()) {
            String anio_mes = row.get("anio_mes").getStringValue();
            Float cantidad_a_comprar = row.get("cantidad_a_comprar").getNumericValue().floatValue();
            int product_id = row.get("product_id").getNumericValue().intValue();
            System.out.println("anio_mes: " + anio_mes + " cantidad_a_comprar: " + cantidad_a_comprar + " product_id: " + product_id);
        }
    }
}
