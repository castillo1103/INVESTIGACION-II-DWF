package com.example.Ferreteria.events;

import com.example.Ferreteria.model.Producto;

public class ProductoCreadoEvent {
    private final Producto producto;

    public ProductoCreadoEvent(Producto producto) {
        this.producto = producto;
    }

    public Producto getProducto() {
        return producto;
    }
}
