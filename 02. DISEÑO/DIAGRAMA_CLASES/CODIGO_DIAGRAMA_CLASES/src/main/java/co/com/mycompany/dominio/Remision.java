package co.com.mycompany.dominio;

public class Remision {
    private int id;
    private int cantidadRemitida;

    private ProductoSitio productoSitio;
    private Lote lote;
    private Codigo codigo;
    private Sitio sitio; //SitioDestino


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidadRemitida() {
        return cantidadRemitida;
    }

    public void setCantidadRemitida(int cantidadRemitida) {
        this.cantidadRemitida = cantidadRemitida;
    }

    public ProductoSitio getProductoSitio() {
        return productoSitio;
    }

    public void setProductoSitio(ProductoSitio productoSitio) {
        this.productoSitio = productoSitio;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public Codigo getCodigo() {
        return codigo;
    }

    public void setCodigo(Codigo codigo) {
        this.codigo = codigo;
    }

    public Sitio getSitio() {
        return sitio;
    }

    public void setSitio(Sitio sitio) {
        this.sitio = sitio;
    }
}
