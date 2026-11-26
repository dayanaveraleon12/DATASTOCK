package com.mycompany.datastock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.datastock.domain.Lote} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoteDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fechaIngreso;

    @NotNull
    private LocalDate fechaVencimiento;

    @NotNull
    @Size(max = 100)
    private String codigoLote;

    @Size(max = 10)
    private String descripcion;

    @NotNull
    @Size(max = 255)
    private String facturaProveedor;

    @NotNull
    @Min(value = 0)
    private Integer cantidadIngresada;

    @NotNull
    private ProductoDTO producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFacturaProveedor() {
        return facturaProveedor;
    }

    public void setFacturaProveedor(String facturaProveedor) {
        this.facturaProveedor = facturaProveedor;
    }

    public Integer getCantidadIngresada() {
        return cantidadIngresada;
    }

    public void setCantidadIngresada(Integer cantidadIngresada) {
        this.cantidadIngresada = cantidadIngresada;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoteDTO)) {
            return false;
        }

        LoteDTO loteDTO = (LoteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoteDTO{" +
            "id=" + getId() +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", fechaVencimiento='" + getFechaVencimiento() + "'" +
            ", codigoLote='" + getCodigoLote() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", facturaProveedor='" + getFacturaProveedor() + "'" +
            ", cantidadIngresada=" + getCantidadIngresada() +
            ", producto=" + getProducto() +
            "}";
    }
}
