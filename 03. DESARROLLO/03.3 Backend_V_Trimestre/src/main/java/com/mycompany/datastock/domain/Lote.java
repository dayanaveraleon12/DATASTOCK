package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Lote.
 */
@Entity
@Table(name = "lote")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Lote implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @NotNull
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @NotNull
    @Size(max = 100)
    @Column(name = "codigo_lote", length = 100, nullable = false)
    private String codigoLote;

    @Size(max = 10)
    @Column(name = "descripcion", length = 10)
    private String descripcion;

    @NotNull
    @Size(max = 255)
    @Column(name = "factura_proveedor", length = 255, nullable = false)
    private String facturaProveedor;

    @NotNull
    @Min(value = 0)
    @Column(name = "cantidad_ingresada", nullable = false)
    private Integer cantidadIngresada;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "categoria", "presentacion", "marca", "loteses", "productoSitioses" }, allowSetters = true)
    private Producto producto;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lote")
    @JsonIgnoreProperties(
        value = { "producto", "sitio", "lote", "estadoProducto", "trasladoProductoSitioses", "remisioneses", "salidases" },
        allowSetters = true
    )
    private Set<ProductoSitio> productoSitioses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Lote id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaIngreso() {
        return this.fechaIngreso;
    }

    public Lote fechaIngreso(LocalDate fechaIngreso) {
        this.setFechaIngreso(fechaIngreso);
        return this;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    public Lote fechaVencimiento(LocalDate fechaVencimiento) {
        this.setFechaVencimiento(fechaVencimiento);
        return this;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getCodigoLote() {
        return this.codigoLote;
    }

    public Lote codigoLote(String codigoLote) {
        this.setCodigoLote(codigoLote);
        return this;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Lote descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFacturaProveedor() {
        return this.facturaProveedor;
    }

    public Lote facturaProveedor(String facturaProveedor) {
        this.setFacturaProveedor(facturaProveedor);
        return this;
    }

    public void setFacturaProveedor(String facturaProveedor) {
        this.facturaProveedor = facturaProveedor;
    }

    public Integer getCantidadIngresada() {
        return this.cantidadIngresada;
    }

    public Lote cantidadIngresada(Integer cantidadIngresada) {
        this.setCantidadIngresada(cantidadIngresada);
        return this;
    }

    public void setCantidadIngresada(Integer cantidadIngresada) {
        this.cantidadIngresada = cantidadIngresada;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Lote producto(Producto producto) {
        this.setProducto(producto);
        return this;
    }

    public Set<ProductoSitio> getProductoSitioses() {
        return this.productoSitioses;
    }

    public void setProductoSitioses(Set<ProductoSitio> productoSitios) {
        if (this.productoSitioses != null) {
            this.productoSitioses.forEach(i -> i.setLote(null));
        }
        if (productoSitios != null) {
            productoSitios.forEach(i -> i.setLote(this));
        }
        this.productoSitioses = productoSitios;
    }

    public Lote productoSitioses(Set<ProductoSitio> productoSitios) {
        this.setProductoSitioses(productoSitios);
        return this;
    }

    public Lote addProductoSitios(ProductoSitio productoSitio) {
        this.productoSitioses.add(productoSitio);
        productoSitio.setLote(this);
        return this;
    }

    public Lote removeProductoSitios(ProductoSitio productoSitio) {
        this.productoSitioses.remove(productoSitio);
        productoSitio.setLote(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lote)) {
            return false;
        }
        return getId() != null && getId().equals(((Lote) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lote{" +
            "id=" + getId() +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", fechaVencimiento='" + getFechaVencimiento() + "'" +
            ", codigoLote='" + getCodigoLote() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", facturaProveedor='" + getFacturaProveedor() + "'" +
            ", cantidadIngresada=" + getCantidadIngresada() +
            "}";
    }
}
