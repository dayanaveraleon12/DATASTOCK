package com.mycompany.datastock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.Remision} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RemisionDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer cantidadRemitida;

    @NotNull
    private ProductoSitioDTO productoSitio;

    @NotNull
    private CodigoDTO codigo;

    @NotNull
    private SitioDTO sitioDestino;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidadRemitida() {
        return cantidadRemitida;
    }

    public void setCantidadRemitida(Integer cantidadRemitida) {
        this.cantidadRemitida = cantidadRemitida;
    }

    public ProductoSitioDTO getProductoSitio() {
        return productoSitio;
    }

    public void setProductoSitio(ProductoSitioDTO productoSitio) {
        this.productoSitio = productoSitio;
    }

    public CodigoDTO getCodigo() {
        return codigo;
    }

    public void setCodigo(CodigoDTO codigo) {
        this.codigo = codigo;
    }

    public SitioDTO getSitioDestino() {
        return sitioDestino;
    }

    public void setSitioDestino(SitioDTO sitioDestino) {
        this.sitioDestino = sitioDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RemisionDTO)) {
            return false;
        }

        RemisionDTO remisionDTO = (RemisionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, remisionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RemisionDTO{" +
            "id=" + getId() +
            ", cantidadRemitida=" + getCantidadRemitida() +
            ", productoSitio=" + getProductoSitio() +
            ", codigo=" + getCodigo() +
            ", sitioDestino=" + getSitioDestino() +
            "}";
    }
}
