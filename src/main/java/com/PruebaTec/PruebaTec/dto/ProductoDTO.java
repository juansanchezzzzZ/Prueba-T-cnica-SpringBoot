package com.PruebaTec.PruebaTec.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    @Getter
    private Long id;
    private String nombre;
    private String categoria;
    private Double precio;
    private int cantidad;

    public Long getId() {
        return id;
    }
}
