package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Obra;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.TipoObra;

import java.util.List;
import java.util.Optional;

public interface ObraRepository extends JpaRepository<Obra, Integer> {
    List<Obra> findAll();
    Optional<Obra> findById(Integer integer);
    Optional<List<Obra>> findAllByIdAndDescripcionAndLatitudAndLongitudAndDireccionAndSuperficieAndTipoObraAndCliente(Integer id, String description, Float latitud, Float longitud, String direccion, Integer superficie, TipoObra tipoObra, Cliente cliente);
}
