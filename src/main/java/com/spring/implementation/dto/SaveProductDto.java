package com.spring.implementation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SaveProductDto {

    private String name;

    //private Date datePurchase; se crea cuando se guarda un producto

    private Date dueDate; //el usuario lo ingresa

    private float unitCost;

    private float amount;

    private int categoryId;

    private int supplierId;

}
