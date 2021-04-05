package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.logic.entities;

public class TipoObra {
    private Integer id;
    private String descripcion;

    public TipoObra(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public TipoObra() {
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
}
