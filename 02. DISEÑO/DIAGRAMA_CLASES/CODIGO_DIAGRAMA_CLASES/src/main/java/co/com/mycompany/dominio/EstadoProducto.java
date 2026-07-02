package co.com.mycompany.dominio;

import java.util.Set;

public class EstadoProducto {
    private int id;
    private String tipoEstado;

    private Set<Producto> productos;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTipoEstado(){
        return tipoEstado;
    }

    public void setTipoEstado(String tipoEstado){
        this.tipoEstado = tipoEstado;
    }

    public Set<Producto> getProductos(){
        return productos;
    }

    public void setProductos(Set<Producto> productos){
        this.productos = productos;
    }
}
