package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service;

import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Obra;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.TipoObra;

import java.util.List;
import java.util.Optional;

public interface ObraService {
    List<Obra> buscarTodas();
    Optional<Obra> buscarObraPorId(Integer id);
    Optional<List<Obra>> buscarObra(Integer id, String descripcion, Float latitud, Float longitud, String direccion, Integer superficie, TipoObra tipoObra, Cliente cliente);
    Obra crearObra(Obra nuevaObra);
    List<Obra> crearObras(List<Obra> nuevasObras);
    Optional<Obra> actualizarObra(Obra nuevaObra, Integer id);
    Boolean borrarObra(Integer id);
}
