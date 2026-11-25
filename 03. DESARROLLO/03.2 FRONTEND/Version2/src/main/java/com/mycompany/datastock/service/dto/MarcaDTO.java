package com.mycompany.datastock.service.dto;

import com.mycompany.datastock.domain.enumeration.Estado;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.Marca} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MarcaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String marca;

    @NotNull
    private Estado estadoMarca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Estado getEstadoMarca() {
        return estadoMarca;
    }

    public void setEstadoMarca(Estado estadoMarca) {
        this.estadoMarca = estadoMarca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MarcaDTO)) {
            return false;
        }

        MarcaDTO marcaDTO = (MarcaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, marcaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MarcaDTO{" +
            "id=" + getId() +
            ", marca='" + getMarca() + "'" +
            ", estadoMarca='" + getEstadoMarca() + "'" +
            "}";
    }
}
