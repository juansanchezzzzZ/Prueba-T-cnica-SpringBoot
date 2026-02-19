package com.PruebaTec.PruebaTec.service;

import com.PruebaTec.PruebaTec.dto.ProductoDTO;
import com.PruebaTec.PruebaTec.exception.NotFoundException;
import com.PruebaTec.PruebaTec.mapper.Mapper;
import com.PruebaTec.PruebaTec.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.PruebaTec.PruebaTec.model.Producto;

import java.util.List;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private ProductoRepository repo;

    @Override
    public List<ProductoDTO> traerProductos() {
        return repo.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDto) {
        Producto prod = Producto.builder()
                .nombre(productoDto.getNombre())
                .categoria(productoDto.getCategoria())
                .precio(productoDto.getPrecio())
                .cantidad(productoDto.getCantidad())
                .build();
        return Mapper.toDTO(repo.save(prod));

    }

    @Override
    public ProductoDTO actualizarProducto(Long id, ProductoDTO productoDto) {
        //buscar si existe el producto en la bd
        Producto prod = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro el producto con id: " + id));

        prod.setNombre(productoDto.getNombre());
        prod.setCategoria(productoDto.getCategoria());
        prod.setPrecio(productoDto.getPrecio());
        prod.setCantidad(productoDto.getCantidad());

        return Mapper.toDTO(repo.save(prod));
    }

    @Override
    public void eliminarProducto(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("No se encontro el producto con id: " + id);
        }

        repo.deleteById(id);
    }
}
