package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Empleado;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    List<Empleado> findAll();
    Optional<Empleado> findById(Integer integer);
    @Query(value = "SELECT e FROM Empleado e where (:id is null or e.id = :id) and (:razonSocial is null or e.razonSocial = :razonSocial) and (:mail is null or e.mail = :mail)")
    Optional<List<Empleado>> findAllByQuery(Integer id, String razonSocial, String mail);
}
