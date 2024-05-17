package com.spring.implementation.domain.service;

import com.spring.implementation.dto.PronosticoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BigQueryService {
    List<PronosticoDto> getQueryResult() throws Exception ;
    void callSP() throws Exception;
}
