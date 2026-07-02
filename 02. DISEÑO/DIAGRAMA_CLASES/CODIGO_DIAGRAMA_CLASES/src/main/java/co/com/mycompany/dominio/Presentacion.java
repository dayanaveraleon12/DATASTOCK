package co.com.mycompany.dominio;

import java.util.Set;

public class Presentacion {

    private int id;
    private String tipoPresentacion;

    private Set<Producto> productos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id=id;
    }

    public String getTipoPresentacion() {
        return tipoPresentacion;
    }

    public void setTipoPresentacion(String tipoPresentacion) {
        this.tipoPresentacion=tipoPresentacion;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

}