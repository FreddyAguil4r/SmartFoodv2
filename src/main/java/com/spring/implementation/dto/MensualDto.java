package com.spring.implementation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MensualDto {
    private String mes;
    private String numero;
    private List<ObjectMensualDto> data;
}
