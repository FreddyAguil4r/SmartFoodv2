package com.spring.implementation.dto.domain;


import com.spring.implementation.dto.CategoryTotalDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TotalsWithCategoryDto {

    private float totalInventario;

    private int totalCantidad;

    private List<CategoryTotalDto> categories;

}
