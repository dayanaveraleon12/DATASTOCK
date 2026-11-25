package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EstadoProducto.
 */
@Entity
@Table(name = "estado_producto")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoProducto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "tipo_estado", length = 50, nullable = false, unique = true)
    private String tipoEstado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estadoProducto")
    @JsonIgnoreProperties(
        value = { "producto", "sitio", "lote", "estadoProducto", "trasladoProductoSitioses", "remisioneses", "salidases" },
        allowSetters = true
    )
    private Set<ProductoSitio> productoSitioses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EstadoProducto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoEstado() {
        return this.tipoEstado;
    }

    public EstadoProducto tipoEstado(String tipoEstado) {
        this.setTipoEstado(tipoEstado);
        return this;
    }

    public void setTipoEstado(String tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    public Set<ProductoSitio> getProductoSitioses() {
        return this.productoSitioses;
    }

    public void setProductoSitioses(Set<ProductoSitio> productoSitios) {
        if (this.productoSitioses != null) {
            this.productoSitioses.forEach(i -> i.setEstadoProducto(null));
        }
        if (productoSitios != null) {
            productoSitios.forEach(i -> i.setEstadoProducto(this));
        }
        this.productoSitioses = productoSitios;
    }

    public EstadoProducto productoSitioses(Set<ProductoSitio> productoSitios) {
        this.setProductoSitioses(productoSitios);
        return this;
    }

    public EstadoProducto addProductoSitios(ProductoSitio productoSitio) {
        this.productoSitioses.add(productoSitio);
        productoSitio.setEstadoProducto(this);
        return this;
    }

    public EstadoProducto removeProductoSitios(ProductoSitio productoSitio) {
        this.productoSitioses.remove(productoSitio);
        productoSitio.setEstadoProducto(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoProducto)) {
            return false;
        }
        return getId() != null && getId().equals(((EstadoProducto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoProducto{" +
            "id=" + getId() +
            ", tipoEstado='" + getTipoEstado() + "'" +
            "}";
    }
}
