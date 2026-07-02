package co.com.mycompany.dominio;

import java.util.Set;

public class Sitio {
    private int id;
    private String nombre;

    private Set<ProductoSitio> productoSitios;
    private Set<CuentaSitio> cuentaSitios;
    private Set<EvidenciaSalida> evidenciaSalidas;
    private Set<Traslado> traslados;
    private Set<Remision> remisiones;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public Set<ProductoSitio> getProductoSitios(){
        return productoSitios;
    }

    public void setProductoSitios(Set<ProductoSitio> productoSitios){
        this.productoSitios = productoSitios;
    }

    public Set<CuentaSitio> getCuentaSitios(){
        return cuentaSitios;
    }

    public void setCuentaSitios(Set<CuentaSitio> cuentaSitios){
        this.cuentaSitios = cuentaSitios;
    }

    public Set<EvidenciaSalida> getEvidenciaSalidas(){
        return evidenciaSalidas;
    }

    public void setEvidenciaSalidas(Set<EvidenciaSalida> evidenciaSalidas){
        this.evidenciaSalidas = evidenciaSalidas;
    }

    public Set<Traslado> getTraslados(){
        return traslados;
    }

    public void setTraslados(Set<Traslado> traslados){
        this.traslados = traslados;
    }

    public Set<Remision> getRemisiones(){
        return remisiones;
    }

    public void setRemisiones(Set<Remision> remisiones){
        this.remisiones = remisiones;
    }
}
