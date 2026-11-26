package com.mycompany.datastock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.HistorialError} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HistorialErrorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String nombreError;

    @NotNull
    @Size(max = 255)
    private String mensaje;

    @NotNull
    private Instant fechaHora;

    @NotNull
    private CuentaDTO cuenta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreError() {
        return nombreError;
    }

    public void setNombreError(String nombreError) {
        this.nombreError = nombreError;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Instant getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Instant fechaHora) {
        this.fechaHora = fechaHora;
    }

    public CuentaDTO getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaDTO cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HistorialErrorDTO)) {
            return false;
        }

        HistorialErrorDTO historialErrorDTO = (HistorialErrorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, historialErrorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HistorialErrorDTO{" +
            "id=" + getId() +
            ", nombreError='" + getNombreError() + "'" +
            ", mensaje='" + getMensaje() + "'" +
            ", fechaHora='" + getFechaHora() + "'" +
            ", cuenta=" + getCuenta() +
            "}";
    }
}
