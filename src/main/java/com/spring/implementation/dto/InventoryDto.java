package com.spring.implementation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InventoryDto {

    private Date currentSystem;

    private float totalInventory;
}
