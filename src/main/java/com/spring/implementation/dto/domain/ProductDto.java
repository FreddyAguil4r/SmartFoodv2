package com.spring.implementation.dto.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ProductDto {
    private String name;
    private CategoryDto category;
}
