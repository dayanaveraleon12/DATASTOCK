package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

/**
 * A HistorialError.
 */
@Entity
@Table(name = "historial_error")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HistorialError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nombre_error", length = 150, nullable = false)
    private String nombreError;

    @NotNull
    @Size(max = 255)
    @Column(name = "mensaje", length = 255, nullable = false)
    private String mensaje;

    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    private Instant fechaHora;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "user", "tipoDocumento", "historialErroreses", "cuentaSitioses", "evidenciaSalidases" },
        allowSetters = true
    )
    private Cuenta cuenta;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HistorialError id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreError() {
        return this.nombreError;
    }

    public HistorialError nombreError(String nombreError) {
        this.setNombreError(nombreError);
        return this;
    }

    public void setNombreError(String nombreError) {
        this.nombreError = nombreError;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public HistorialError mensaje(String mensaje) {
        this.setMensaje(mensaje);
        return this;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Instant getFechaHora() {
        return this.fechaHora;
    }

    public HistorialError fechaHora(Instant fechaHora) {
        this.setFechaHora(fechaHora);
        return this;
    }

    public void setFechaHora(Instant fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Cuenta getCuenta() {
        return this.cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public HistorialError cuenta(Cuenta cuenta) {
        this.setCuenta(cuenta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistorialError)) {
            return false;
        }
        return getId() != null && getId().equals(((HistorialError) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistorialError{" +
            "id=" + getId() +
            ", nombreError='" + getNombreError() + "'" +
            ", mensaje='" + getMensaje() + "'" +
            ", fechaHora='" + getFechaHora() + "'" +
            "}";
    }
}
