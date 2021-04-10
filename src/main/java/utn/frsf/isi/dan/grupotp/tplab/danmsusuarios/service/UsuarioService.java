package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service;

import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> buscartodos();
    Usuario crearUsuario(Usuario nuevoUsuario);
}
