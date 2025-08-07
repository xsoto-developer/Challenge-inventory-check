package com.walmarttech.inventory.repository;

import com.walmarttech.inventory.domain.Almacen;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryAlmacenRepository implements AlmacenRepository {
    private final Map<String, Almacen> almacenes = new HashMap<>();

    @Override
    public void save(Almacen almacen) {
        almacenes.put(almacen.getId(), almacen);
    }

    @Override
    public Optional<Almacen> findById(String id) {
        return Optional.ofNullable(almacenes.get(id));
    }
}
