package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value="UsuarioRest", description = "Permite gestionar los usuarios de la empresa")
public class UsuarioRest {
    final UsuarioService usuarioService;
    @Autowired
    public UsuarioRest(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("")
    @ApiOperation(value = "Devuelve la lista completa de usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Devueltos correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<List<Usuario>> buscarTodos(){
        return ResponseEntity.ok(usuarioService.buscartodos());
    }
}
