package com.walmarttech.inventory.service;

import com.walmarttech.inventory.domain.Almacen;
import com.walmarttech.inventory.domain.ComparisonResult;
import com.walmarttech.inventory.domain.Producto;
import org.springframework.stereotype.Component;

@Component
public class DefaultComparisonStrategy implements ComparisonStrategy {
    @Override
    public ComparisonResult comparar(Almacen almacen1, Almacen almacen2) {
        ComparisonResult result = new ComparisonResult(almacen1.getNombre(), almacen2.getNombre());

        // Comparar productos de almacen1 con almacen2
        for (Producto p1 : almacen1.getProductos()) {
            boolean found = false;
            for (Producto p2 : almacen2.getProductos()) {
                if (p1.equals(p2)) {
                    found = true;
                    if (p1.getStock() != p2.getStock()) {
                        result.addDiferencia(String.format("Producto %s: stock %d en %s, stock %d en %s",
                                p1.getNombre(), p1.getStock(), almacen1.getNombre(),
                                p2.getStock(), almacen2.getNombre()));
                    }
                    break;
                }
            }
            if (!found) {
                result.addDiferencia(String.format("Producto %s: presente en %s, ausente en %s",
                        p1.getNombre(), almacen1.getNombre(), almacen2.getNombre()));
            }
        }

        // Comparar productos de almacen2 que no están en almacen1
        for (Producto p2 : almacen2.getProductos()) {
            boolean found = false;
            for (Producto p1 : almacen1.getProductos()) {
                if (p2.equals(p1)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.addDiferencia(String.format("Producto %s: presente en %s, ausente en %s",
                        p2.getNombre(), almacen2.getNombre(), almacen1.getNombre()));
            }
        }

        return result;
    }
}