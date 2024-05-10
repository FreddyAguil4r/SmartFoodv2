package com.spring.implementation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryWithProductQttyDto {
    private String categoryName;
    private List<ProductWithQuantityDto> products;
}
