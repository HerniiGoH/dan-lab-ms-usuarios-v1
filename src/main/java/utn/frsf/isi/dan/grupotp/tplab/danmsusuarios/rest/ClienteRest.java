package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@Api(value="ClienteRest", description = "Permite gestionar los clientes de la empresa")
public class ClienteRest {
    final ClienteService clienteService;
    @Autowired
    public ClienteRest(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    @ApiOperation(value = "Devuelve la lista completa de clientes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Devueltos correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<List<Cliente>> buscarTodos(){
        return ResponseEntity.ok(clienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca un cliente por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Integer id){
        return ResponseEntity.of(clienteService.buscarClientePorId(id));
    }

    @GetMapping("/cliente")
    @ApiOperation(value = "Busca todos los clientes que cumplan los criterios de busqueda")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrado(s) correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "Error al ingresar los criterios de busqueda"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<List<Cliente>> buscarCliente(@RequestParam(required = false, name="id") Integer id, @RequestParam(required = false, name="cuit") String cuit, @RequestParam(required = false, name = "razonSocial") String razonSocial, @RequestParam(required = false, name = "mail") String mail, @RequestParam(required = false, name = "maxCuentaCorriente") Double maxCuentaCorriente, @RequestParam(required = false, name = "habilitadoOnline") Boolean habilitadoOnline){
        return ResponseEntity.of(clienteService.buscarCliente(id, cuit, razonSocial, mail, maxCuentaCorriente, habilitadoOnline));
    }

    @PostMapping
    @ApiOperation(value="Crea un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Creado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente nuevoCliente){
        return ResponseEntity.ok(clienteService.crearCliente(nuevoCliente));
    }

    @PutMapping("/{id}")
    @ApiOperation(value="Actualiza un cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizado) correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<Cliente> actualizarCliente(@RequestBody Cliente nuevoCliente, @PathVariable Integer id){
        return ResponseEntity.of(clienteService.actualizarCliente(nuevoCliente, id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="Elimina un cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Eliminado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<Cliente> borrarCliente(@PathVariable Integer id){
        if(clienteService.borrarCliente(id)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
