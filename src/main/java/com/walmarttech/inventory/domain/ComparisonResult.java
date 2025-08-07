package com.walmarttech.inventory.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ComparisonResult {
    private String almacen1Nombre;
    private String almacen2Nombre;
    private List<String> diferencias;

    public ComparisonResult(String almacen1Nombre, String almacen2Nombre) {
        this.almacen1Nombre = almacen1Nombre;
        this.almacen2Nombre = almacen2Nombre;
        this.diferencias = new ArrayList<>();
    }

    public void addDiferencia(String diferencia){
        diferencias.add(diferencia);
    }
}
