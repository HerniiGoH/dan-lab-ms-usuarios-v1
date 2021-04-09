package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model;

public class Usuario {
    private Integer id;
    private String user;
    private String password;
    private TipoUsuario tipoUsuario;

    public Usuario(Integer id, String user, String password, TipoUsuario tipoUsuario) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
