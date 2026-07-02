package co.com.mycompany.dominio;

import java.time.LocalDate;

public class EvidenciaSalida {
    private int id;
    private String archivoExcel;
    private String nombreExcel;
    private LocalDate fechaCarga;

    private Cuenta cuenta;
    //Sitio hace referencia al sitio que pueda descargar el documento
    private Sitio sitio;
    private Salida salidas;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getArchivoExcel(){
        return archivoExcel;
    }

    public void setArchivoExcel(String archivoExcel){
        this.archivoExcel = archivoExcel;
    }

    public String getNombreExcel(){
        return nombreExcel;
    }

    public void setNombreExcel(String nombreExcel){
        this.nombreExcel = nombreExcel;
    }

    public LocalDate getFechaCarga(){
        return fechaCarga;
    }

    public void setFechaCarga(LocalDate fechaCarga){
        this.fechaCarga = fechaCarga;
    }

    public Cuenta getCuenta(){
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta){
        this.cuenta = cuenta;
    }

    public Salida getSalidas(){
        return salidas;
    }

    public void setSalidas(Salida salidas){
        this.salidas = salidas;
    }

    public Sitio getSitio(){
        return sitio;
    }

    public void setSitio(Sitio sitio){
        this.sitio = sitio;
    }
}
