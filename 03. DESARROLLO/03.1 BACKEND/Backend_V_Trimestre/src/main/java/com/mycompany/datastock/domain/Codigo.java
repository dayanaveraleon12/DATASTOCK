package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.datastock.domain.enumeration.EstadoOperacion;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Cabecera de la remisión: los datos comunes se guardan una sola vez
 */
@Entity
@Table(name = "codigo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Codigo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_remision", nullable = false)
    private LocalDate fechaRemision;

    @NotNull
    @Size(max = 255)
    @Column(name = "codigo", length = 255, nullable = false)
    private String codigo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_operacion", nullable = false)
    private EstadoOperacion estadoOperacion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "cuenta", "sitio", "codigoses", "trasladoses", "salidases" }, allowSetters = true)
    private CuentaSitio cuentaSitio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "codigo")
    @JsonIgnoreProperties(value = { "productoSitio", "codigo", "sitioDestino" }, allowSetters = true)
    private Set<Remision> remisioneses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "codigo")
    @JsonIgnoreProperties(value = { "cuentaSitio", "sitioDestino", "codigo", "trasladoProductoSitioses" }, allowSetters = true)
    private Set<Traslado> trasladoses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Codigo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaRemision() {
        return this.fechaRemision;
    }

    public Codigo fechaRemision(LocalDate fechaRemision) {
        this.setFechaRemision(fechaRemision);
        return this;
    }

    public void setFechaRemision(LocalDate fechaRemision) {
        this.fechaRemision = fechaRemision;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Codigo codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public EstadoOperacion getEstadoOperacion() {
        return this.estadoOperacion;
    }

    public Codigo estadoOperacion(EstadoOperacion estadoOperacion) {
        this.setEstadoOperacion(estadoOperacion);
        return this;
    }

    public void setEstadoOperacion(EstadoOperacion estadoOperacion) {
        this.estadoOperacion = estadoOperacion;
    }

    public CuentaSitio getCuentaSitio() {
        return this.cuentaSitio;
    }

    public void setCuentaSitio(CuentaSitio cuentaSitio) {
        this.cuentaSitio = cuentaSitio;
    }

    public Codigo cuentaSitio(CuentaSitio cuentaSitio) {
        this.setCuentaSitio(cuentaSitio);
        return this;
    }

    public Set<Remision> getRemisioneses() {
        return this.remisioneses;
    }

    public void setRemisioneses(Set<Remision> remisions) {
        if (this.remisioneses != null) {
            this.remisioneses.forEach(i -> i.setCodigo(null));
        }
        if (remisions != null) {
            remisions.forEach(i -> i.setCodigo(this));
        }
        this.remisioneses = remisions;
    }

    public Codigo remisioneses(Set<Remision> remisions) {
        this.setRemisioneses(remisions);
        return this;
    }

    public Codigo addRemisiones(Remision remision) {
        this.remisioneses.add(remision);
        remision.setCodigo(this);
        return this;
    }

    public Codigo removeRemisiones(Remision remision) {
        this.remisioneses.remove(remision);
        remision.setCodigo(null);
        return this;
    }

    public Set<Traslado> getTrasladoses() {
        return this.trasladoses;
    }

    public void setTrasladoses(Set<Traslado> traslados) {
        if (this.trasladoses != null) {
            this.trasladoses.forEach(i -> i.setCodigo(null));
        }
        if (traslados != null) {
            traslados.forEach(i -> i.setCodigo(this));
        }
        this.trasladoses = traslados;
    }

    public Codigo trasladoses(Set<Traslado> traslados) {
        this.setTrasladoses(traslados);
        return this;
    }

    public Codigo addTraslados(Traslado traslado) {
        this.trasladoses.add(traslado);
        traslado.setCodigo(this);
        return this;
    }

    public Codigo removeTraslados(Traslado traslado) {
        this.trasladoses.remove(traslado);
        traslado.setCodigo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Codigo)) {
            return false;
        }
        return getId() != null && getId().equals(((Codigo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Codigo{" +
            "id=" + getId() +
            ", fechaRemision='" + getFechaRemision() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", estadoOperacion='" + getEstadoOperacion() + "'" +
            "}";
    }
}
