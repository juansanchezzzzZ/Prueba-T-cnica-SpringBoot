package com.PruebaTec.PruebaTec.mapper;

import com.PruebaTec.PruebaTec.dto.DetalleVentaDTO;
import com.PruebaTec.PruebaTec.dto.ProductoDTO;
import com.PruebaTec.PruebaTec.dto.SucursalDTO;
import com.PruebaTec.PruebaTec.dto.VentaDTO;
import com.PruebaTec.PruebaTec.model.DetalleVenta;
import com.PruebaTec.PruebaTec.model.Producto;
import com.PruebaTec.PruebaTec.model.Sucursal;
import com.PruebaTec.PruebaTec.model.Venta;

import java.util.stream.Collectors;

public class Mapper {

    //Mapeo de Producto a ProductoDTO
    public static ProductoDTO toDTO(Producto p) {
        if (p == null) return null;

        return ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .precio(p.getPrecio())
                .categoria(p.getCategoria())
                .cantidad(p.getCantidad())
                .build();
    }


    //Venta a VentaDTO
    public static VentaDTO toDTO(Venta venta) {
        if (venta == null) return null;

        var detalle = venta.getDetalle().stream()
                .map(det ->
                        DetalleVentaDTO.builder()
                                .id(det.getProd().getId())
                                .nombreProd(det.getProd().getNombre())
                                .cantProd(det.getCantProd())
                                .precio(det.getPrecio())
                                .subtotal(det.getPrecio() * det.getCantProd())
                                .build()
                )
                .collect(Collectors.toList());

        var total = detalle.stream()
                .map(DetalleVentaDTO::getSubtotal)
                .reduce(0.0, Double::sum);

        return VentaDTO.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .idSucursal(venta.getSucursal().getId())
                .estado(venta.getEstado())
                .detalle(detalle)
                .total(total)
                .build();
    }

    //Mapeo de Sucursal a SucursalDTO
    public static SucursalDTO toDTO(Sucursal sucursal) {
        if (sucursal == null) return null;

        return SucursalDTO.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .direccion(sucursal.getDireccion())
                .build();

    }
}
