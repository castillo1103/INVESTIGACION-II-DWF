package com.example.Ferreteria.service;

import com.example.Ferreteria.events.ProductoAgotadoEvent;
import com.example.Ferreteria.events.ProductoCreadoEvent;
import com.example.Ferreteria.events.StockBajoEvent;
import com.example.Ferreteria.model.Producto;
import com.example.Ferreteria.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final ApplicationEventPublisher publisher;

    //esta clase es quien maneja la logica de negocio y publica los eventos, este es un claro ejemplo de event driven


    @Autowired
    public ProductoService(ProductoRepository productoRepository, ApplicationEventPublisher publisher) {
        this.productoRepository = productoRepository;
        this.publisher = publisher;
    }
    public List<Producto> listAll(){
        return productoRepository.findAll();
    }
    public Optional<Producto> getById(Long id) {
        return productoRepository.findById(id);
    }


    @Transactional
    public Producto create(Producto producto) {
        Producto saved = productoRepository.save(producto);
        publisher.publishEvent(new ProductoCreadoEvent(saved));
        checkStock(saved);
        return saved;
    }


    @Transactional
    public Producto update(Long id, Producto dto) {
        Producto p = productoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        p.setNombre(dto.getNombre());
        p.setCategoria(dto.getCategoria());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());
        Producto saved = productoRepository.save(p);
        checkStock(saved);
        return saved;
    }


    @Transactional
    public void delete(Long id) {
        Producto p = productoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        productoRepository.delete(p);
    }


    @Transactional
    public Producto adjustStock(Long id, int delta) {
        Producto p = productoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        int newStock = Math.max(0, p.getStock() + delta);
        p.setStock(newStock);
        Producto saved = productoRepository.save(p);
        checkStock(saved);
        return saved;
    }


    private void checkStock(Producto p) {
        if (p.getStock() == 0) publisher.publishEvent(new ProductoAgotadoEvent(p));
        else if (p.getStock() < 5) publisher.publishEvent(new StockBajoEvent(p));
    }


    // Custom exception for service
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String msg) { super(msg); }
    }
}
