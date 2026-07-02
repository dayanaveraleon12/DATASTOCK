package co.com.mycompany.dominio;

public class TrasladoProductoSitioLote {
    private int id;
    private int cantidadTraslado;

    private Traslado traslado;
    private ProductoSitio productoSitio;
    private Lote lote;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getCantidadTraslado(){
        return cantidadTraslado;
    }

    public void setCantidadTraslado(int cantidadTraslado){
        this.cantidadTraslado = cantidadTraslado;
    }

    public Traslado getTraslado(){
        return traslado;
    }

    public void setTraslado(Traslado traslado){
        this.traslado = traslado;
    }

    public ProductoSitio getProductoSitio(){
        return productoSitio;
    }

    public void setProductoSitio(ProductoSitio productoSitio){
        this.productoSitio = productoSitio;
    }

    public Lote getLote(){
        return lote;
    }

    public void setLote(Lote lote){
        this.lote = lote;
    }
}
