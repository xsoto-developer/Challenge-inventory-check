package com.walmarttech.inventory.controller;

import com.walmarttech.inventory.domain.Almacen;
import com.walmarttech.inventory.domain.ComparisonResult;
import com.walmarttech.inventory.domain.Producto;
import com.walmarttech.inventory.dto.AlmacenDTO;
import com.walmarttech.inventory.dto.ComparisonResultDTO;
import com.walmarttech.inventory.dto.ProductoDTO;
import com.walmarttech.inventory.service.AlmacenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/almacenes")
public class AlmacenController {

    private final AlmacenService service;

    public AlmacenController(AlmacenService service) {
        this.service = service;
    }

    @Operation(summary = "Agrega un almacén",
            description = "Añade un nuevo almacén específico identificado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto agregado exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Almacén no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos del producto inválidos",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> agregarAmacen(@RequestBody AlmacenDTO almacenDTO) {
        Almacen almacen = new Almacen(
                almacenDTO.getId(),
                almacenDTO.getNombre()
        );
        service.saveAlmacen(almacen);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Lista los productos de un almacén",
            description = "Obtiene la lista de productos asociados a un almacén específico identificado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Almacén no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}/productos")
    public ResponseEntity<List<ProductoDTO>> listarProductos(@PathVariable String id) {
        Almacen almacen = service.findAlmacenById(id);
        List<ProductoDTO> productos = almacen.getProductos().stream()
                .map(p -> {
                    ProductoDTO dto = new ProductoDTO();
                    dto.setCodigo(p.getCodigo());
                    dto.setNombre(p.getNombre());
                    dto.setCategoria(p.getCategoria());
                    dto.setPrecio(p.getPrecio());
                    dto.setStock(p.getStock());
                    return dto;
                })
                .toList();
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Agrega un producto a un almacén",
            description = "Añade un nuevo producto al inventario de un almacén específico identificado por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto agregado exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Almacén no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos del producto inválidos",
                    content = @Content)
    })
    @PostMapping("/{id}/productos")
    public ResponseEntity<Void> agregarProducto(@PathVariable String id, @RequestBody ProductoDTO productoDTO) {
        Producto producto = new Producto(
                productoDTO.getCodigo(),
                productoDTO.getNombre(),
                productoDTO.getCategoria(),
                productoDTO.getPrecio(),
                productoDTO.getStock()
        );
        service.agregarProducto(id, producto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Compara inventarios entre dos almacenes",
            description = "Compara los inventarios de dos almacenes y devuelve las diferencias en stock o productos faltantes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comparación realizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ComparisonResultDTO.class))),
            @ApiResponse(responseCode = "404", description = "Uno o ambos almacenes no encontrados",
                    content = @Content)
    })
    @GetMapping("/comparar")
    public ResponseEntity<ComparisonResultDTO> compararInventarios(
            @RequestParam String idAlmacen1,
            @RequestParam String idAlmacen2) {
        ComparisonResult result = service.compararInventarios(idAlmacen1, idAlmacen2);
        ComparisonResultDTO dto = new ComparisonResultDTO();
        dto.setAlmacen1Nombre(result.getAlmacen1Nombre());
        dto.setAlmacen2Nombre(result.getAlmacen2Nombre());
        dto.setDiferencias(result.getDiferencias());
        return ResponseEntity.ok(dto);
    }
}