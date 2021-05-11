package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model;

import com.fasterxml.jackson.annotation.*;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.enumerations.RiesgoBCRA;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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
    @JsonIdentityReference
    @OneToMany(mappedBy = "cliente")
    private List<Obra> obras;
    private RiesgoBCRA riesgoBCRA;
    private Date fechaBaja;

    public Cliente(Integer id, String razonSocial, String cuit, String mail, Double maxCuentaCorriente, Boolean habilitadoOnline, Usuario usuario, List<Obra> obras, RiesgoBCRA riesgoBCRA, Date fechaBaja) {
        this.id = id;
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.mail = mail;
        this.maxCuentaCorriente = maxCuentaCorriente;
        this.habilitadoOnline = habilitadoOnline;
        this.usuario = usuario;
        this.obras = obras;
        this.riesgoBCRA=riesgoBCRA;
        this.fechaBaja=fechaBaja;
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

    public RiesgoBCRA getRiesgoBCRA() {
        return riesgoBCRA;
    }

    public void setRiesgoBCRA(RiesgoBCRA riesgoBCRA) {
        this.riesgoBCRA = riesgoBCRA;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return getId().equals(cliente.getId()) && getRazonSocial().equals(cliente.getRazonSocial()) && getCuit().equals(cliente.getCuit()) && getMail().equals(cliente.getMail()) && getMaxCuentaCorriente().equals(cliente.getMaxCuentaCorriente()) && getHabilitadoOnline().equals(cliente.getHabilitadoOnline()) && getUsuario().equals(cliente.getUsuario()) && getObras().equals(cliente.getObras()) && getRiesgoBCRA() == cliente.getRiesgoBCRA() && Objects.equals(getFechaBaja(), cliente.getFechaBaja());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRazonSocial(), getCuit(), getMail(), getMaxCuentaCorriente(), getHabilitadoOnline(), getUsuario(), getObras(), getRiesgoBCRA(), getFechaBaja());
    }
}
