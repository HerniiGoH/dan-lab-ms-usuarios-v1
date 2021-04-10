package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findAll();
    Optional<Usuario> findById(Integer integer);
    Optional<Usuario> findDistinctByUser(String user);
}
