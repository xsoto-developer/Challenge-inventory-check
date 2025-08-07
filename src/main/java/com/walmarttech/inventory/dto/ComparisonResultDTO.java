package com.walmarttech.inventory.dto;

import lombok.Data;

import java.util.List;

@Data
public class ComparisonResultDTO {
    private String almacen1Nombre;
    private String almacen2Nombre;
    private List<String> diferencias;
}
