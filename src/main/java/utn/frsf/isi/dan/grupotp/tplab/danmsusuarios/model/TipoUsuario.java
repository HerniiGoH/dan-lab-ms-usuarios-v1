package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model;

public class TipoUsuario {
    private Integer id;
    private String tipo;

    public TipoUsuario(Integer id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public TipoUsuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
