package co.com.mycompany.dominio;

import java.time.LocalDate;
import java.util.Set;

public class Codigo {
    private int id;
    private LocalDate fechaRemision;
    private String codigo;

    private Estado estado;
    private CuentaSitio cuentaSitio;

    private Set<Remision> remisiones;
    private Set<Traslado> traslados;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public LocalDate getFechaRemision(){
        return fechaRemision;
    }

    public void setFechaRemision(LocalDate fechaRemision){
        this.fechaRemision = fechaRemision;
    }

    public String getCodigo(){
        return codigo;
    }

    public void setCodigo(String codigo){
        this.codigo = codigo;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

    public CuentaSitio getCuentaSitio(){
        return cuentaSitio;
    }

    public void setCuentaSitio(CuentaSitio cuentaSitio){
        this.cuentaSitio = cuentaSitio;
    }

    public Set<Remision> getRemisiones(){
        return remisiones;
    }

    public void setRemisiones(Set<Remision> remisiones){
        this.remisiones = remisiones;
    }

    public Set<Traslado> getTraslados(){
        return traslados;
    }

    public void setTraslados(Set<Traslado> traslados){
        this.traslados = traslados;
    }
}
