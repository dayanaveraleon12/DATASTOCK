package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.datastock.domain.enumeration.Estado;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Marca.
 */
@Entity
@Table(name = "marca")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Marca implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "marca", length = 50, nullable = false, unique = true)
    private String marca;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_marca", nullable = false)
    private Estado estadoMarca;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "marca")
    @JsonIgnoreProperties(value = { "categoria", "presentacion", "marca", "loteses", "productoSitioses" }, allowSetters = true)
    private Set<Producto> productoses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Marca id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return this.marca;
    }

    public Marca marca(String marca) {
        this.setMarca(marca);
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Estado getEstadoMarca() {
        return this.estadoMarca;
    }

    public Marca estadoMarca(Estado estadoMarca) {
        this.setEstadoMarca(estadoMarca);
        return this;
    }

    public void setEstadoMarca(Estado estadoMarca) {
        this.estadoMarca = estadoMarca;
    }

    public Set<Producto> getProductoses() {
        return this.productoses;
    }

    public void setProductoses(Set<Producto> productos) {
        if (this.productoses != null) {
            this.productoses.forEach(i -> i.setMarca(null));
        }
        if (productos != null) {
            productos.forEach(i -> i.setMarca(this));
        }
        this.productoses = productos;
    }

    public Marca productoses(Set<Producto> productos) {
        this.setProductoses(productos);
        return this;
    }

    public Marca addProductos(Producto producto) {
        this.productoses.add(producto);
        producto.setMarca(this);
        return this;
    }

    public Marca removeProductos(Producto producto) {
        this.productoses.remove(producto);
        producto.setMarca(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Marca)) {
            return false;
        }
        return getId() != null && getId().equals(((Marca) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Marca{" +
            "id=" + getId() +
            ", marca='" + getMarca() + "'" +
            ", estadoMarca='" + getEstadoMarca() + "'" +
            "}";
    }
}
