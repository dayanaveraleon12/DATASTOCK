package co.com.mycompany.dominio;

import java.util.Set;

public class TipoDocumento {

    private int id;
    private String tipoDocumento;
    private Set<Cuenta> cuentas;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTipoDocumento(){
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento){
        this.tipoDocumento = tipoDocumento;
    }

    public Set<Cuenta> getCuentas(){
        return cuentas;
    }

    public void setCuentas(Set<Cuenta> cuentas){
        this.cuentas = cuentas;
    }
}
