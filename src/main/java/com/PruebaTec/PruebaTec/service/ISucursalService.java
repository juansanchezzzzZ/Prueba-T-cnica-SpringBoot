package com.PruebaTec.PruebaTec.service;

import com.PruebaTec.PruebaTec.dto.SucursalDTO;

import java.util.List;

public interface ISucursalService {

    List<SucursalDTO> traerSucursales();
    SucursalDTO crearSucursal(SucursalDTO sucursalDto);
    SucursalDTO actualizarSucursal(Long id, SucursalDTO SucursalDto);
    void eliminarSucursal(Long id);

}
