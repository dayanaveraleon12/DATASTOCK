package co.com.mycompany.dominio;

import java.util.Set;

public class ProductoSitio {

    private int id;
    private int cantidadSitio;

    private Producto producto;

    private Set<ProductoSitioSalida> productoSitioSalidas;

    private Set<TrasladoProductoSitioLote> trasladoProductoSitioLotes;

    private Set<Remision> remisiones;

    private Lote lote;

    private Sitio sitio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidadSitio() {
        return cantidadSitio;
    }

    public void setCantidadSitio(int cantidadSitio) {
        this.cantidadSitio = cantidadSitio;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Set<ProductoSitioSalida> getProductoSitioSalidas() {
        return productoSitioSalidas;
    }

    public void setProductoSitioSalidas(Set<ProductoSitioSalida> productoSitioSalidas) {
        this.productoSitioSalidas = productoSitioSalidas;
    }

    public Lote getLote(){
        return lote;
    }

    public void setLote(Lote lote){
        this.lote = lote;
    }

    public Sitio getSitio(){
        return sitio;
    }

    public void setSitio(Sitio sitio){
        this.sitio = sitio;
    }

    public Set<Remision> getRemisiones(){
        return remisiones;
    }

    public void setRemisiones(Set<Remision> remisiones){
        this.remisiones = remisiones;
    }

    public Set<TrasladoProductoSitioLote> getTrasladoProductoSitioLotes(){
        return trasladoProductoSitioLotes;
    }

    public void setTrasladoProductoSitioLotes(Set<TrasladoProductoSitioLote> trasladoProductoSitioLotes){
        this.trasladoProductoSitioLotes = trasladoProductoSitioLotes;
    }
}