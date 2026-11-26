package com.mycompany.datastock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cuenta.
 */
@Entity
@Table(name = "cuenta")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cuenta implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "numero_documento", length = 10, nullable = false, unique = true)
    private String numeroDocumento;

    @NotNull
    @Size(max = 20)
    @Column(name = "primer_nombre", length = 20, nullable = false)
    private String primerNombre;

    @Size(max = 20)
    @Column(name = "segundo_nombre", length = 20)
    private String segundoNombre;

    @NotNull
    @Size(max = 20)
    @Column(name = "primer_apellido", length = 20, nullable = false)
    private String primerApellido;

    @Size(max = 20)
    @Column(name = "segundo_apellido", length = 20)
    private String segundoApellido;

    @NotNull
    @Size(max = 20)
    @Column(name = "numero_celular", length = 20, nullable = false)
    private String numeroCelular;

    @NotNull
    @Size(max = 30)
    @Column(name = "eps", length = 30, nullable = false)
    private String eps;

    @NotNull
    @Size(max = 20)
    @Column(name = "contacto_emergencia", length = 20, nullable = false)
    private String contactoEmergencia;

    @NotNull
    @Size(max = 20)
    @Column(name = "parentezco_emergencia", length = 20, nullable = false)
    private String parentezcoEmergencia;

    @NotNull
    @Size(max = 20)
    @Column(name = "nombre_emergencia", length = 20, nullable = false)
    private String nombreEmergencia;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "cuentases" }, allowSetters = true)
    private TipoDocumento tipoDocumento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuenta")
    @JsonIgnoreProperties(value = { "cuenta" }, allowSetters = true)
    private Set<HistorialError> historialErroreses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuenta")
    @JsonIgnoreProperties(value = { "cuenta", "sitio", "codigoses", "trasladoses", "salidases" }, allowSetters = true)
    private Set<CuentaSitio> cuentaSitioses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuenta")
    @JsonIgnoreProperties(value = { "cuenta", "sitioSubida", "salidases" }, allowSetters = true)
    private Set<EvidenciaSalida> evidenciaSalidases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cuenta id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }

    public Cuenta numeroDocumento(String numeroDocumento) {
        this.setNumeroDocumento(numeroDocumento);
        return this;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getPrimerNombre() {
        return this.primerNombre;
    }

    public Cuenta primerNombre(String primerNombre) {
        this.setPrimerNombre(primerNombre);
        return this;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return this.segundoNombre;
    }

    public Cuenta segundoNombre(String segundoNombre) {
        this.setSegundoNombre(segundoNombre);
        return this;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public Cuenta primerApellido(String primerApellido) {
        this.setPrimerApellido(primerApellido);
        return this;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public Cuenta segundoApellido(String segundoApellido) {
        this.setSegundoApellido(segundoApellido);
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNumeroCelular() {
        return this.numeroCelular;
    }

    public Cuenta numeroCelular(String numeroCelular) {
        this.setNumeroCelular(numeroCelular);
        return this;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getEps() {
        return this.eps;
    }

    public Cuenta eps(String eps) {
        this.setEps(eps);
        return this;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getContactoEmergencia() {
        return this.contactoEmergencia;
    }

    public Cuenta contactoEmergencia(String contactoEmergencia) {
        this.setContactoEmergencia(contactoEmergencia);
        return this;
    }

    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public String getParentezcoEmergencia() {
        return this.parentezcoEmergencia;
    }

    public Cuenta parentezcoEmergencia(String parentezcoEmergencia) {
        this.setParentezcoEmergencia(parentezcoEmergencia);
        return this;
    }

    public void setParentezcoEmergencia(String parentezcoEmergencia) {
        this.parentezcoEmergencia = parentezcoEmergencia;
    }

    public String getNombreEmergencia() {
        return this.nombreEmergencia;
    }

    public Cuenta nombreEmergencia(String nombreEmergencia) {
        this.setNombreEmergencia(nombreEmergencia);
        return this;
    }

    public void setNombreEmergencia(String nombreEmergencia) {
        this.nombreEmergencia = nombreEmergencia;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cuenta user(User user) {
        this.setUser(user);
        return this;
    }

    public TipoDocumento getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Cuenta tipoDocumento(TipoDocumento tipoDocumento) {
        this.setTipoDocumento(tipoDocumento);
        return this;
    }

    public Set<HistorialError> getHistorialErroreses() {
        return this.historialErroreses;
    }

    public void setHistorialErroreses(Set<HistorialError> historialErrors) {
        if (this.historialErroreses != null) {
            this.historialErroreses.forEach(i -> i.setCuenta(null));
        }
        if (historialErrors != null) {
            historialErrors.forEach(i -> i.setCuenta(this));
        }
        this.historialErroreses = historialErrors;
    }

    public Cuenta historialErroreses(Set<HistorialError> historialErrors) {
        this.setHistorialErroreses(historialErrors);
        return this;
    }

    public Cuenta addHistorialErrores(HistorialError historialError) {
        this.historialErroreses.add(historialError);
        historialError.setCuenta(this);
        return this;
    }

    public Cuenta removeHistorialErrores(HistorialError historialError) {
        this.historialErroreses.remove(historialError);
        historialError.setCuenta(null);
        return this;
    }

    public Set<CuentaSitio> getCuentaSitioses() {
        return this.cuentaSitioses;
    }

    public void setCuentaSitioses(Set<CuentaSitio> cuentaSitios) {
        if (this.cuentaSitioses != null) {
            this.cuentaSitioses.forEach(i -> i.setCuenta(null));
        }
        if (cuentaSitios != null) {
            cuentaSitios.forEach(i -> i.setCuenta(this));
        }
        this.cuentaSitioses = cuentaSitios;
    }

    public Cuenta cuentaSitioses(Set<CuentaSitio> cuentaSitios) {
        this.setCuentaSitioses(cuentaSitios);
        return this;
    }

    public Cuenta addCuentaSitios(CuentaSitio cuentaSitio) {
        this.cuentaSitioses.add(cuentaSitio);
        cuentaSitio.setCuenta(this);
        return this;
    }

    public Cuenta removeCuentaSitios(CuentaSitio cuentaSitio) {
        this.cuentaSitioses.remove(cuentaSitio);
        cuentaSitio.setCuenta(null);
        return this;
    }

    public Set<EvidenciaSalida> getEvidenciaSalidases() {
        return this.evidenciaSalidases;
    }

    public void setEvidenciaSalidases(Set<EvidenciaSalida> evidenciaSalidas) {
        if (this.evidenciaSalidases != null) {
            this.evidenciaSalidases.forEach(i -> i.setCuenta(null));
        }
        if (evidenciaSalidas != null) {
            evidenciaSalidas.forEach(i -> i.setCuenta(this));
        }
        this.evidenciaSalidases = evidenciaSalidas;
    }

    public Cuenta evidenciaSalidases(Set<EvidenciaSalida> evidenciaSalidas) {
        this.setEvidenciaSalidases(evidenciaSalidas);
        return this;
    }

    public Cuenta addEvidenciaSalidas(EvidenciaSalida evidenciaSalida) {
        this.evidenciaSalidases.add(evidenciaSalida);
        evidenciaSalida.setCuenta(this);
        return this;
    }

    public Cuenta removeEvidenciaSalidas(EvidenciaSalida evidenciaSalida) {
        this.evidenciaSalidases.remove(evidenciaSalida);
        evidenciaSalida.setCuenta(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cuenta)) {
            return false;
        }
        return getId() != null && getId().equals(((Cuenta) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cuenta{" +
            "id=" + getId() +
            ", numeroDocumento='" + getNumeroDocumento() + "'" +
            ", primerNombre='" + getPrimerNombre() + "'" +
            ", segundoNombre='" + getSegundoNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", numeroCelular='" + getNumeroCelular() + "'" +
            ", eps='" + getEps() + "'" +
            ", contactoEmergencia='" + getContactoEmergencia() + "'" +
            ", parentezcoEmergencia='" + getParentezcoEmergencia() + "'" +
            ", nombreEmergencia='" + getNombreEmergencia() + "'" +
            "}";
    }
}
