package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.minidev.json.JSONObject;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.ClienteService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<List<Cliente>> buscarTodos(){
        return ResponseEntity.ok(clienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca un cliente por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrado correctamente"),
            @ApiResponse(code = 404, message = "El ID no existe"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity buscarClientePorId(@PathVariable Integer id){
        Optional<Cliente> clienteEncontrado = clienteService.buscarClientePorId(id);
        if(clienteEncontrado.isPresent()){
            return ResponseEntity.of(clienteEncontrado);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(construirMensaje(LocalDateTime.now(), 404, "Not Found", "El id no existe"));

    }

    @GetMapping("/cliente")
    @ApiOperation(value = "Busca todos los clientes que cumplan los criterios de busqueda")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrado(s) correctamente"),
            @ApiResponse(code = 404, message = "No hay clientes que coincidan con los criterios ingresados"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity buscarCliente(@RequestParam(required = false, name="id") Integer id, @RequestParam(required = false, name="cuit") String cuit, @RequestParam(required = false, name = "razonSocial") String razonSocial, @RequestParam(required = false, name = "mail") String mail, @RequestParam(required = false, name = "maxCuentaCorriente") Double maxCuentaCorriente, @RequestParam(required = false, name = "habilitadoOnline") Boolean habilitadoOnline){
        Optional<List<Cliente>> clientesEncontrados = clienteService.buscarCliente(id, cuit, razonSocial, mail, maxCuentaCorriente, habilitadoOnline);
        if(clientesEncontrados.isPresent()){
            return ResponseEntity.of(clientesEncontrados);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(construirMensaje(LocalDateTime.now(), 404, "Not Found", "No hay clientes que coincidan con los criterios de busqueda"));
        }
    }

    @PostMapping
    @ApiOperation(value="Crea un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Creado correctamente"),
            @ApiResponse(code = 400, message = "Error en los datos de ingreso"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity crearCliente(@RequestBody Cliente nuevoCliente){
        if(nuevoCliente.getUsuario()!=null && nuevoCliente.getObras()!=null && !nuevoCliente.getObras().isEmpty()){
            return ResponseEntity.of(clienteService.crearCliente(nuevoCliente));
        } else return ResponseEntity.badRequest().body(construirMensaje(LocalDateTime.now(), 400, "Bad Request","Cliente debe tener un usuario asociado y al menos 1 (una) obra."));
    }

    @PutMapping("/{id}")
    @ApiOperation(value="Actualiza un cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizado) correctamente"),
            @ApiResponse(code = 404, message = "El ID no existe"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity actualizarCliente(@RequestBody Cliente nuevoCliente, @PathVariable Integer id){
        Optional<Cliente> clienteActualizado = clienteService.actualizarCliente(nuevoCliente, id);
        if(clienteActualizado.isPresent()){
            return ResponseEntity.of(clienteActualizado);
        } else {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(construirMensaje(LocalDateTime.now(), 404, "Not Found", "El id no existe"));
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="Elimina un cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Eliminado correctamente"),
            @ApiResponse(code = 404, message = "El ID no existe"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity borrarCliente(@PathVariable Integer id){
        Optional<Cliente> clienteEliminado = clienteService.borrarCliente(id);
        if(clienteEliminado.isPresent()){
            return ResponseEntity.of(clienteEliminado);
        } else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(construirMensaje(LocalDateTime.now(), 404, "Not Found", "El id no existe"));
        }
    }

    private JSONObject construirMensaje(LocalDateTime timeStamp, Integer status, String error, String mensaje){
        JSONObject jsonObject = new JSONObject();
        jsonObject.appendField("timestamp", timeStamp)
                .appendField("status", status)
                .appendField("error", error)
                .appendField("message", mensaje);
        return jsonObject;
    }
}
