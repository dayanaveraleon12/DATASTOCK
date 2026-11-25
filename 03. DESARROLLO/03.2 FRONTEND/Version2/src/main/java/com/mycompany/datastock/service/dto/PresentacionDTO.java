package com.mycompany.datastock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.Presentacion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PresentacionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String tipoPresentacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoPresentacion() {
        return tipoPresentacion;
    }

    public void setTipoPresentacion(String tipoPresentacion) {
        this.tipoPresentacion = tipoPresentacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PresentacionDTO)) {
            return false;
        }

        PresentacionDTO presentacionDTO = (PresentacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, presentacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PresentacionDTO{" +
            "id=" + getId() +
            ", tipoPresentacion='" + getTipoPresentacion() + "'" +
            "}";
    }
}
