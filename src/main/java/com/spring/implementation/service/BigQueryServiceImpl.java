package com.spring.implementation.service;

import com.google.cloud.bigquery.*;
import com.spring.implementation.domain.model.Product;
import com.spring.implementation.domain.service.BigQueryService;
import com.spring.implementation.domain.service.ProductService;
import com.spring.implementation.dto.PronosticoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BigQueryServiceImpl implements BigQueryService {

    final String QUERY_GET_DATA =
            "SELECT * FROM `smartfood-421500.rp_mysqlsmartfooddb.resultados_demanda`";

    final String CALL_SP =
            "CALL rp_mysqlsmartfooddb.sp_resultados_demanda();";


    private ProductService productService;

    @Autowired
    public BigQueryServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<PronosticoDto> getQueryResult() throws Exception {

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

        List<PronosticoDto> pronosticoDtoList = new ArrayList<>();

//        System.out.println("Query results: ");
        TableResult result = queryJob.getQueryResults();

        List<Product> products = productService.getAllProducts();

        for (FieldValueList row : result.iterateAll()) {

            String anioMes = row.get("year_month").getStringValue();
            float cantidadAComprar = row.get("cantidad_a_comprar").getNumericValue().floatValue();
            int productId = row.get("product_id").getNumericValue().intValue();
//            System.out.println("anio_mes: " + anio_mes + " cantidad_a_comprar: " + cantidad_a_comprar + " product_id: " + product_id);
            PronosticoDto pronosticoDto = new PronosticoDto();
            String anio = anioMes.substring(0, 4);
            String mes = anioMes.substring(5, 7);
            pronosticoDto.setAnio(anio);
            pronosticoDto.setMes(mes);
            pronosticoDto.setCantidadComprar((int) cantidadAComprar);
            pronosticoDto.setProductId(productId);

            //find product name in products list

            Product product = products.stream().filter(p -> p.getId() == productId).findFirst().orElse(null);

            pronosticoDto.setProductName(product.getName());
            pronosticoDtoList.add(pronosticoDto);
        }
        return pronosticoDtoList;
    }

    @Override
    public void callSP() throws Exception {
        BigQuery bigQuery = BigQueryOptions.newBuilder()
                .setProjectId("smartfood-421500")
                .build()
                .getService();

        QueryJobConfiguration queryJobConfiguration =
                QueryJobConfiguration.newBuilder(CALL_SP).build();

        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryJobConfiguration).build());

        queryJob = queryJob.waitFor();

        if (queryJob == null) {
            throw new Exception("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            throw new Exception(queryJob.getStatus().getError().toString());
        }
    }

}
