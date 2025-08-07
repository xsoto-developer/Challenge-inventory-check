package com.walmarttech.inventory.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
    public class Almacen {
        private String id;
        private String nombre;
        private List<Producto> productos;

        public Almacen(String id, String nombre) {
            this.id = id;
            this.nombre = nombre;
            this.productos = new ArrayList<>();
        }

        public void agregarProducto(Producto producto) { productos.add(producto); }

        @Override
        public String toString() {
            return String.format("Almacen{id='%s', nombre='%s', productos=%s}", id, nombre, productos);
        }
}
