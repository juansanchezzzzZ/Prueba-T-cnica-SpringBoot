package com.PruebaTec.PruebaTec.service;

import com.PruebaTec.PruebaTec.dto.DetalleVentaDTO;
import com.PruebaTec.PruebaTec.dto.VentaDTO;
import com.PruebaTec.PruebaTec.mapper.Mapper;
import com.PruebaTec.PruebaTec.model.DetalleVenta;
import com.PruebaTec.PruebaTec.model.Producto;
import com.PruebaTec.PruebaTec.model.Sucursal;
import com.PruebaTec.PruebaTec.model.Venta;
import com.PruebaTec.PruebaTec.repository.ProductoRepository;
import com.PruebaTec.PruebaTec.repository.SucursalRepository;
import com.PruebaTec.PruebaTec.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService implements IVentaService {
    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private SucursalRepository sucursalRepo;

    @Override
    public List<VentaDTO> traerVentas() {
        List<Venta> ventas = ventaRepo.findAll();
        List<VentaDTO> ventasDto = new ArrayList<>();
        VentaDTO dto;
        for (Venta v : ventas) {
            dto = Mapper.toDTO(v);
            ventasDto.add(dto);
        }
        return ventasDto;
    }

    @Override
    public VentaDTO crearVenta(VentaDTO ventaDto) {
        if (ventaDto == null) throw new RuntimeException("VentaDto es nulo");
        if (ventaDto.getIdSucursal() == null) throw new RuntimeException("Id de sucursal es nulo");
        if ((ventaDto.getDetalle() == null) || ventaDto.getDetalle().isEmpty()) throw new RuntimeException("Debe incluir almenos un producto");

        //buscar la sucursal
        Sucursal sucursal = sucursalRepo.findById(ventaDto.getIdSucursal()).orElse(null);
        if(sucursal == null) throw new RuntimeException("Sucursal no encontrado");

        //crear la venta
        Venta venta = new Venta();
        venta.setFecha(ventaDto.getFecha());
        venta.setEstado(ventaDto.getEstado());
        venta.setSucursal(sucursal);
        venta.setTotal(ventaDto.getTotal());

        //lista de detalles
        List<DetalleVenta> detalles = new ArrayList<>();


        for (DetalleVentaDTO detDTO : ventaDto.getDetalle()) {
            // Buscar producto por id
            Producto p = productoRepo.findByNombre(detDTO.getNombreProd()).orElse(null);
            if (p ==null)
                throw new RuntimeException("No se puede crear un producto");

            //Crear detalle
            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setProd(p);
            detalleVenta.setPrecio(detDTO.getPrecio());
            detalleVenta.setCantProd(detDTO.getCantProd());
            detalleVenta.setVenta(venta);

            detalles.add(detalleVenta);
        }
        venta.setDetalle(detalles);

        // guardamos en la bd

        ventaRepo.save(venta);

        return Mapper.toDTO(venta);

    }

    @Override
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDto) {

        //Buscar si la venta existe para actualizarla
        Venta venta = ventaRepo.findById(id).orElse(null);
        if (venta == null) throw new RuntimeException("Venta no encontrada");

        if (ventaDto.getFecha() != null) {
            venta.setFecha(ventaDto.getFecha());
        }

        if (ventaDto.getEstado() != null) {
            venta.setEstado(ventaDto.getEstado());
        }
        if (ventaDto.getTotal() != null) {
            venta.setTotal(ventaDto.getTotal());
        }
        if (ventaDto.getIdSucursal() != null) {
            Sucursal sucursal = sucursalRepo.findById(id).orElse(null);
            if (sucursal == null) throw new RuntimeException("Sucursal no encontrado");
            ventaRepo.save(venta);
        }

        return Mapper.toDTO(venta);
    }

    @Override
    public void eliminarVenta(Long id) {
        Venta venta = ventaRepo.findById(id).orElse(null);
        if (venta == null) throw new RuntimeException("Venta no encontrada");
        ventaRepo.delete(venta);
    }
}
