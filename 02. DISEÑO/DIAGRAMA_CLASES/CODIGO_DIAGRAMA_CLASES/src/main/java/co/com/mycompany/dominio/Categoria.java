package co.com.mycompany.dominio;

import java.util.Set;

public class Categoria {
    private int id;
    private String nombre;
    private Set<Producto> productos;

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

    public Set<Producto> getProductos(){
        return productos;
    }

    public void setProductos(Set<Producto> productos){
        this.productos = productos;
    }
}
