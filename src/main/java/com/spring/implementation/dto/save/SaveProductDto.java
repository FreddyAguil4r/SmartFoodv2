package com.spring.implementation.dto.save;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SaveProductDto {
    private String name;
    private int categoryId;
    private int unitId;
}
