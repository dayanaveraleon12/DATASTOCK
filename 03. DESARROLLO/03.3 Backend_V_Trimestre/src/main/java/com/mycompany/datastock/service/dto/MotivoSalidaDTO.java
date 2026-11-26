package com.mycompany.datastock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.MotivoSalida} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MotivoSalidaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 150)
    private String tipoMotivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoMotivo() {
        return tipoMotivo;
    }

    public void setTipoMotivo(String tipoMotivo) {
        this.tipoMotivo = tipoMotivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MotivoSalidaDTO)) {
            return false;
        }

        MotivoSalidaDTO motivoSalidaDTO = (MotivoSalidaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, motivoSalidaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MotivoSalidaDTO{" +
            "id=" + getId() +
            ", tipoMotivo='" + getTipoMotivo() + "'" +
            "}";
    }
}
