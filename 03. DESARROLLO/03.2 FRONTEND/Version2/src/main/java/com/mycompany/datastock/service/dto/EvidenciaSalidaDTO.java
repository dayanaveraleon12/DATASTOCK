package com.mycompany.datastock.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.EvidenciaSalida} entity.
 */
@Schema(description = "Solo para el empleado que únicamente sube el archivo de excel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EvidenciaSalidaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String archivoExcel;

    @NotNull
    @Size(max = 255)
    private String nombreExcel;

    @NotNull
    private LocalDate fechaCarga;

    @NotNull
    private CuentaDTO cuenta;

    @NotNull
    private SitioDTO sitioSubida;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArchivoExcel() {
        return archivoExcel;
    }

    public void setArchivoExcel(String archivoExcel) {
        this.archivoExcel = archivoExcel;
    }

    public String getNombreExcel() {
        return nombreExcel;
    }

    public void setNombreExcel(String nombreExcel) {
        this.nombreExcel = nombreExcel;
    }

    public LocalDate getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(LocalDate fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public CuentaDTO getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaDTO cuenta) {
        this.cuenta = cuenta;
    }

    public SitioDTO getSitioSubida() {
        return sitioSubida;
    }

    public void setSitioSubida(SitioDTO sitioSubida) {
        this.sitioSubida = sitioSubida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvidenciaSalidaDTO)) {
            return false;
        }

        EvidenciaSalidaDTO evidenciaSalidaDTO = (EvidenciaSalidaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, evidenciaSalidaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvidenciaSalidaDTO{" +
            "id=" + getId() +
            ", archivoExcel='" + getArchivoExcel() + "'" +
            ", nombreExcel='" + getNombreExcel() + "'" +
            ", fechaCarga='" + getFechaCarga() + "'" +
            ", cuenta=" + getCuenta() +
            ", sitioSubida=" + getSitioSubida() +
            "}";
    }
}
