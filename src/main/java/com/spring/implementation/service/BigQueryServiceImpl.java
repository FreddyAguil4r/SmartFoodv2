package com.spring.implementation.service;

import com.google.cloud.bigquery.*;
import com.spring.implementation.domain.model.Product;
import com.spring.implementation.domain.service.BigQueryService;
import com.spring.implementation.domain.service.ProductService;
import com.spring.implementation.dto.MensualDto;
import com.spring.implementation.dto.ObjectMensualDto;
import com.spring.implementation.dto.PronosticoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BigQueryServiceImpl implements BigQueryService {

    final String QUERY_GET_DATA =
            "SELECT * FROM `smartfood-421500.rp_mysqlsmartfooddb.resultados_demanda`";
    final String CALL_SP =
            "CALL rp_mysqlsmartfooddb.sp_resultados_demanda();";

    final String QUERY_BIMENSUAL =
            "SELECT * FROM `smartfood-421500.rp_mysqlsmartfooddb.resultados_estacional`";

    final String CALL_SP_BIMENSUAL =
            "CALL rp_mysqlsmartfooddb.sp_resultados_estacional();";

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

        TableResult result = queryJob.getQueryResults();

        List<Product> products = productService.getAllProducts();

        for (FieldValueList row : result.iterateAll()) {

            String anioMes = row.get("year_month").getStringValue();
            float cantidadAComprar = row.get("cantidad_a_comprar").getNumericValue().floatValue();
            int productId = row.get("product_id").getNumericValue().intValue();
            PronosticoDto pronosticoDto = new PronosticoDto();
            String anio = anioMes.substring(0, 4);
            String mes = anioMes.substring(5, 7);
            pronosticoDto.setAnio(anio);
            pronosticoDto.setMes(mes);
            pronosticoDto.setCantidadComprar((int) cantidadAComprar);
            pronosticoDto.setProductId(productId);

            Product product = products.stream().filter(p -> p.getId() == productId).findFirst().orElse(null);

            pronosticoDto.setProductName(product.getName());
            pronosticoDtoList.add(pronosticoDto);
        }
        return pronosticoDtoList;
    }

    @Override
    public List<PronosticoDto> getQueryResultSorted() throws Exception {

        List<PronosticoDto> queryResult = getQueryResult();

        queryResult.sort((p1, p2) -> p2.getCantidadComprar() - p1.getCantidadComprar());

        return queryResult;
    }

    @Override
    public List<MensualDto> getQueryResultFormated() throws Exception {

        List<PronosticoDto> queryResult = getQueryResult();
        List<MensualDto> mensualDtoList = new ArrayList<>();
        Set<String> meses = Set.of("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

        Map<String, String> mesesMap = new HashMap<>();
        mesesMap.put("01", "Enero");
        mesesMap.put("02", "Febrero");
        mesesMap.put("03", "Marzo");
        mesesMap.put("04", "Abril");
        mesesMap.put("05", "Mayo");
        mesesMap.put("06", "Junio");
        mesesMap.put("07", "Julio");
        mesesMap.put("08", "Agosto");
        mesesMap.put("09", "Septiembre");
        mesesMap.put("10", "Octubre");
        mesesMap.put("11", "Noviembre");
        mesesMap.put("12", "Diciembre");

        for (String mes : meses) {
            MensualDto mensualDto = new MensualDto();
            mensualDto.setMes(mesesMap.get(mes));
            mensualDto.setNumero(mes);
            List<ObjectMensualDto> objectMensualDtoList = new ArrayList<>();
            for (PronosticoDto pronosticoDto : queryResult) {
                if (pronosticoDto.getMes().equals(mes)) {
                    ObjectMensualDto objectMensualDto = new ObjectMensualDto();
                    objectMensualDto.setCantidadComprar(pronosticoDto.getCantidadComprar());
                    objectMensualDto.setProductName(pronosticoDto.getProductName());
                    objectMensualDto.setProductId(pronosticoDto.getProductId());
                    objectMensualDtoList.add(objectMensualDto);
                }
            }
            objectMensualDtoList.sort((o1, o2) -> o2.getCantidadComprar() - o1.getCantidadComprar());
            mensualDto.setData(objectMensualDtoList);
            mensualDtoList.add(mensualDto);
        }
        mensualDtoList.sort(Comparator.comparing(MensualDto::getNumero));
        return mensualDtoList;
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

    @Override
    public List<PronosticoDto> getResultBimensual() throws Exception {

        BigQuery bigQuery = BigQueryOptions.newBuilder()
                .setProjectId("smartfood-421500")
                .build()
                .getService();

        QueryJobConfiguration queryJobConfiguration =
                QueryJobConfiguration.newBuilder(QUERY_BIMENSUAL).build();

        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryJobConfiguration).build());

        queryJob = queryJob.waitFor();

        if (queryJob == null) {
            throw new Exception("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            throw new Exception(queryJob.getStatus().getError().toString());
        }

        List<PronosticoDto> pronosticoDtoList = new ArrayList<>();

        TableResult result = queryJob.getQueryResults();

        List<Product> products = productService.getAllProducts();

        for (FieldValueList row : result.iterateAll()) {

            String anioMes = row.get("forecast_month").getStringValue();
            float cantidadAComprar = row.get("forecast_value").getNumericValue().floatValue();
            int productId = row.get("product_id").getNumericValue().intValue();
            PronosticoDto pronosticoDto = new PronosticoDto();
            String anio = anioMes.substring(0, 4);
            String mes = anioMes.substring(5, 7);
            pronosticoDto.setAnio(anio);
            pronosticoDto.setMes(mes);
            pronosticoDto.setCantidadComprar((int) cantidadAComprar);
            pronosticoDto.setProductId(productId);

            Product product = products.stream().filter(p -> p.getId() == productId).findFirst().orElse(null);

            pronosticoDto.setProductName(product.getName());
            pronosticoDtoList.add(pronosticoDto);
        }

        pronosticoDtoList.sort((p1, p2) -> p2.getCantidadComprar() - p1.getCantidadComprar());

        return pronosticoDtoList;
    }

    @Override
    public void callSpBimensual() throws Exception {
        BigQuery bigQuery = BigQueryOptions.newBuilder()
                .setProjectId("smartfood-421500")
                .build()
                .getService();

        QueryJobConfiguration queryJobConfiguration =
                QueryJobConfiguration.newBuilder(CALL_SP_BIMENSUAL).build();

        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryJobConfiguration).build());

        queryJob = queryJob.waitFor();

        if (queryJob == null) {
            throw new Exception("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            throw new Exception(queryJob.getStatus().getError().toString());
        }
    }

}
