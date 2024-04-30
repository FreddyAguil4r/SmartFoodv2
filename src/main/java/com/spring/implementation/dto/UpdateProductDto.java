package com.spring.implementation.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UpdateProductDto {
    private String name;
    private float unitCost;
    private float amount;
}
