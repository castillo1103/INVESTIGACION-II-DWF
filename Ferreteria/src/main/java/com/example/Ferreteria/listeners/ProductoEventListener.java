package com.example.Ferreteria.listeners;

import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.example.Ferreteria.events.ProductoAgotadoEvent;
import com.example.Ferreteria.events.ProductoCreadoEvent;
import com.example.Ferreteria.events.StockBajoEvent;
import org.slf4j.Logger;

@Component
public class ProductoEventListener {
    private static final Logger log = LoggerFactory.getLogger(ProductoEventListener.class);

    //Este listener reacciona a los eventos publicados

    @Async
    @EventListener
    public void onProductoCreado(ProductoCreadoEvent event) {
        log.info("[Evento] Producto creado -> {} (Notificación simulada)", event.getProducto().getNombre());
// Simular envío de correo / notificación
        try { Thread.sleep(700); } catch (InterruptedException ignored) {}
        log.info("[Async Task] Email enviado al proveedor sobre el producto: {}", event.getProducto().getNombre());
    }


    @Async
    @EventListener
    public void onStockBajo(StockBajoEvent event) {
        log.warn("[Alerta] Stock bajo para {}: {} unidades", event.getProducto().getNombre(), event.getProducto().getStock());
// Aquí podrían integrarse llamadas a sistema de compras
    }


    @Async
    @EventListener
    public void onProductoAgotado(ProductoAgotadoEvent event) {
        log.error("[Alerta CRITICA] Producto agotado: {}", event.getProducto().getNombre());
// Notificar al equipo o proveedor
    }

}
