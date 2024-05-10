package com.spring.implementation.dto.save;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveProductsPurchaseDto {

    private int amount;
    private float unitCost;
    private int productId;
    private int supplierId;
    private int unitId;

}
