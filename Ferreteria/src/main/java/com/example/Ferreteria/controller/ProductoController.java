package com.example.Ferreteria.controller;

import com.example.Ferreteria.model.Producto;
import com.example.Ferreteria.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Validated
public class ProductoController {

    //esto expone la API con los Endpoints
    private final ProductoService productoService;


    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }


    @GetMapping
    public ResponseEntity<List<Producto>> listAll() {
        return ResponseEntity.ok(productoService.listAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Producto> getById(@PathVariable Long id) {
        return productoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PostMapping
    public ResponseEntity<Producto> create(@Valid @RequestBody Producto producto) {
        Producto saved = productoService.create(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Producto> update(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        Producto updated = productoService.update(id, producto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }


    // Endpoint para ajustar stock (por ejemplo venta: -1, reabastecer: +10)
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Producto> adjustStock(@PathVariable Long id, @RequestParam int delta) {
        Producto p = productoService.adjustStock(id, delta);
        return ResponseEntity.ok(p);
    }
}
