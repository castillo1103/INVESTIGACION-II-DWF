package com.example.Ferreteria.events;

import com.example.Ferreteria.model.Producto;

public class StockBajoEvent {
    private final Producto producto;

    public StockBajoEvent(Producto producto) {
        this.producto = producto;
    }

    public Producto getProducto() {
        return producto;
    }
}
