package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Usuario;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories.UsuarioRepository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    //lista de los usuarios creados
    private static final List<Usuario> listaUsuarios = new ArrayList<>();
    //generador de los id de los usuarios
    private static Integer SEQ_ID = 0;

    @Override
    public List<Usuario> buscartodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario crearUsuario(Usuario nuevoUsuario) {
        return usuarioRepository.save(nuevoUsuario);
    }
}
