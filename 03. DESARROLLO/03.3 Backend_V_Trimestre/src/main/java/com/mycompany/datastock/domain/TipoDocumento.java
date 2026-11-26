package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoDocumento.
 */
@Entity
@Table(name = "tipo_documento")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TipoDocumento implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "tipo_documento", length = 10, nullable = false)
    private String tipoDocumento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoDocumento")
    @JsonIgnoreProperties(
        value = { "user", "tipoDocumento", "historialErroreses", "cuentaSitioses", "evidenciaSalidases" },
        allowSetters = true
    )
    private Set<Cuenta> cuentases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoDocumento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return this.tipoDocumento;
    }

    public TipoDocumento tipoDocumento(String tipoDocumento) {
        this.setTipoDocumento(tipoDocumento);
        return this;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Set<Cuenta> getCuentases() {
        return this.cuentases;
    }

    public void setCuentases(Set<Cuenta> cuentas) {
        if (this.cuentases != null) {
            this.cuentases.forEach(i -> i.setTipoDocumento(null));
        }
        if (cuentas != null) {
            cuentas.forEach(i -> i.setTipoDocumento(this));
        }
        this.cuentases = cuentas;
    }

    public TipoDocumento cuentases(Set<Cuenta> cuentas) {
        this.setCuentases(cuentas);
        return this;
    }

    public TipoDocumento addCuentas(Cuenta cuenta) {
        this.cuentases.add(cuenta);
        cuenta.setTipoDocumento(this);
        return this;
    }

    public TipoDocumento removeCuentas(Cuenta cuenta) {
        this.cuentases.remove(cuenta);
        cuenta.setTipoDocumento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDocumento)) {
            return false;
        }
        return getId() != null && getId().equals(((TipoDocumento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDocumento{" +
            "id=" + getId() +
            ", tipoDocumento='" + getTipoDocumento() + "'" +
            "}";
    }
}
