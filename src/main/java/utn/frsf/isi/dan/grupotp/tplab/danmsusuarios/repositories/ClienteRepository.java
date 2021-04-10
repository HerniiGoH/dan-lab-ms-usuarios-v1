package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findAll();
    Optional<Cliente> findById(Integer integer);
    @Query(value = "SELECT c FROM Cliente c WHERE (:id is null or c.id = :id) and (:mail is null or c.mail = :mail) and (:cuit is null or c.cuit = :cuit) and (:razonSocial is null or c.razonSocial = :razonSocial) and (:maxCuentaCorriente is null or c.maxCuentaCorriente=:maxCuentaCorriente) and (:habilitadoOnline is null or c.habilitadoOnline = :habilitadoOnline)")
    Optional<List<Cliente>> findAllByQuerySQL(Integer id, String cuit, String razonSocial, String mail, Double maxCuentaCorriente, Boolean habilitadoOnline);
}
