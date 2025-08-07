package com.walmarttech.inventory.service;

import com.walmarttech.inventory.domain.Almacen;
import com.walmarttech.inventory.domain.ComparisonResult;
import com.walmarttech.inventory.domain.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultComparisonStrategyTest {
    private DefaultComparisonStrategy strategy;
    private Almacen almacen1;
    private Almacen almacen2;

    @BeforeEach
    void setUp() {
        strategy = new DefaultComparisonStrategy();
        almacen1 = new Almacen("1", "Almacén Norte");
        almacen2 = new Almacen("2", "Almacén Sur");
    }

    @Test
    void testMismoProductoDiferenteStock() {
        Producto p1 = new Producto("001", "Camiseta", "Ropa", 25.0, 50);
        Producto p2 = new Producto("001", "Camiseta", "Ropa", 25.0, 30);
        almacen1.agregarProducto(p1);
        almacen2.agregarProducto(p2);

        ComparisonResult result = strategy.comparar(almacen1, almacen2);
        assertEquals(1, result.getDiferencias().size());
        assertTrue(result.getDiferencias().contains(
                "Producto Camiseta: stock 50 en Almacén Norte, stock 30 en Almacén Sur"));
    }

    @Test
    void testProductoSoloEnUnAlmacen() {
        Producto p1 = new Producto("001", "Camiseta", "Ropa", 25.0, 50);
        almacen1.agregarProducto(p1);

        ComparisonResult result = strategy.comparar(almacen1, almacen2);
        assertEquals(1, result.getDiferencias().size());
        assertTrue(result.getDiferencias().contains(
                "Producto Camiseta: presente en Almacén Norte, ausente en Almacén Sur"));
    }

    @Test
    void testAlmacenesVacios() {
        ComparisonResult result = strategy.comparar(almacen1, almacen2);
        assertEquals(0, result.getDiferencias().size());
    }

    @Test
    void testProductoConStockCero() {
        Producto p1 = new Producto("001", "Camiseta", "Ropa", 25.0, 0);
        Producto p2 = new Producto("001", "Camiseta", "Ropa", 25.0, 30);
        almacen1.agregarProducto(p1);
        almacen2.agregarProducto(p2);

        ComparisonResult result = strategy.comparar(almacen1, almacen2);
        assertEquals(1, result.getDiferencias().size());
        assertTrue(result.getDiferencias().contains(
                "Producto Camiseta: stock 0 en Almacén Norte, stock 30 en Almacén Sur"));
    }
}
