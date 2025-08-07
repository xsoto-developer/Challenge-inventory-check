package com.walmarttech.inventory.repository;

import com.walmarttech.inventory.domain.Almacen;
import java.util.Optional;

public interface AlmacenRepository {
    void save(Almacen almacen);
    Optional<Almacen> findById(String id);
}