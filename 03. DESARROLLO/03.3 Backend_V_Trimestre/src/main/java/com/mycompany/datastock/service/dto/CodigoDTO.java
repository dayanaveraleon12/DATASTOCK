package com.mycompany.datastock.service.dto;

import com.mycompany.datastock.domain.enumeration.EstadoOperacion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.Codigo} entity.
 */
@Schema(description = "Cabecera de la remisión: los datos comunes se guardan una sola vez")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CodigoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fechaRemision;

    @NotNull
    @Size(max = 255)
    private String codigo;

    @NotNull
    private EstadoOperacion estadoOperacion;

    @NotNull
    private CuentaSitioDTO cuentaSitio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaRemision() {
        return fechaRemision;
    }

    public void setFechaRemision(LocalDate fechaRemision) {
        this.fechaRemision = fechaRemision;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public EstadoOperacion getEstadoOperacion() {
        return estadoOperacion;
    }

    public void setEstadoOperacion(EstadoOperacion estadoOperacion) {
        this.estadoOperacion = estadoOperacion;
    }

    public CuentaSitioDTO getCuentaSitio() {
        return cuentaSitio;
    }

    public void setCuentaSitio(CuentaSitioDTO cuentaSitio) {
        this.cuentaSitio = cuentaSitio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodigoDTO)) {
            return false;
        }

        CodigoDTO codigoDTO = (CodigoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, codigoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CodigoDTO{" +
            "id=" + getId() +
            ", fechaRemision='" + getFechaRemision() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", estadoOperacion='" + getEstadoOperacion() + "'" +
            ", cuentaSitio=" + getCuentaSitio() +
            "}";
    }
}
