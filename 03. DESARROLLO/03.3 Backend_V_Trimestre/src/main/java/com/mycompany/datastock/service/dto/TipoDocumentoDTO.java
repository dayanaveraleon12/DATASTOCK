package com.mycompany.datastock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.TipoDocumento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoDocumentoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String tipoDocumento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDocumentoDTO)) {
            return false;
        }

        TipoDocumentoDTO tipoDocumentoDTO = (TipoDocumentoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tipoDocumentoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDocumentoDTO{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            "}";
    }
}
