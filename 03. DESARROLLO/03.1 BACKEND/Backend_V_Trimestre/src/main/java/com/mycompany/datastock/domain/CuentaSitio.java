package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CuentaSitio.
 */
@Entity
@Table(name = "cuenta_sitio")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CuentaSitio implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "user", "tipoDocumento", "historialErroreses", "cuentaSitioses", "evidenciaSalidases" },
        allowSetters = true
    )
    private Cuenta cuenta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "productoSitioses", "cuentaSitioses", "remisionesDestinos", "trasladosDestinos", "evidenciaSalidases" },
        allowSetters = true
    )
    private Sitio sitio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuentaSitio")
    @JsonIgnoreProperties(value = { "cuentaSitio", "remisioneses", "trasladoses" }, allowSetters = true)
    private Set<Codigo> codigoses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuentaSitio")
    @JsonIgnoreProperties(value = { "cuentaSitio", "sitioDestino", "codigo", "trasladoProductoSitioses" }, allowSetters = true)
    private Set<Traslado> trasladoses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuentaSitio")
    @JsonIgnoreProperties(value = { "evidenciaSalida", "cuentaSitio", "motivoSalida", "productoSitioses" }, allowSetters = true)
    private Set<Salida> salidases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CuentaSitio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cuenta getCuenta() {
        return this.cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public CuentaSitio cuenta(Cuenta cuenta) {
        this.setCuenta(cuenta);
        return this;
    }

    public Sitio getSitio() {
        return this.sitio;
    }

    public void setSitio(Sitio sitio) {
        this.sitio = sitio;
    }

    public CuentaSitio sitio(Sitio sitio) {
        this.setSitio(sitio);
        return this;
    }

    public Set<Codigo> getCodigoses() {
        return this.codigoses;
    }

    public void setCodigoses(Set<Codigo> codigos) {
        if (this.codigoses != null) {
            this.codigoses.forEach(i -> i.setCuentaSitio(null));
        }
        if (codigos != null) {
            codigos.forEach(i -> i.setCuentaSitio(this));
        }
        this.codigoses = codigos;
    }

    public CuentaSitio codigoses(Set<Codigo> codigos) {
        this.setCodigoses(codigos);
        return this;
    }

    public CuentaSitio addCodigos(Codigo codigo) {
        this.codigoses.add(codigo);
        codigo.setCuentaSitio(this);
        return this;
    }

    public CuentaSitio removeCodigos(Codigo codigo) {
        this.codigoses.remove(codigo);
        codigo.setCuentaSitio(null);
        return this;
    }

    public Set<Traslado> getTrasladoses() {
        return this.trasladoses;
    }

    public void setTrasladoses(Set<Traslado> traslados) {
        if (this.trasladoses != null) {
            this.trasladoses.forEach(i -> i.setCuentaSitio(null));
        }
        if (traslados != null) {
            traslados.forEach(i -> i.setCuentaSitio(this));
        }
        this.trasladoses = traslados;
    }

    public CuentaSitio trasladoses(Set<Traslado> traslados) {
        this.setTrasladoses(traslados);
        return this;
    }

    public CuentaSitio addTraslados(Traslado traslado) {
        this.trasladoses.add(traslado);
        traslado.setCuentaSitio(this);
        return this;
    }

    public CuentaSitio removeTraslados(Traslado traslado) {
        this.trasladoses.remove(traslado);
        traslado.setCuentaSitio(null);
        return this;
    }

    public Set<Salida> getSalidases() {
        return this.salidases;
    }

    public void setSalidases(Set<Salida> salidas) {
        if (this.salidases != null) {
            this.salidases.forEach(i -> i.setCuentaSitio(null));
        }
        if (salidas != null) {
            salidas.forEach(i -> i.setCuentaSitio(this));
        }
        this.salidases = salidas;
    }

    public CuentaSitio salidases(Set<Salida> salidas) {
        this.setSalidases(salidas);
        return this;
    }

    public CuentaSitio addSalidas(Salida salida) {
        this.salidases.add(salida);
        salida.setCuentaSitio(this);
        return this;
    }

    public CuentaSitio removeSalidas(Salida salida) {
        this.salidases.remove(salida);
        salida.setCuentaSitio(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CuentaSitio)) {
            return false;
        }
        return getId() != null && getId().equals(((CuentaSitio) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CuentaSitio{" +
            "id=" + getId() +
            "}";
    }
}
