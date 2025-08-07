package com.walmarttech.inventory.service;

import com.walmarttech.inventory.domain.Almacen;
import com.walmarttech.inventory.domain.ComparisonResult;
import com.walmarttech.inventory.domain.Producto;
import com.walmarttech.inventory.exception.AlmacenNotFoundException;
import com.walmarttech.inventory.repository.AlmacenRepository;
import org.springframework.stereotype.Service;

@Service
public class AlmacenService {
    private final AlmacenRepository repository;
    private final ComparisonStrategy comparisonStrategy;

    public AlmacenService(AlmacenRepository repository, ComparisonStrategy comparisonStrategy) {
        this.repository = repository;
        this.comparisonStrategy = comparisonStrategy;
    }
    public void saveAlmacen(Almacen almacen) {
        repository.save(almacen);
    }

    public Almacen findAlmacenById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new AlmacenNotFoundException("Almacén con ID " + id + " no encontrado"));
    }

    public void agregarProducto(String almacenId, Producto producto) {
        Almacen almacen = findAlmacenById(almacenId);
        almacen.agregarProducto(producto);
        repository.save(almacen);
    }
    public ComparisonResult compararInventarios(String almacen1Id, String almacen2Id) {
        Almacen almacen1 = findAlmacenById(almacen1Id);
        Almacen almacen2 = findAlmacenById(almacen2Id);
        return comparisonStrategy.comparar(almacen1, almacen2);
    }
}
