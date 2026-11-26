package com.mycompany.datastock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.CuentaSitio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CuentaSitioDTO implements Serializable {

    private Long id;

    @NotNull
    private CuentaDTO cuenta;

    @NotNull
    private SitioDTO sitio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CuentaDTO getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaDTO cuenta) {
        this.cuenta = cuenta;
    }

    public SitioDTO getSitio() {
        return sitio;
    }

    public void setSitio(SitioDTO sitio) {
        this.sitio = sitio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CuentaSitioDTO)) {
            return false;
        }

        CuentaSitioDTO cuentaSitioDTO = (CuentaSitioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cuentaSitioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CuentaSitioDTO{" +
            "id=" + getId() +
            ", cuenta=" + getCuenta() +
            ", sitio=" + getSitio() +
            "}";
    }
}
