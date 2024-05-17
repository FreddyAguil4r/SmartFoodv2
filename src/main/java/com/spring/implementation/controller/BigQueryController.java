package com.spring.implementation.controller;

import com.spring.implementation.domain.service.BigQueryService;
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
        public void getQueryResult() throws Exception {
            bigQueryService.getQueryResult();
        }
}
