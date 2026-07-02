package co.com.mycompany.dominio;

import java.util.Set;

public class Cuenta {
    private int id;
    private String numeroDocumento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String numeroCelular;
    private String eps;
    private String contactoEmergencia;

    private Usuario usuario;
    private TipoDocumento tipoDocumento;
    private Set<HistorialError> historialError;
    private Set<EvidenciaSalida> evidenciaSalidas;
    private Set<CuentaSitio> cuentaSitios;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNumeroDocumento(){
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento){
        this.numeroDocumento = numeroDocumento;
    }

    public String getPrimerNombre(){
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre){
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre(){
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre){
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido(){
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido){
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido(){
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido){
        this.segundoApellido = segundoApellido;
    }

    public String getNumeroCelular(){
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular){
        this.numeroCelular = numeroCelular;
    }

    public String getEps(){
        return eps;
    }

    public void setEps(String eps){
        this.eps = eps;
    }

    public String getContactoEmergencia(){
        return contactoEmergencia;
    }

    public void setContactoEmergencia(String contactoEmergencia){
        this.contactoEmergencia = contactoEmergencia;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public TipoDocumento getTipoDocumento(){
        return tipoDocumento;
    }

    public void setTipoDocumento(TipoDocumento tipoDocumento){
        this.tipoDocumento = tipoDocumento;
    }

    public Set<HistorialError> getHistorialError(){
        return historialError;
    }

    public void setHistorialError(Set<HistorialError> historialError){
        this.historialError = historialError;
    }

    public Set<EvidenciaSalida> getEvidenciaSalidas(){
        return evidenciaSalidas;
    }

    public void setEvidenciaSalidas(Set<EvidenciaSalida> evidenciaSalidas){
        this.evidenciaSalidas = evidenciaSalidas;
    }

    public Set<CuentaSitio> getCuentaSitios(){
        return cuentaSitios;
    }

    public void setCuentaSitios(Set<CuentaSitio> cuentaSitios){
        this.cuentaSitios = cuentaSitios;
    }
}
