package com.mycompany.datastock.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.TrasladoProductoSitio} entity.
 */
@Schema(description = "Detalle del traslado: lote/producto/cantidad que se mueve de un sitio a otro")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrasladoProductoSitioDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer cantidadTraslado;

    @NotNull
    private TrasladoDTO traslado;

    @NotNull
    private ProductoSitioDTO productoSitio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidadTraslado() {
        return cantidadTraslado;
    }

    public void setCantidadTraslado(Integer cantidadTraslado) {
        this.cantidadTraslado = cantidadTraslado;
    }

    public TrasladoDTO getTraslado() {
        return traslado;
    }

    public void setTraslado(TrasladoDTO traslado) {
        this.traslado = traslado;
    }

    public ProductoSitioDTO getProductoSitio() {
        return productoSitio;
    }

    public void setProductoSitio(ProductoSitioDTO productoSitio) {
        this.productoSitio = productoSitio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrasladoProductoSitioDTO)) {
            return false;
        }

        TrasladoProductoSitioDTO trasladoProductoSitioDTO = (TrasladoProductoSitioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trasladoProductoSitioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrasladoProductoSitioDTO{" +
            "id=" + getId() +
            ", cantidadTraslado=" + getCantidadTraslado() +
            ", traslado=" + getTraslado() +
            ", productoSitio=" + getProductoSitio() +
            "}";
    }
}
