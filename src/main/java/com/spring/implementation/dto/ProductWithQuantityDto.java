package com.spring.implementation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductWithQuantityDto {
    private Integer productId;
    private String productName;
    private Integer quantity;
    private float totalInventory;
}
