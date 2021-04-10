package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Empleado;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    List<Empleado> findAll();
    Optional<Empleado> findById(Integer integer);
    Optional<List<Empleado>> findAllByIdAndRazonSocialAndMail(Integer id, String razonSocial, String mail);
}
