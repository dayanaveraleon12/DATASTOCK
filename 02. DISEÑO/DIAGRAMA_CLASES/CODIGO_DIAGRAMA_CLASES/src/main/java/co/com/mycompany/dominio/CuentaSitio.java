package co.com.mycompany.dominio;

import java.util.Set;

public class CuentaSitio {
    private int id;

    private Cuenta cuenta;
    private Sitio sitio;
    private Set<Traslado> traslados;
    private Set<Codigo> codigos;
    private Set<Salida> salidas;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Cuenta getCuenta(){
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta){
        this.cuenta = cuenta;
    }

    public Sitio getSitio(){
        return sitio;

    }

    public void setSitio(Sitio sitio){
        this.sitio = sitio;
    }

    public Set<Traslado> getTraslados(){
        return traslados;
    }

    public void setTraslados(Set<Traslado> traslados){
        this.traslados = traslados;
    }

    public Set<Codigo> getCodigos(){
        return codigos;
    }

    public void setCodigos(Set<Codigo> codigos){
        this.codigos = codigos;
    }

    public Set<Salida> getSalidas(){
        return salidas;
    }

    public void setSalidas(Set<Salida> salidas){
        this.salidas = salidas;
    }
}
