package co.com.mycompany.dominio;

import java.util.Set;

public class Producto {

    private int id;
    private String nombreEspecificacion;

    private Categoria categoria;
    private Presentacion presentacion;
    private EstadoProducto estadoProducto;
    private Marca marca;

    private Set<Lote> lotes;
    private Set<ProductoSitio> productoSitios;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreEspecificacion() {
        return nombreEspecificacion;
    }

    public void setNombreEspecificacion(String nombreEspecificacion) {
        this.nombreEspecificacion = nombreEspecificacion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Presentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }

    public EstadoProducto getEstadoProducto() {
        return estadoProducto;
    }

    public void setEstadoProducto(EstadoProducto estadoProducto) {
        this.estadoProducto = estadoProducto;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Set<Lote> getLotes() {
        return lotes;
    }

    public void setLotes(Set<Lote> lotes) {
        this.lotes = lotes;
    }

    public Set<ProductoSitio> getProductoSitios() {
        return productoSitios;
    }

    public void setProductoSitios(Set<ProductoSitio> productoSitios) {
        this.productoSitios = productoSitios;
    }

}