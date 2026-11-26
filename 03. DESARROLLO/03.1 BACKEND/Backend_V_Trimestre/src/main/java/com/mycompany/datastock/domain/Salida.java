package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Para el Administrador que SOLO completa la salida de inventario
 */
@Entity
@Table(name = "salida")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Salida implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 255)
    @Column(name = "respaldo_imagen", length = 255)
    private String respaldoImagen;

    @NotNull
    @Min(value = 1)
    @Column(name = "cantidad_a_sacar", nullable = false)
    private Integer cantidadASacar;

    @Size(max = 150)
    @Column(name = "observaciones", length = 150)
    private String observaciones;

    @JsonIgnoreProperties(value = { "cuenta", "sitioSubida", "salida" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private EvidenciaSalida evidenciaSalida;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "cuenta", "sitio", "codigoses", "trasladoses", "salidases" }, allowSetters = true)
    private CuentaSitio cuentaSitio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "salidases" }, allowSetters = true)
    private MotivoSalida motivoSalida;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_salida__producto_sitios",
        joinColumns = @JoinColumn(name = "salida_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_sitios_id")
    )
    @JsonIgnoreProperties(
        value = { "producto", "sitio", "lote", "estadoProducto", "trasladoProductoSitioses", "remisioneses", "salidases" },
        allowSetters = true
    )
    private Set<ProductoSitio> productoSitioses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Salida id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRespaldoImagen() {
        return this.respaldoImagen;
    }

    public Salida respaldoImagen(String respaldoImagen) {
        this.setRespaldoImagen(respaldoImagen);
        return this;
    }

    public void setRespaldoImagen(String respaldoImagen) {
        this.respaldoImagen = respaldoImagen;
    }

    public Integer getCantidadASacar() {
        return this.cantidadASacar;
    }

    public Salida cantidadASacar(Integer cantidadASacar) {
        this.setCantidadASacar(cantidadASacar);
        return this;
    }

    public void setCantidadASacar(Integer cantidadASacar) {
        this.cantidadASacar = cantidadASacar;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public Salida observaciones(String observaciones) {
        this.setObservaciones(observaciones);
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public EvidenciaSalida getEvidenciaSalida() {
        return this.evidenciaSalida;
    }

    public void setEvidenciaSalida(EvidenciaSalida evidenciaSalida) {
        this.evidenciaSalida = evidenciaSalida;
    }

    public Salida evidenciaSalida(EvidenciaSalida evidenciaSalida) {
        this.setEvidenciaSalida(evidenciaSalida);
        return this;
    }

    public CuentaSitio getCuentaSitio() {
        return this.cuentaSitio;
    }

    public void setCuentaSitio(CuentaSitio cuentaSitio) {
        this.cuentaSitio = cuentaSitio;
    }

    public Salida cuentaSitio(CuentaSitio cuentaSitio) {
        this.setCuentaSitio(cuentaSitio);
        return this;
    }

    public MotivoSalida getMotivoSalida() {
        return this.motivoSalida;
    }

    public void setMotivoSalida(MotivoSalida motivoSalida) {
        this.motivoSalida = motivoSalida;
    }

    public Salida motivoSalida(MotivoSalida motivoSalida) {
        this.setMotivoSalida(motivoSalida);
        return this;
    }

    public Set<ProductoSitio> getProductoSitioses() {
        return this.productoSitioses;
    }

    public void setProductoSitioses(Set<ProductoSitio> productoSitios) {
        this.productoSitioses = productoSitios;
    }

    public Salida productoSitioses(Set<ProductoSitio> productoSitios) {
        this.setProductoSitioses(productoSitios);
        return this;
    }

    public Salida addProductoSitios(ProductoSitio productoSitio) {
        this.productoSitioses.add(productoSitio);
        return this;
    }

    public Salida removeProductoSitios(ProductoSitio productoSitio) {
        this.productoSitioses.remove(productoSitio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Salida)) {
            return false;
        }
        return getId() != null && getId().equals(((Salida) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Salida{" +
            "id=" + getId() +
            ", respaldoImagen='" + getRespaldoImagen() + "'" +
            ", cantidadASacar=" + getCantidadASacar() +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
