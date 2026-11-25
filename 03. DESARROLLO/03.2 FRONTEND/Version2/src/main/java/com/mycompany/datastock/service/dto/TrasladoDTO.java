package com.mycompany.datastock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.Traslado} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrasladoDTO implements Serializable {

    private Long id;

    @Size(max = 150)
    private String descripcion;

    @NotNull
    private CuentaSitioDTO cuentaSitio;

    @NotNull
    private SitioDTO sitioDestino;

    @NotNull
    private CodigoDTO codigo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CuentaSitioDTO getCuentaSitio() {
        return cuentaSitio;
    }

    public void setCuentaSitio(CuentaSitioDTO cuentaSitio) {
        this.cuentaSitio = cuentaSitio;
    }

    public SitioDTO getSitioDestino() {
        return sitioDestino;
    }

    public void setSitioDestino(SitioDTO sitioDestino) {
        this.sitioDestino = sitioDestino;
    }

    public CodigoDTO getCodigo() {
        return codigo;
    }

    public void setCodigo(CodigoDTO codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrasladoDTO)) {
            return false;
        }

        TrasladoDTO trasladoDTO = (TrasladoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trasladoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrasladoDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", cuentaSitio=" + getCuentaSitio() +
            ", sitioDestino=" + getSitioDestino() +
            ", codigo=" + getCodigo() +
            "}";
    }
}
