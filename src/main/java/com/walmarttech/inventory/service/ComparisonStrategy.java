package com.walmarttech.inventory.service;

import com.walmarttech.inventory.domain.Almacen;
import com.walmarttech.inventory.domain.ComparisonResult;

public interface ComparisonStrategy {
    ComparisonResult comparar (Almacen almacen1, Almacen almacen2 );
}
