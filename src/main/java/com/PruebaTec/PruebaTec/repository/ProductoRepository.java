package com.PruebaTec.PruebaTec.repository;

import com.PruebaTec.PruebaTec.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    //buscar producto por nombre
    Optional<Producto> findByNombre(String nombre);
}
