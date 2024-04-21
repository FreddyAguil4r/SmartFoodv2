package com.spring.implementation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ShortProductDto {

    private String name;

    private Date datePurchase;

    private Date dueDate;

    private float unitCost;

    private float amount;

    private float warehouseValue;

}
