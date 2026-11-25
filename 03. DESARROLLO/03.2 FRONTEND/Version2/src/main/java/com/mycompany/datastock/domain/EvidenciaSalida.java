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
 * Solo para el empleado que únicamente sube el archivo de excel
 */
@Entity
@Table(name = "evidencia_salida")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EvidenciaSalida implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "archivo_excel", length = 255, nullable = false)
    private String archivoExcel;

    @NotNull
    @Size(max = 255)
    @Column(name = "nombre_excel", length = 255, nullable = false)
    private String nombreExcel;

    @NotNull
    @Column(name = "fecha_carga", nullable = false)
    private LocalDate fechaCarga;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "user", "tipoDocumento", "historialErroreses", "cuentaSitioses", "evidenciaSalidases" },
        allowSetters = true
    )
    private Cuenta cuenta;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "productoSitioses", "cuentaSitioses", "remisionesDestinos", "trasladosDestinos", "evidenciaSalidases" },
        allowSetters = true
    )
    private Sitio sitioSubida;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "evidenciaSalida")
    @JsonIgnoreProperties(value = { "cuentaSitio", "motivoSalida", "evidenciaSalida", "productoSitioses" }, allowSetters = true)
    private Set<Salida> salidases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EvidenciaSalida id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArchivoExcel() {
        return this.archivoExcel;
    }

    public EvidenciaSalida archivoExcel(String archivoExcel) {
        this.setArchivoExcel(archivoExcel);
        return this;
    }

    public void setArchivoExcel(String archivoExcel) {
        this.archivoExcel = archivoExcel;
    }

    public String getNombreExcel() {
        return this.nombreExcel;
    }

    public EvidenciaSalida nombreExcel(String nombreExcel) {
        this.setNombreExcel(nombreExcel);
        return this;
    }

    public void setNombreExcel(String nombreExcel) {
        this.nombreExcel = nombreExcel;
    }

    public LocalDate getFechaCarga() {
        return this.fechaCarga;
    }

    public EvidenciaSalida fechaCarga(LocalDate fechaCarga) {
        this.setFechaCarga(fechaCarga);
        return this;
    }

    public void setFechaCarga(LocalDate fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Cuenta getCuenta() {
        return this.cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public EvidenciaSalida cuenta(Cuenta cuenta) {
        this.setCuenta(cuenta);
        return this;
    }

    public Sitio getSitioSubida() {
        return this.sitioSubida;
    }

    public void setSitioSubida(Sitio sitio) {
        this.sitioSubida = sitio;
    }

    public EvidenciaSalida sitioSubida(Sitio sitio) {
        this.setSitioSubida(sitio);
        return this;
    }

    public Set<Salida> getSalidases() {
        return this.salidases;
    }

    public void setSalidases(Set<Salida> salidas) {
        if (this.salidases != null) {
            this.salidases.forEach(i -> i.setEvidenciaSalida(null));
        }
        if (salidas != null) {
            salidas.forEach(i -> i.setEvidenciaSalida(this));
        }
        this.salidases = salidas;
    }

    public EvidenciaSalida salidases(Set<Salida> salidas) {
        this.setSalidases(salidas);
        return this;
    }

    public EvidenciaSalida addSalidas(Salida salida) {
        this.salidases.add(salida);
        salida.setEvidenciaSalida(this);
        return this;
    }

    public EvidenciaSalida removeSalidas(Salida salida) {
        this.salidases.remove(salida);
        salida.setEvidenciaSalida(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvidenciaSalida)) {
            return false;
        }
        return getId() != null && getId().equals(((EvidenciaSalida) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvidenciaSalida{" +
            "id=" + getId() +
            ", archivoExcel='" + getArchivoExcel() + "'" +
            ", nombreExcel='" + getNombreExcel() + "'" +
            ", fechaCarga='" + getFechaCarga() + "'" +
            "}";
    }
}
