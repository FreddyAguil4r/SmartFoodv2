package com.spring.implementation.dto.domain;


import com.spring.implementation.dto.CategoryTotalDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TotalsWithCategoryDto {

    private int totalInventario;

    private float totalCantidad;

    private List<CategoryTotalDto> categories;

}
