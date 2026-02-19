package com.PruebaTec.PruebaTec.controller;

import com.PruebaTec.PruebaTec.dto.VentaDTO;
import com.PruebaTec.PruebaTec.service.IVentaService;
import com.PruebaTec.PruebaTec.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private IVentaService ventaService;


    @GetMapping
    public ResponseEntity<List<VentaDTO>> traerVentas() {
        return ResponseEntity.ok(ventaService.traerVentas());
    }


    @PostMapping
    public ResponseEntity<VentaDTO> create(@RequestBody VentaDTO dto) {
        VentaDTO created = ventaService.crearVenta(dto);
        return ResponseEntity
                .created(URI.create("/api/ventas/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> actualizar(
            @PathVariable Long id,
            @RequestBody VentaDTO dto) {

        // Actualiza fecha, estado, idSucursal y reemplaza el detalle
        return ResponseEntity.ok(
                ventaService.actualizarVenta(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }
}
