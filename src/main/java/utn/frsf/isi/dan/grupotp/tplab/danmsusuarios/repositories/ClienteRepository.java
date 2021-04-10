package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findAll();
    Optional<Cliente> findById(Integer integer);
    Optional<List<Cliente>> findAllByIdAndCuitAndRazonSocialAndMailAndMaxCuentaCorrienteAndHabilitadoOnline(Integer id, String cuit, String razonSocial, String mail, Double maxCuentaCorriente, Boolean habilitadoOnline);
}
