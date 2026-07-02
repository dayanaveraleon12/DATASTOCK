package co.com.mycompany.dominio;

import java.util.Set;

public class MotivoSalida {

    private int id;
    private String tipoMotivo;
    private Set<Salida> salidas;

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getTipoMotivo() {
        return tipoMotivo;
    }

    public void setTipoMotivo(String tipoMotivo){
        this.tipoMotivo=tipoMotivo;
    }

    public Set<Salida> getSalidas(){
        return salidas;
    }

    public void setSalidas(Set<Salida> salidas){
        this.salidas = salidas;
    }
}