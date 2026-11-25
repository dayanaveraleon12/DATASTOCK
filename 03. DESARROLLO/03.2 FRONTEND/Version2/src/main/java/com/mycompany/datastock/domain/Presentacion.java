package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Presentacion.
 */
@Entity
@Table(name = "presentacion")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Presentacion implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "tipo_presentacion", length = 50, nullable = false, unique = true)
    private String tipoPresentacion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "presentacion")
    @JsonIgnoreProperties(value = { "categoria", "presentacion", "marca", "loteses", "productoSitioses" }, allowSetters = true)
    private Set<Producto> productoses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Presentacion id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoPresentacion() {
        return this.tipoPresentacion;
    }

    public Presentacion tipoPresentacion(String tipoPresentacion) {
        this.setTipoPresentacion(tipoPresentacion);
        return this;
    }

    public void setTipoPresentacion(String tipoPresentacion) {
        this.tipoPresentacion = tipoPresentacion;
    }

    public Set<Producto> getProductoses() {
        return this.productoses;
    }

    public void setProductoses(Set<Producto> productos) {
        if (this.productoses != null) {
            this.productoses.forEach(i -> i.setPresentacion(null));
        }
        if (productos != null) {
            productos.forEach(i -> i.setPresentacion(this));
        }
        this.productoses = productos;
    }

    public Presentacion productoses(Set<Producto> productos) {
        this.setProductoses(productos);
        return this;
    }

    public Presentacion addProductos(Producto producto) {
        this.productoses.add(producto);
        producto.setPresentacion(this);
        return this;
    }

    public Presentacion removeProductos(Producto producto) {
        this.productoses.remove(producto);
        producto.setPresentacion(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Presentacion)) {
            return false;
        }
        return getId() != null && getId().equals(((Presentacion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Presentacion{" +
            "id=" + getId() +
            ", tipoPresentacion='" + getTipoPresentacion() + "'" +
            "}";
    }
}
