package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * A Remision.
 */
@Entity
@Table(name = "remision")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Remision implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "cantidad_remitida", nullable = false)
    private Integer cantidadRemitida;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "producto", "sitio", "lote", "estadoProducto", "trasladoProductoSitioses", "remisioneses", "salidases" },
        allowSetters = true
    )
    private ProductoSitio productoSitio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "cuentaSitio", "remisioneses", "trasladoses" }, allowSetters = true)
    private Codigo codigo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "productoSitioses", "cuentaSitioses", "remisionesDestinos", "trasladosDestinos", "evidenciaSalidases" },
        allowSetters = true
    )
    private Sitio sitioDestino;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Remision id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidadRemitida() {
        return this.cantidadRemitida;
    }

    public Remision cantidadRemitida(Integer cantidadRemitida) {
        this.setCantidadRemitida(cantidadRemitida);
        return this;
    }

    public void setCantidadRemitida(Integer cantidadRemitida) {
        this.cantidadRemitida = cantidadRemitida;
    }

    public ProductoSitio getProductoSitio() {
        return this.productoSitio;
    }

    public void setProductoSitio(ProductoSitio productoSitio) {
        this.productoSitio = productoSitio;
    }

    public Remision productoSitio(ProductoSitio productoSitio) {
        this.setProductoSitio(productoSitio);
        return this;
    }

    public Codigo getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Codigo codigo) {
        this.codigo = codigo;
    }

    public Remision codigo(Codigo codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public Sitio getSitioDestino() {
        return this.sitioDestino;
    }

    public void setSitioDestino(Sitio sitio) {
        this.sitioDestino = sitio;
    }

    public Remision sitioDestino(Sitio sitio) {
        this.setSitioDestino(sitio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Remision)) {
            return false;
        }
        return getId() != null && getId().equals(((Remision) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Remision{" +
            "id=" + getId() +
            ", cantidadRemitida=" + getCantidadRemitida() +
            "}";
    }
}
