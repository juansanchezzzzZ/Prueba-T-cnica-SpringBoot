package com.PruebaTec.PruebaTec.service;

import com.PruebaTec.PruebaTec.dto.SucursalDTO;
import com.PruebaTec.PruebaTec.exception.NotFoundException;
import com.PruebaTec.PruebaTec.mapper.Mapper;
import com.PruebaTec.PruebaTec.model.Sucursal;
import com.PruebaTec.PruebaTec.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class SucursalService  implements ISucursalService{

    @Autowired
    private SucursalRepository repo;

    @Override
    public List<SucursalDTO> traerSucursales() {
        return repo.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public SucursalDTO crearSucursal(SucursalDTO sucursalDto) {
        Sucursal sucursal = Sucursal.builder()
                .id(sucursalDto.getId())
                .nombre(sucursalDto.getNombre())
                .direccion(sucursalDto.getDireccion())
                .build();

        return Mapper.toDTO(repo.save(sucursal));
    }

    @Override
    public SucursalDTO actualizarSucursal(Long id, SucursalDTO SucursalDto) {
        //buscar si existe la sucursal
        Sucursal sucursal = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe el sucursal con id: " + id));

        sucursal.setNombre(SucursalDto.getNombre());
        sucursal.setDireccion(SucursalDto.getDireccion());
        return Mapper.toDTO(repo.save(sucursal));
    }

    @Override
    public void eliminarSucursal(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("No existe el sucursal con id: " + id);
        }

        repo.deleteById(id);

    }
}
