package com.spring.implementation.controller;

import com.spring.implementation.domain.service.BigQueryService;
import com.spring.implementation.dto.MensualDto;
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

        @GetMapping("/mensual")
        public  List<PronosticoDto> getQueryResult() throws Exception {
            return bigQueryService.getQueryResult();
        }
        @GetMapping("/callsp")
        public void callSP() throws Exception {
            bigQueryService.callSP();
        }
        @GetMapping("/bimensual")
        public List<PronosticoDto> getResultBimensual() throws Exception {
            return bigQueryService.getResultBimensual();
        }
        @GetMapping("/callspbimensual")
        public void callSpBimensual() throws Exception {
            bigQueryService.callSpBimensual();
        }
        @GetMapping("/sorted")
        public List<PronosticoDto> getQueryResultSorted() throws Exception {
            return bigQueryService.getQueryResultSorted();
        }
        @GetMapping("/formated")
        public List<MensualDto> getQueryResultFormated() throws Exception {
            return bigQueryService.getQueryResultFormated();
        }
}
