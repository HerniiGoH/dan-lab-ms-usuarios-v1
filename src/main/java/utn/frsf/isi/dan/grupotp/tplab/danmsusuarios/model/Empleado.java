package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model;

import javax.persistence.*;

@Entity
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String mail;
    private String razonSocial;
    @OneToOne
    private Usuario usuario;

    public Empleado(Integer id, String mail, String razonSocial, Usuario usuario) {
        this.id = id;
        this.mail = mail;
        this.razonSocial = razonSocial;
        this.usuario = usuario;
    }

    public Empleado() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
