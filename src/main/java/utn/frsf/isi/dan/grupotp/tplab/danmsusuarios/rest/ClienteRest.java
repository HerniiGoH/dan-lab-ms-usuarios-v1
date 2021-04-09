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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/cliente")
@Api(value="ClienteRest", description = "Permite gestionar los clientes de la empresa")
public class ClienteRest {

    @Autowired
    ClienteService clienteService;
    //lista de los clientes creados
    private static final List<Cliente> listaClientes = new ArrayList<>();
    //generador de los id de los clientes
    private static Integer SEQ_ID = 0;

    @GetMapping
    @ApiOperation(value = "Devuelve la lista completa de clientes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Devueltos correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido")
    })
    public ResponseEntity<List<Cliente>> buscarTodos(){
        return ResponseEntity.ok(listaClientes);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca un cliente por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Integer id){
        Optional<Cliente> clienteEncontrado = listaClientes
                .stream()
                .filter(cliente_aux -> cliente_aux.getId().equals(id))
                .findFirst();
        return ResponseEntity.of(clienteEncontrado);
    }

    @GetMapping("/cliente")
    @ApiOperation(value = "Busca todos los clientes que cumplan los criterios de busqueda")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrado(s) correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "Error al ingresar los criterios de busqueda")
    })
    public ResponseEntity<List<Cliente>> buscarCliente(@RequestParam(required = false, name="id") Integer id, @RequestParam(required = false, name="cuit") String cuit, @RequestParam(required = false, name = "razonSocial") String razonSocial, @RequestParam(required = false, name = "mail") String mail, @RequestParam(required = false, name = "maxCuentaCorriente") Double maxCuentaCorriente, @RequestParam(required = false, name = "habilitadoOnline") Boolean habilitadoOnline){
        Optional<List<Cliente>> listaClientesEncontrados = Optional.of(listaClientes
            .stream()
            .filter(cliente -> id==null || cliente.getId().equals(id))
            .filter(cliente -> cuit==null || cliente.getCuit().equalsIgnoreCase(cuit))
            .filter(cliente -> razonSocial==null || cliente.getRazonSocial().equalsIgnoreCase(razonSocial))
            .filter(cliente -> mail==null || cliente.getMail().equalsIgnoreCase(mail))
            .filter(cliente -> maxCuentaCorriente==null || cliente.getMaxCuentaCorriente().equals(maxCuentaCorriente))
            .filter(cliente -> habilitadoOnline==null || cliente.getHabilitadoOnline().equals(habilitadoOnline))
            .collect(Collectors.toList()));
        return ResponseEntity.of(listaClientesEncontrados);
    }

    @PostMapping
    @ApiOperation(value="Crea un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Creado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido")
    })
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente nuevoCliente){
        nuevoCliente.setId(SEQ_ID++);
        listaClientes.add(nuevoCliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    @PutMapping("/{id}")
    @ApiOperation(value="Actualiza un cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizado) correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Cliente> actualizarCliente(@RequestBody Cliente nuevoCliente, @PathVariable Integer id){
        OptionalInt optIndex = IntStream.range(0, listaClientes.size())
                .filter(pos -> listaClientes.get(pos).getId().equals(id))
                .findFirst();
        if(optIndex.isPresent()){
            listaClientes.set(optIndex.getAsInt(), nuevoCliente);
            return ResponseEntity.ok(nuevoCliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="Elimina un cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Eliminado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Cliente> borrarCliente(@PathVariable Integer id){
        OptionalInt optIndex = IntStream.range(0, listaClientes.size())
                .filter(pos -> listaClientes.get(pos).getId().equals(id))
                .findFirst();
        if(optIndex.isPresent()){
            listaClientes.remove(optIndex.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
