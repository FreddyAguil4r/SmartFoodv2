package com.spring.implementation.domain.service;

import com.spring.implementation.dto.MensualDto;
import com.spring.implementation.dto.PronosticoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BigQueryService {
    List<PronosticoDto> getQueryResult() throws Exception ;
    void callSP() throws Exception;
    List<PronosticoDto> getResultBimensual() throws Exception;
    void callSpBimensual() throws Exception;
    List<PronosticoDto> getQueryResultSorted() throws Exception;
    List<MensualDto> getQueryResultFormated() throws Exception;
}
