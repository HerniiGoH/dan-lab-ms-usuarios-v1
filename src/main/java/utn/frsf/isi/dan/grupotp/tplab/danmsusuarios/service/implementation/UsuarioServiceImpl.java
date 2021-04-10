package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Usuario;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories.UsuarioRepository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.UsuarioService;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> buscartodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario crearUsuario(Usuario nuevoUsuario) {
        return usuarioRepository.save(nuevoUsuario);
    }

    @Override
    public void borrarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
