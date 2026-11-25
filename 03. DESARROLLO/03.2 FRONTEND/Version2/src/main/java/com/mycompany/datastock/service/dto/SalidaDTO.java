package com.mycompany.datastock.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.Salida} entity.
 */
@Schema(description = "Para el Administrador que SOLO completa la salida de inventario")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalidaDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String respaldoImagen;

    @NotNull
    @Min(value = 1)
    private Integer cantidadASacar;

    @Size(max = 150)
    private String observaciones;

    @NotNull
    private CuentaSitioDTO cuentaSitio;

    @NotNull
    private MotivoSalidaDTO motivoSalida;

    @NotNull
    private EvidenciaSalidaDTO evidenciaSalida;

    private Set<ProductoSitioDTO> productoSitioses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRespaldoImagen() {
        return respaldoImagen;
    }

    public void setRespaldoImagen(String respaldoImagen) {
        this.respaldoImagen = respaldoImagen;
    }

    public Integer getCantidadASacar() {
        return cantidadASacar;
    }

    public void setCantidadASacar(Integer cantidadASacar) {
        this.cantidadASacar = cantidadASacar;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public CuentaSitioDTO getCuentaSitio() {
        return cuentaSitio;
    }

    public void setCuentaSitio(CuentaSitioDTO cuentaSitio) {
        this.cuentaSitio = cuentaSitio;
    }

    public MotivoSalidaDTO getMotivoSalida() {
        return motivoSalida;
    }

    public void setMotivoSalida(MotivoSalidaDTO motivoSalida) {
        this.motivoSalida = motivoSalida;
    }

    public EvidenciaSalidaDTO getEvidenciaSalida() {
        return evidenciaSalida;
    }

    public void setEvidenciaSalida(EvidenciaSalidaDTO evidenciaSalida) {
        this.evidenciaSalida = evidenciaSalida;
    }

    public Set<ProductoSitioDTO> getProductoSitioses() {
        return productoSitioses;
    }

    public void setProductoSitioses(Set<ProductoSitioDTO> productoSitioses) {
        this.productoSitioses = productoSitioses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalidaDTO)) {
            return false;
        }

        SalidaDTO salidaDTO = (SalidaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, salidaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalidaDTO{" +
            "id=" + getId() +
            ", respaldoImagen='" + getRespaldoImagen() + "'" +
            ", cantidadASacar=" + getCantidadASacar() +
            ", observaciones='" + getObservaciones() + "'" +
            ", cuentaSitio=" + getCuentaSitio() +
            ", motivoSalida=" + getMotivoSalida() +
            ", evidenciaSalida=" + getEvidenciaSalida() +
            ", productoSitioses=" + getProductoSitioses() +
            "}";
    }
}
