package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model;

public class JsonWrapper {
    private Cliente cliente;
    private TipoObra tipoObra;

    public JsonWrapper() {
    }

    public JsonWrapper(Cliente cliente, TipoObra tipoObra) {
        this.cliente = cliente;
        this.tipoObra = tipoObra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoObra getTipoObra() {
        return tipoObra;
    }

    public void setTipoObra(TipoObra tipoObra) {
        this.tipoObra = tipoObra;
    }

    @Override
    public String toString() {
        return "JsonWrapper{" +
                "cliente=" + cliente +
                ", tipoObra=" + tipoObra +
                '}';
    }
}
