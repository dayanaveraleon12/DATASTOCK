package co.com.mycompany.dominio;

import java.util.Set;

public class Traslado {
    private int id;
    private String descripcion;

    private CuentaSitio cuentaSitio;
    private Sitio sitio; //Sitio destino
    private Codigo codigo;

    private Set<TrasladoProductoSitioLote> trasladoProductoSitioLotes;


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public CuentaSitio getCuentaSitio(){
        return cuentaSitio;
    }

    public void setCuentaSitio(CuentaSitio cuentaSitio){
        this.cuentaSitio = cuentaSitio;
    }

    public Sitio getSitio(){
        return sitio; //sitio destino
    }

    public void setSitio(Sitio sitio){
        this.sitio = sitio;
    }

    public Codigo getCodigo(){
        return codigo;
    }

    public void setCodigo(Codigo codigo){
        this.codigo = codigo;
    }

    public Set<TrasladoProductoSitioLote> getTrasladoProductoSitioLotes(){
        return trasladoProductoSitioLotes;
    }

    public void setTrasladoProductoSitioLotes(Set<TrasladoProductoSitioLote> trasladoProductoSitioLotes){
        this.trasladoProductoSitioLotes = trasladoProductoSitioLotes;
    }
}
