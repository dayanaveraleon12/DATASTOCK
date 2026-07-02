package co.com.mycompany.dominio;

import java.util.Set;

public class Rol {
    private int id;
    private String nombre;
    private Set<Autorizacion> autorizacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Autorizacion> getAutorizacion(){
        return autorizacion;
    }

    public void setAutorizacion(Set<Autorizacion> autorizacion){
        this.autorizacion = autorizacion;
    }
}
