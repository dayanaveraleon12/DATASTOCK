package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Traslado.
 */
@Entity
@Table(name = "traslado")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Traslado implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 150)
    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "cuenta", "sitio", "codigoses", "trasladoses", "salidases" }, allowSetters = true)
    private CuentaSitio cuentaSitio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "productoSitioses", "cuentaSitioses", "remisionesDestinos", "trasladosDestinos", "evidenciaSalidases" },
        allowSetters = true
    )
    private Sitio sitioDestino;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "cuentaSitio", "remisioneses", "trasladoses" }, allowSetters = true)
    private Codigo codigo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "traslado")
    @JsonIgnoreProperties(value = { "traslado", "productoSitio" }, allowSetters = true)
    private Set<TrasladoProductoSitio> trasladoProductoSitioses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Traslado id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Traslado descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CuentaSitio getCuentaSitio() {
        return this.cuentaSitio;
    }

    public void setCuentaSitio(CuentaSitio cuentaSitio) {
        this.cuentaSitio = cuentaSitio;
    }

    public Traslado cuentaSitio(CuentaSitio cuentaSitio) {
        this.setCuentaSitio(cuentaSitio);
        return this;
    }

    public Sitio getSitioDestino() {
        return this.sitioDestino;
    }

    public void setSitioDestino(Sitio sitio) {
        this.sitioDestino = sitio;
    }

    public Traslado sitioDestino(Sitio sitio) {
        this.setSitioDestino(sitio);
        return this;
    }

    public Codigo getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Codigo codigo) {
        this.codigo = codigo;
    }

    public Traslado codigo(Codigo codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public Set<TrasladoProductoSitio> getTrasladoProductoSitioses() {
        return this.trasladoProductoSitioses;
    }

    public void setTrasladoProductoSitioses(Set<TrasladoProductoSitio> trasladoProductoSitios) {
        if (this.trasladoProductoSitioses != null) {
            this.trasladoProductoSitioses.forEach(i -> i.setTraslado(null));
        }
        if (trasladoProductoSitios != null) {
            trasladoProductoSitios.forEach(i -> i.setTraslado(this));
        }
        this.trasladoProductoSitioses = trasladoProductoSitios;
    }

    public Traslado trasladoProductoSitioses(Set<TrasladoProductoSitio> trasladoProductoSitios) {
        this.setTrasladoProductoSitioses(trasladoProductoSitios);
        return this;
    }

    public Traslado addTrasladoProductoSitios(TrasladoProductoSitio trasladoProductoSitio) {
        this.trasladoProductoSitioses.add(trasladoProductoSitio);
        trasladoProductoSitio.setTraslado(this);
        return this;
    }

    public Traslado removeTrasladoProductoSitios(TrasladoProductoSitio trasladoProductoSitio) {
        this.trasladoProductoSitioses.remove(trasladoProductoSitio);
        trasladoProductoSitio.setTraslado(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Traslado)) {
            return false;
        }
        return getId() != null && getId().equals(((Traslado) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Traslado{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
