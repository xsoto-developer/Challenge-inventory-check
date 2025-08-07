package com.walmarttech.inventory.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private String codigo;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
}
