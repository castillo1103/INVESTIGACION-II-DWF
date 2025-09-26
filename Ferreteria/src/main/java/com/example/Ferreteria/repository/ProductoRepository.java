package com.example.Ferreteria.repository;

import com.example.Ferreteria.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

//esta la interfaz que extiende de jpa repository y le pasamos la entidad y el tipo de dato del id