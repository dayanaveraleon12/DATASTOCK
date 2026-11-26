package com.mycompany.datastock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.EstadoProducto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EstadoProductoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String tipoEstado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoEstado() {
        return tipoEstado;
    }

    public void setTipoEstado(String tipoEstado) {
        this.tipoEstado = tipoEstado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoProductoDTO)) {
            return false;
        }

        EstadoProductoDTO estadoProductoDTO = (EstadoProductoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, estadoProductoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstadoProductoDTO{" +
            "id=" + getId() +
            ", tipoEstado='" + getTipoEstado() + "'" +
            "}";
    }
}
