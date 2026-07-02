package co.com.mycompany.dominio;

import java.time.LocalDate;
import java.util.Set;

public class Lote {

    private int id;
    private LocalDate fechaIngreso;
    private LocalDate fechaVencimiento;
    private String codigoLote;
    private int descripcion;
    private String facturaProveedor;
    private int cantidadIngresada;

    private Producto producto;
    private Set<ProductoSitio> productoSitios;
    private Set<TrasladoProductoSitioLote> trasladoProductoSitioLotes;
    private Set<Salida> salidas;
    private Set<Remision> remisiones;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }

    public int getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(int descripcion) {
        this.descripcion = descripcion;
    }

    public String getFacturaProveedor() {
        return facturaProveedor;
    }

    public void setFacturaProveedor(String facturaProveedor) {
        this.facturaProveedor = facturaProveedor;
    }

    public int getCantidadIngresada() {
        return cantidadIngresada;
    }

    public void setCantidadIngresada(int cantidadIngresada) {
        this.cantidadIngresada = cantidadIngresada;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Set<ProductoSitio> getProductoSitios(){
        return productoSitios;
    }

    public void setProductoSitios(Set<ProductoSitio> productoSitios){
        this.productoSitios = productoSitios;
    }

    public Set<TrasladoProductoSitioLote> getTrasladoProductoSitioLotes(){
        return trasladoProductoSitioLotes;
    }

    public void setTrasladoProductoSitioLotes(Set<TrasladoProductoSitioLote> trasladoProductoSitioLotes){
        this.trasladoProductoSitioLotes = trasladoProductoSitioLotes;
    }

    public Set<Salida> getSalidas(){
        return salidas;
    }

    public void setSalidas(Set<Salida> salidas){
        this.salidas = salidas;
    }

    public Set<Remision> getRemisiones(){
        return remisiones;
    }

    public void setRemisiones(Set<Remision> remisiones){
        this.remisiones = remisiones;
    }
}