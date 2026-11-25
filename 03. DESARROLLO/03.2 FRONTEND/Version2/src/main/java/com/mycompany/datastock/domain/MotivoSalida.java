package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MotivoSalida.
 */
@Entity
@Table(name = "motivo_salida")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MotivoSalida implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "tipo_motivo", length = 150, nullable = false, unique = true)
    private String tipoMotivo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "motivoSalida")
    @JsonIgnoreProperties(value = { "cuentaSitio", "motivoSalida", "evidenciaSalida", "productoSitioses" }, allowSetters = true)
    private Set<Salida> salidases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MotivoSalida id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoMotivo() {
        return this.tipoMotivo;
    }

    public MotivoSalida tipoMotivo(String tipoMotivo) {
        this.setTipoMotivo(tipoMotivo);
        return this;
    }

    public void setTipoMotivo(String tipoMotivo) {
        this.tipoMotivo = tipoMotivo;
    }

    public Set<Salida> getSalidases() {
        return this.salidases;
    }

    public void setSalidases(Set<Salida> salidas) {
        if (this.salidases != null) {
            this.salidases.forEach(i -> i.setMotivoSalida(null));
        }
        if (salidas != null) {
            salidas.forEach(i -> i.setMotivoSalida(this));
        }
        this.salidases = salidas;
    }

    public MotivoSalida salidases(Set<Salida> salidas) {
        this.setSalidases(salidas);
        return this;
    }

    public MotivoSalida addSalidas(Salida salida) {
        this.salidases.add(salida);
        salida.setMotivoSalida(this);
        return this;
    }

    public MotivoSalida removeSalidas(Salida salida) {
        this.salidases.remove(salida);
        salida.setMotivoSalida(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MotivoSalida)) {
            return false;
        }
        return getId() != null && getId().equals(((MotivoSalida) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MotivoSalida{" +
            "id=" + getId() +
            ", tipoMotivo='" + getTipoMotivo() + "'" +
            "}";
    }
}
