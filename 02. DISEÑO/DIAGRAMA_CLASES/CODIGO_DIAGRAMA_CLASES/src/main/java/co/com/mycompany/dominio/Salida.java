package co.com.mycompany.dominio;

import java.util.Set;

public class Salida {
    private int id;
    private String respaldoImagen;
    private int cantidadSacar;
    private String observacion;

    private CuentaSitio cuentaSitio;
    private MotivoSalida motivoSalida;
    private EvidenciaSalida evidenciaSalida;
    private Lote lote;
    private Set<ProductoSitioSalida> productoSitioSalidas;
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRespaldoImagen() {
        return respaldoImagen;
    }

    public void setRespaldoImagen(String respaldoImagen) {
        this.respaldoImagen = respaldoImagen;
    }

    public int getCantidadSacar() {
        return cantidadSacar;
    }

    public void setCantidadSacar(int cantidadSacar) {
        this.cantidadSacar = cantidadSacar;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public CuentaSitio getCuentaSitio(){
        return cuentaSitio;
    }

    public void setCuentaSitio(CuentaSitio cuentaSitio){
        this.cuentaSitio = cuentaSitio;
    }

    public MotivoSalida getMotivoSalida(){
        return motivoSalida;
    }

    public void setMotivoSalida(MotivoSalida motivoSalida ){
        this.motivoSalida = motivoSalida;
    }

    public EvidenciaSalida getEvidenciaSalida(){
        return evidenciaSalida;
    }

    public void setEvidenciaSalida(EvidenciaSalida evidenciaSalida){
        this.evidenciaSalida = evidenciaSalida;
    }

    public Lote getLote(){
        return lote;
    }

    public void setLote(Lote lote){
        this.lote = lote;
    }

    public Set<ProductoSitioSalida> getpProductoSitioSalidas(){
        return productoSitioSalidas;
    }

    public void setProductoSitioSalidas(Set<ProductoSitioSalida> productoSitioSalidas){
        this.productoSitioSalidas = productoSitioSalidas;
    }
}
