package com.mycompany.datastock.service.dto;

import com.mycompany.datastock.domain.enumeration.Estado;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.Producto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String nombreEspecificacion;

    @NotNull
    private Estado estadoDisponibilidad;

    @NotNull
    private CategoriaDTO categoria;

    @NotNull
    private PresentacionDTO presentacion;

    @NotNull
    private MarcaDTO marca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEspecificacion() {
        return nombreEspecificacion;
    }

    public void setNombreEspecificacion(String nombreEspecificacion) {
        this.nombreEspecificacion = nombreEspecificacion;
    }

    public Estado getEstadoDisponibilidad() {
        return estadoDisponibilidad;
    }

    public void setEstadoDisponibilidad(Estado estadoDisponibilidad) {
        this.estadoDisponibilidad = estadoDisponibilidad;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public PresentacionDTO getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(PresentacionDTO presentacion) {
        this.presentacion = presentacion;
    }

    public MarcaDTO getMarca() {
        return marca;
    }

    public void setMarca(MarcaDTO marca) {
        this.marca = marca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoDTO)) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", nombreEspecificacion='" + getNombreEspecificacion() + "'" +
            ", estadoDisponibilidad='" + getEstadoDisponibilidad() + "'" +
            ", categoria=" + getCategoria() +
            ", presentacion=" + getPresentacion() +
            ", marca=" + getMarca() +
            "}";
    }
}
