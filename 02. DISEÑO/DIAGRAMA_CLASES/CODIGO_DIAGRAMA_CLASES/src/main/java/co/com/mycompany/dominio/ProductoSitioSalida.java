package co.com.mycompany.dominio;

public class ProductoSitioSalida {

    private int id;

    private ProductoSitio productoSitio;

    private Salida salida;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductoSitio getProductoSitio() {
        return productoSitio;
    }

    public void setProductoSitio(ProductoSitio productoSitio) {
        this.productoSitio = productoSitio;
    }

    public Salida getSalida(){
        return salida;
    }

    public void setSalida(Salida salida){
        this.salida = salida;
    }
}