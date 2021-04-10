package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Usuario;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRest {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("")
    public ResponseEntity<List<Usuario>> buscarTodos(){
        return ResponseEntity.ok(usuarioService.buscartodos());
    }
}
