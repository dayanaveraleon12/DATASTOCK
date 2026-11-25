package com.mycompany.datastock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.Cuenta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CuentaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String numeroDocumento;

    @NotNull
    @Size(max = 20)
    private String primerNombre;

    @Size(max = 20)
    private String segundoNombre;

    @NotNull
    @Size(max = 20)
    private String primerApellido;

    @Size(max = 20)
    private String segundoApellido;

    @NotNull
    @Size(max = 20)
    private String numeroCelular;

    @NotNull
    @Size(max = 30)
    private String eps;

    @NotNull
    @Size(max = 20)
    private String contactoEmergencia;

    @NotNull
    @Size(max = 20)
    private String parentezcoEmergencia;

    @NotNull
    @Size(max = 20)
    private String nombreEmergencia;

    @NotNull
    private UserDTO user;

    @NotNull
    private TipoDocumentoDTO tipoDocumento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public String getParentezcoEmergencia() {
        return parentezcoEmergencia;
    }

    public void setParentezcoEmergencia(String parentezcoEmergencia) {
        this.parentezcoEmergencia = parentezcoEmergencia;
    }

    public String getNombreEmergencia() {
        return nombreEmergencia;
    }

    public void setNombreEmergencia(String nombreEmergencia) {
        this.nombreEmergencia = nombreEmergencia;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public TipoDocumentoDTO getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CuentaDTO)) {
            return false;
        }

        CuentaDTO cuentaDTO = (CuentaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cuentaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CuentaDTO{" +
            "id=" + getId() +
            ", numeroDocumento='" + getNumeroDocumento() + "'" +
            ", primerNombre='" + getPrimerNombre() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", numeroCelular='" + getNumeroCelular() + "'" +
            ", eps='" + getEps() + "'" +
            ", contactoEmergencia='" + getContactoEmergencia() + "'" +
            ", parentezcoEmergencia='" + getParentezcoEmergencia() + "'" +
            ", nombreEmergencia='" + getNombreEmergencia() + "'" +
            ", user=" + getUser() +
            ", tipoDocumento=" + getTipoDocumento() +
            "}";
    }
}
