package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.datastock.domain.enumeration.Estado;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Producto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "nombre_especificacion", length = 200, nullable = false, unique = true)
    private String nombreEspecificacion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_disponibilidad", nullable = false)
    private Estado estadoDisponibilidad;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "productoses" }, allowSetters = true)
    private Categoria categoria;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "productoses" }, allowSetters = true)
    private Presentacion presentacion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "productoses" }, allowSetters = true)
    private Marca marca;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producto")
    @JsonIgnoreProperties(value = { "producto", "productoSitioses" }, allowSetters = true)
    private Set<Lote> loteses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producto")
    @JsonIgnoreProperties(
        value = { "producto", "sitio", "lote", "estadoProducto", "trasladoProductoSitioses", "remisioneses", "salidases" },
        allowSetters = true
    )
    private Set<ProductoSitio> productoSitioses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Producto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreEspecificacion() {
        return this.nombreEspecificacion;
    }

    public Producto nombreEspecificacion(String nombreEspecificacion) {
        this.setNombreEspecificacion(nombreEspecificacion);
        return this;
    }

    public void setNombreEspecificacion(String nombreEspecificacion) {
        this.nombreEspecificacion = nombreEspecificacion;
    }

    public Estado getEstadoDisponibilidad() {
        return this.estadoDisponibilidad;
    }

    public Producto estadoDisponibilidad(Estado estadoDisponibilidad) {
        this.setEstadoDisponibilidad(estadoDisponibilidad);
        return this;
    }

    public void setEstadoDisponibilidad(Estado estadoDisponibilidad) {
        this.estadoDisponibilidad = estadoDisponibilidad;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Producto categoria(Categoria categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public Presentacion getPresentacion() {
        return this.presentacion;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }

    public Producto presentacion(Presentacion presentacion) {
        this.setPresentacion(presentacion);
        return this;
    }

    public Marca getMarca() {
        return this.marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Producto marca(Marca marca) {
        this.setMarca(marca);
        return this;
    }

    public Set<Lote> getLoteses() {
        return this.loteses;
    }

    public void setLoteses(Set<Lote> lotes) {
        if (this.loteses != null) {
            this.loteses.forEach(i -> i.setProducto(null));
        }
        if (lotes != null) {
            lotes.forEach(i -> i.setProducto(this));
        }
        this.loteses = lotes;
    }

    public Producto loteses(Set<Lote> lotes) {
        this.setLoteses(lotes);
        return this;
    }

    public Producto addLotes(Lote lote) {
        this.loteses.add(lote);
        lote.setProducto(this);
        return this;
    }

    public Producto removeLotes(Lote lote) {
        this.loteses.remove(lote);
        lote.setProducto(null);
        return this;
    }

    public Set<ProductoSitio> getProductoSitioses() {
        return this.productoSitioses;
    }

    public void setProductoSitioses(Set<ProductoSitio> productoSitios) {
        if (this.productoSitioses != null) {
            this.productoSitioses.forEach(i -> i.setProducto(null));
        }
        if (productoSitios != null) {
            productoSitios.forEach(i -> i.setProducto(this));
        }
        this.productoSitioses = productoSitios;
    }

    public Producto productoSitioses(Set<ProductoSitio> productoSitios) {
        this.setProductoSitioses(productoSitios);
        return this;
    }

    public Producto addProductoSitios(ProductoSitio productoSitio) {
        this.productoSitioses.add(productoSitio);
        productoSitio.setProducto(this);
        return this;
    }

    public Producto removeProductoSitios(ProductoSitio productoSitio) {
        this.productoSitioses.remove(productoSitio);
        productoSitio.setProducto(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return getId() != null && getId().equals(((Producto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", nombreEspecificacion='" + getNombreEspecificacion() + "'" +
            ", estadoDisponibilidad='" + getEstadoDisponibilidad() + "'" +
            "}";
    }
}
