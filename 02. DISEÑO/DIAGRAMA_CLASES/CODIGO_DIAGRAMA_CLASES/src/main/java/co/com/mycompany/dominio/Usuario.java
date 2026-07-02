package co.com.mycompany.dominio;

import java.util.Set;

public class Usuario {
    private int id;
    private String correoElectronico;
    private String contrasenia;
    private int tokenCambioContrasenia;
    private Cuenta cuenta;
    private Set<Autorizacion> autorizacion;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCorreoElectronico(){
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico){
        this.correoElectronico = correoElectronico;
    }

    public String getContrasenia(){
        return contrasenia;
    }

    public void setContrasenia(String contrasenia){
        this.contrasenia = contrasenia;
    }

    public int getTokenCambioContrasenia(){
        return tokenCambioContrasenia;
    }

    public void setTokenCambioContrasenia(int tokenCambioContrasenia){
        this.tokenCambioContrasenia = tokenCambioContrasenia;
    }

    public Cuenta getCuenta(){
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta){
        this.cuenta = cuenta;
    }

    public Set<Autorizacion> getAutorizacion(){
        return autorizacion;
    }

    public void setAutorizacion(Set<Autorizacion> autorizacion){
        this.autorizacion = autorizacion;
    }
}
