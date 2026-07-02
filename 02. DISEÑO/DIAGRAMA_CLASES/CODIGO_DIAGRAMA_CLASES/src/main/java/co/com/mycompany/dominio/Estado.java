package co.com.mycompany.dominio;

import java.util.Set;

public class Estado {
    private int id;
    private String nombre;

    private Set<Codigo> codigos;

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

    public Set<Codigo> getCodigos(){
        return codigos;
    }

    public void setCodigos(Set<Codigo> codigos){
        this.codigos = codigos;
    }
}
