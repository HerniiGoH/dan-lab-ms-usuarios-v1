package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String razonSocial;
    private String cuit;
    private String mail;
    private Double maxCuentaCorriente;
    private Boolean habilitadoOnline;
    @OneToOne
    private Usuario usuario;
    @JsonManagedReference
    @OneToMany(mappedBy = "cliente")
    private List<Obra> obras;

    public Cliente(Integer id, String razonSocial, String cuit, String mail, Double maxCuentaCorriente, Boolean habilitadoOnline, Usuario usuario, List<Obra> obras) {
        this.id = id;
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.mail = mail;
        this.maxCuentaCorriente = maxCuentaCorriente;
        this.habilitadoOnline = habilitadoOnline;
        this.usuario = usuario;
        this.obras = obras;
    }

    public Cliente() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Double getMaxCuentaCorriente() {
        return maxCuentaCorriente;
    }

    public void setMaxCuentaCorriente(Double maxCuentaCorriente) {
        this.maxCuentaCorriente = maxCuentaCorriente;
    }

    public Boolean getHabilitadoOnline() {
        return habilitadoOnline;
    }

    public void setHabilitadoOnline(Boolean habilitadoOnline) {
        this.habilitadoOnline = habilitadoOnline;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Obra> getObras() {
        return obras;
    }

    public void setObras(List<Obra> obras) {
        this.obras = obras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(getId(), cliente.getId()) && Objects.equals(getRazonSocial(), cliente.getRazonSocial()) && Objects.equals(getCuit(), cliente.getCuit()) && Objects.equals(getMail(), cliente.getMail()) && Objects.equals(getMaxCuentaCorriente(), cliente.getMaxCuentaCorriente()) && Objects.equals(getHabilitadoOnline(), cliente.getHabilitadoOnline()) && Objects.equals(getUsuario(), cliente.getUsuario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRazonSocial(), getCuit(), getMail(), getMaxCuentaCorriente(), getHabilitadoOnline(), getUsuario());
    }
}
