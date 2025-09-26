package com.example.Ferreteria.events;

import com.example.Ferreteria.model.Producto;

public class ProductoAgotadoEvent {
    private final Producto producto;

    public ProductoAgotadoEvent(Producto producto) {
        this.producto = producto;
    }

    public Producto getProducto() {
        return producto;
    }
}
