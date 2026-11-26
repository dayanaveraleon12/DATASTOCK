package com.mycompany.datastock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.ProductoSitio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductoSitioDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer cantidadSitio;

    @NotNull
    private ProductoDTO producto;

    @NotNull
    private SitioDTO sitio;

    @NotNull
    private LoteDTO lote;

    @NotNull
    private EstadoProductoDTO estadoProducto;

    private Set<SalidaDTO> salidases = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidadSitio() {
        return cantidadSitio;
    }

    public void setCantidadSitio(Integer cantidadSitio) {
        this.cantidadSitio = cantidadSitio;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public SitioDTO getSitio() {
        return sitio;
    }

    public void setSitio(SitioDTO sitio) {
        this.sitio = sitio;
    }

    public LoteDTO getLote() {
        return lote;
    }

    public void setLote(LoteDTO lote) {
        this.lote = lote;
    }

    public EstadoProductoDTO getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(EstadoProductoDTO estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public Set<SalidaDTO> getSalidases() {
        return salidases;
    }

    public void setSalidases(Set<SalidaDTO> salidases) {
        this.salidases = salidases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoSitioDTO)) {
            return false;
        }

        ProductoSitioDTO productoSitioDTO = (ProductoSitioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productoSitioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoSitioDTO{" +
            "id=" + getId() +
            ", cantidadSitio=" + getCantidadSitio() +
            ", producto=" + getProducto() +
            ", sitio=" + getSitio() +
            ", lote=" + getLote() +
            ", estadoProducto=" + getEstadoProducto() +
            ", salidases=" + getSalidases() +
            "}";
    }
}
