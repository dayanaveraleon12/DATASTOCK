package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * Detalle del traslado: lote/producto/cantidad que se mueve de un sitio a otro
 */
@Entity
@Table(name = "traslado_producto_sitio")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrasladoProductoSitio implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "cantidad_traslado", nullable = false)
    private Integer cantidadTraslado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "cuentaSitio", "sitioDestino", "codigo", "trasladoProductoSitioses" }, allowSetters = true)
    private Traslado traslado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "producto", "sitio", "lote", "estadoProducto", "trasladoProductoSitioses", "remisioneses", "salidases" },
        allowSetters = true
    )
    private ProductoSitio productoSitio;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TrasladoProductoSitio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidadTraslado() {
        return this.cantidadTraslado;
    }

    public TrasladoProductoSitio cantidadTraslado(Integer cantidadTraslado) {
        this.setCantidadTraslado(cantidadTraslado);
        return this;
    }

    public void setCantidadTraslado(Integer cantidadTraslado) {
        this.cantidadTraslado = cantidadTraslado;
    }

    public Traslado getTraslado() {
        return this.traslado;
    }

    public void setTraslado(Traslado traslado) {
        this.traslado = traslado;
    }

    public TrasladoProductoSitio traslado(Traslado traslado) {
        this.setTraslado(traslado);
        return this;
    }

    public ProductoSitio getProductoSitio() {
        return this.productoSitio;
    }

    public void setProductoSitio(ProductoSitio productoSitio) {
        this.productoSitio = productoSitio;
    }

    public TrasladoProductoSitio productoSitio(ProductoSitio productoSitio) {
        this.setProductoSitio(productoSitio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrasladoProductoSitio)) {
            return false;
        }
        return getId() != null && getId().equals(((TrasladoProductoSitio) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrasladoProductoSitio{" +
            "id=" + getId() +
            ", cantidadTraslado=" + getCantidadTraslado() +
            "}";
    }
}
