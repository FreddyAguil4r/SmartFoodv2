package com.spring.implementation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductWithQuantityDto {
    private String productName;
    private Integer quantity;
}
