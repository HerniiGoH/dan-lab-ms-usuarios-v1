package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.logic.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class Obra {
    private Integer id;
    private String descripcion;
    private Float latitud;
    private Float longitud;
    private String direccion;
    private Integer superficie;
    private TipoObra TipoipoObra;
    @JsonManagedReference
    private Cliente cliente;

    public Obra(Integer id, String descripcion, Float latitud, Float longitud, String direccion, Integer superficie, TipoObra tipoipoObra, Cliente cliente) {
        this.id = id;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.superficie = superficie;
        TipoipoObra = tipoipoObra;
        this.cliente = cliente;
    }

    public Obra() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getSuperficie() {
        return superficie;
    }

    public void setSuperficie(Integer superficie) {
        this.superficie = superficie;
    }

    public TipoObra getTipoipoObra() {
        return TipoipoObra;
    }

    public void setTipoipoObra(TipoObra tipoipoObra) {
        TipoipoObra = tipoipoObra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
