package com.spring.implementation.controller;

import com.spring.implementation.domain.service.BigQueryService;
import com.spring.implementation.dto.PronosticoDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/bigquery")
@CrossOrigin(origins = "*")
public class BigQueryController {

        private BigQueryService bigQueryService;

        @GetMapping("/query")
        public  List<PronosticoDto> getQueryResult() throws Exception {
            return bigQueryService.getQueryResult();
        }

        @GetMapping("/callsp")
        public void callSP() throws Exception {
            bigQueryService.callSP();
        }

}
