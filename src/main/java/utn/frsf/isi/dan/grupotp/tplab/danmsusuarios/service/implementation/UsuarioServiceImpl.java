package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.stereotype.Service;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Usuario;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    //lista de los usuarios creados
    private static final List<Usuario> listaUsuarios = new ArrayList<>();
    //generador de los id de los usuarios
    private static Integer SEQ_ID = 0;

    @Override
    public List<Usuario> buscartodos() {
        return listaUsuarios;
    }

    @Override
    public Usuario crearUsuario(Usuario nuevoUsuario) {
        nuevoUsuario.setId(++SEQ_ID);
        listaUsuarios.add(nuevoUsuario);
        return nuevoUsuario;
    }
}
