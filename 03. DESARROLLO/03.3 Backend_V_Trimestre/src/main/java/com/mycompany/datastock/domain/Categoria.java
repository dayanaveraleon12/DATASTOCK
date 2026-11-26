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
 * A Categoria.
 */
@Entity
@Table(name = "categoria")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Categoria implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_categoria", nullable = false)
    private Estado estadoCategoria;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria")
    @JsonIgnoreProperties(value = { "categoria", "presentacion", "marca", "loteses", "productoSitioses" }, allowSetters = true)
    private Set<Producto> productoses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Categoria id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Categoria nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Estado getEstadoCategoria() {
        return this.estadoCategoria;
    }

    public Categoria estadoCategoria(Estado estadoCategoria) {
        this.setEstadoCategoria(estadoCategoria);
        return this;
    }

    public void setEstadoCategoria(Estado estadoCategoria) {
        this.estadoCategoria = estadoCategoria;
    }

    public Set<Producto> getProductoses() {
        return this.productoses;
    }

    public void setProductoses(Set<Producto> productos) {
        if (this.productoses != null) {
            this.productoses.forEach(i -> i.setCategoria(null));
        }
        if (productos != null) {
            productos.forEach(i -> i.setCategoria(this));
        }
        this.productoses = productos;
    }

    public Categoria productoses(Set<Producto> productos) {
        this.setProductoses(productos);
        return this;
    }

    public Categoria addProductos(Producto producto) {
        this.productoses.add(producto);
        producto.setCategoria(this);
        return this;
    }

    public Categoria removeProductos(Producto producto) {
        this.productoses.remove(producto);
        producto.setCategoria(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categoria)) {
            return false;
        }
        return getId() != null && getId().equals(((Categoria) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categoria{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", estadoCategoria='" + getEstadoCategoria() + "'" +
            "}";
    }
}
