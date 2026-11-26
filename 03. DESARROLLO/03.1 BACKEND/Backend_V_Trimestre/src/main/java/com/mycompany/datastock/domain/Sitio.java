package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sitio.
 */
@Entity
@Table(name = "sitio")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sitio implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 70)
    @Column(name = "nombre", length = 70, nullable = false, unique = true)
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sitio")
    @JsonIgnoreProperties(
        value = { "producto", "sitio", "lote", "estadoProducto", "trasladoProductoSitioses", "remisioneses", "salidases" },
        allowSetters = true
    )
    private Set<ProductoSitio> productoSitioses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sitio")
    @JsonIgnoreProperties(value = { "cuenta", "sitio", "codigoses", "trasladoses", "salidases" }, allowSetters = true)
    private Set<CuentaSitio> cuentaSitioses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sitioDestino")
    @JsonIgnoreProperties(value = { "productoSitio", "codigo", "sitioDestino" }, allowSetters = true)
    private Set<Remision> remisionesDestinos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sitioDestino")
    @JsonIgnoreProperties(value = { "cuentaSitio", "sitioDestino", "codigo", "trasladoProductoSitioses" }, allowSetters = true)
    private Set<Traslado> trasladosDestinos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sitioSubida")
    @JsonIgnoreProperties(value = { "cuenta", "sitioSubida", "salida" }, allowSetters = true)
    private Set<EvidenciaSalida> evidenciaSalidases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sitio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Sitio nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<ProductoSitio> getProductoSitioses() {
        return this.productoSitioses;
    }

    public void setProductoSitioses(Set<ProductoSitio> productoSitios) {
        if (this.productoSitioses != null) {
            this.productoSitioses.forEach(i -> i.setSitio(null));
        }
        if (productoSitios != null) {
            productoSitios.forEach(i -> i.setSitio(this));
        }
        this.productoSitioses = productoSitios;
    }

    public Sitio productoSitioses(Set<ProductoSitio> productoSitios) {
        this.setProductoSitioses(productoSitios);
        return this;
    }

    public Sitio addProductoSitios(ProductoSitio productoSitio) {
        this.productoSitioses.add(productoSitio);
        productoSitio.setSitio(this);
        return this;
    }

    public Sitio removeProductoSitios(ProductoSitio productoSitio) {
        this.productoSitioses.remove(productoSitio);
        productoSitio.setSitio(null);
        return this;
    }

    public Set<CuentaSitio> getCuentaSitioses() {
        return this.cuentaSitioses;
    }

    public void setCuentaSitioses(Set<CuentaSitio> cuentaSitios) {
        if (this.cuentaSitioses != null) {
            this.cuentaSitioses.forEach(i -> i.setSitio(null));
        }
        if (cuentaSitios != null) {
            cuentaSitios.forEach(i -> i.setSitio(this));
        }
        this.cuentaSitioses = cuentaSitios;
    }

    public Sitio cuentaSitioses(Set<CuentaSitio> cuentaSitios) {
        this.setCuentaSitioses(cuentaSitios);
        return this;
    }

    public Sitio addCuentaSitios(CuentaSitio cuentaSitio) {
        this.cuentaSitioses.add(cuentaSitio);
        cuentaSitio.setSitio(this);
        return this;
    }

    public Sitio removeCuentaSitios(CuentaSitio cuentaSitio) {
        this.cuentaSitioses.remove(cuentaSitio);
        cuentaSitio.setSitio(null);
        return this;
    }

    public Set<Remision> getRemisionesDestinos() {
        return this.remisionesDestinos;
    }

    public void setRemisionesDestinos(Set<Remision> remisions) {
        if (this.remisionesDestinos != null) {
            this.remisionesDestinos.forEach(i -> i.setSitioDestino(null));
        }
        if (remisions != null) {
            remisions.forEach(i -> i.setSitioDestino(this));
        }
        this.remisionesDestinos = remisions;
    }

    public Sitio remisionesDestinos(Set<Remision> remisions) {
        this.setRemisionesDestinos(remisions);
        return this;
    }

    public Sitio addRemisionesDestino(Remision remision) {
        this.remisionesDestinos.add(remision);
        remision.setSitioDestino(this);
        return this;
    }

    public Sitio removeRemisionesDestino(Remision remision) {
        this.remisionesDestinos.remove(remision);
        remision.setSitioDestino(null);
        return this;
    }

    public Set<Traslado> getTrasladosDestinos() {
        return this.trasladosDestinos;
    }

    public void setTrasladosDestinos(Set<Traslado> traslados) {
        if (this.trasladosDestinos != null) {
            this.trasladosDestinos.forEach(i -> i.setSitioDestino(null));
        }
        if (traslados != null) {
            traslados.forEach(i -> i.setSitioDestino(this));
        }
        this.trasladosDestinos = traslados;
    }

    public Sitio trasladosDestinos(Set<Traslado> traslados) {
        this.setTrasladosDestinos(traslados);
        return this;
    }

    public Sitio addTrasladosDestino(Traslado traslado) {
        this.trasladosDestinos.add(traslado);
        traslado.setSitioDestino(this);
        return this;
    }

    public Sitio removeTrasladosDestino(Traslado traslado) {
        this.trasladosDestinos.remove(traslado);
        traslado.setSitioDestino(null);
        return this;
    }

    public Set<EvidenciaSalida> getEvidenciaSalidases() {
        return this.evidenciaSalidases;
    }

    public void setEvidenciaSalidases(Set<EvidenciaSalida> evidenciaSalidas) {
        if (this.evidenciaSalidases != null) {
            this.evidenciaSalidases.forEach(i -> i.setSitioSubida(null));
        }
        if (evidenciaSalidas != null) {
            evidenciaSalidas.forEach(i -> i.setSitioSubida(this));
        }
        this.evidenciaSalidases = evidenciaSalidas;
    }

    public Sitio evidenciaSalidases(Set<EvidenciaSalida> evidenciaSalidas) {
        this.setEvidenciaSalidases(evidenciaSalidas);
        return this;
    }

    public Sitio addEvidenciaSalidas(EvidenciaSalida evidenciaSalida) {
        this.evidenciaSalidases.add(evidenciaSalida);
        evidenciaSalida.setSitioSubida(this);
        return this;
    }

    public Sitio removeEvidenciaSalidas(EvidenciaSalida evidenciaSalida) {
        this.evidenciaSalidases.remove(evidenciaSalida);
        evidenciaSalida.setSitioSubida(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sitio)) {
            return false;
        }
        return getId() != null && getId().equals(((Sitio) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sitio{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
