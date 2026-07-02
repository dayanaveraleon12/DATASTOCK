package co.com.mycompany.dominio;

import java.time.LocalDateTime;

public class HistorialError {

    private int id;
    private String nombreError;
    private String mensaje;
    private LocalDateTime fechaHora;

    private Cuenta cuenta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreError() {
        return nombreError;
    }

    public void setNombreError(String nombreError) {
        this.nombreError = nombreError;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

}

