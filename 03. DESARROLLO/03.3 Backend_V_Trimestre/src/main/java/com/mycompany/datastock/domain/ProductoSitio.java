package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProductoSitio.
 */
@Entity
@Table(name = "producto_sitio")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductoSitio implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "cantidad_sitio", nullable = false)
    private Integer cantidadSitio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "categoria", "presentacion", "marca", "loteses", "productoSitioses" }, allowSetters = true)
    private Producto producto;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "productoSitioses", "cuentaSitioses", "remisionesDestinos", "trasladosDestinos", "evidenciaSalidases" },
        allowSetters = true
    )
    private Sitio sitio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "producto", "productoSitioses" }, allowSetters = true)
    private Lote lote;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "productoSitioses" }, allowSetters = true)
    private EstadoProducto estadoProducto;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productoSitio")
    @JsonIgnoreProperties(value = { "traslado", "productoSitio" }, allowSetters = true)
    private Set<TrasladoProductoSitio> trasladoProductoSitioses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productoSitio")
    @JsonIgnoreProperties(value = { "productoSitio", "codigo", "sitioDestino" }, allowSetters = true)
    private Set<Remision> remisioneses = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "productoSitioses")
    @JsonIgnoreProperties(value = { "cuentaSitio", "motivoSalida", "evidenciaSalida", "productoSitioses" }, allowSetters = true)
    private Set<Salida> salidases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductoSitio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidadSitio() {
        return this.cantidadSitio;
    }

    public ProductoSitio cantidadSitio(Integer cantidadSitio) {
        this.setCantidadSitio(cantidadSitio);
        return this;
    }

    public void setCantidadSitio(Integer cantidadSitio) {
        this.cantidadSitio = cantidadSitio;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ProductoSitio producto(Producto producto) {
        this.setProducto(producto);
        return this;
    }

    public Sitio getSitio() {
        return this.sitio;
    }

    public void setSitio(Sitio sitio) {
        this.sitio = sitio;
    }

    public ProductoSitio sitio(Sitio sitio) {
        this.setSitio(sitio);
        return this;
    }

    public Lote getLote() {
        return this.lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public ProductoSitio lote(Lote lote) {
        this.setLote(lote);
        return this;
    }

    public EstadoProducto getEstadoProducto() {
        return this.estadoProducto;
    }

    public void setEstadoProducto(EstadoProducto estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public ProductoSitio estadoProducto(EstadoProducto estadoProducto) {
        this.setEstadoProducto(estadoProducto);
        return this;
    }

    public Set<TrasladoProductoSitio> getTrasladoProductoSitioses() {
        return this.trasladoProductoSitioses;
    }

    public void setTrasladoProductoSitioses(Set<TrasladoProductoSitio> trasladoProductoSitios) {
        if (this.trasladoProductoSitioses != null) {
            this.trasladoProductoSitioses.forEach(i -> i.setProductoSitio(null));
        }
        if (trasladoProductoSitios != null) {
            trasladoProductoSitios.forEach(i -> i.setProductoSitio(this));
        }
        this.trasladoProductoSitioses = trasladoProductoSitios;
    }

    public ProductoSitio trasladoProductoSitioses(Set<TrasladoProductoSitio> trasladoProductoSitios) {
        this.setTrasladoProductoSitioses(trasladoProductoSitios);
        return this;
    }

    public ProductoSitio addTrasladoProductoSitios(TrasladoProductoSitio trasladoProductoSitio) {
        this.trasladoProductoSitioses.add(trasladoProductoSitio);
        trasladoProductoSitio.setProductoSitio(this);
        return this;
    }

    public ProductoSitio removeTrasladoProductoSitios(TrasladoProductoSitio trasladoProductoSitio) {
        this.trasladoProductoSitioses.remove(trasladoProductoSitio);
        trasladoProductoSitio.setProductoSitio(null);
        return this;
    }

    public Set<Remision> getRemisioneses() {
        return this.remisioneses;
    }

    public void setRemisioneses(Set<Remision> remisions) {
        if (this.remisioneses != null) {
            this.remisioneses.forEach(i -> i.setProductoSitio(null));
        }
        if (remisions != null) {
            remisions.forEach(i -> i.setProductoSitio(this));
        }
        this.remisioneses = remisions;
    }

    public ProductoSitio remisioneses(Set<Remision> remisions) {
        this.setRemisioneses(remisions);
        return this;
    }

    public ProductoSitio addRemisiones(Remision remision) {
        this.remisioneses.add(remision);
        remision.setProductoSitio(this);
        return this;
    }

    public ProductoSitio removeRemisiones(Remision remision) {
        this.remisioneses.remove(remision);
        remision.setProductoSitio(null);
        return this;
    }

    public Set<Salida> getSalidases() {
        return this.salidases;
    }

    public void setSalidases(Set<Salida> salidas) {
        if (this.salidases != null) {
            this.salidases.forEach(i -> i.removeProductoSitios(this));
        }
        if (salidas != null) {
            salidas.forEach(i -> i.addProductoSitios(this));
        }
        this.salidases = salidas;
    }

    public ProductoSitio salidases(Set<Salida> salidas) {
        this.setSalidases(salidas);
        return this;
    }

    public ProductoSitio addSalidas(Salida salida) {
        this.salidases.add(salida);
        salida.getProductoSitioses().add(this);
        return this;
    }

    public ProductoSitio removeSalidas(Salida salida) {
        this.salidases.remove(salida);
        salida.getProductoSitioses().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoSitio)) {
            return false;
        }
        return getId() != null && getId().equals(((ProductoSitio) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductoSitio{" +
            "id=" + getId() +
            ", cantidadSitio=" + getCantidadSitio() +
            "}";
    }
}
