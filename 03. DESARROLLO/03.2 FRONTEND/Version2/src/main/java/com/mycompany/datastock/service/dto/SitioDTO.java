package com.mycompany.datastock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.Sitio} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SitioDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SitioDTO)) {
            return false;
        }

        SitioDTO sitioDTO = (SitioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sitioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SitioDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
