package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.logic.entities.Empleado;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/empleado")
@Api(value = "EmpleadoRest", description = "Permite gestionar los empleados de la empresa")
public class EmpleadoRest {
    //lista de los empleados creados
    private static final List<Empleado> listaEmpleados = new ArrayList<Empleado>();
    //generador de los id de los empleados
    private static Integer SEQ_ID = 0;

    @GetMapping
    @ApiOperation(value = "Devuelve la lista completa de empleados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Devueltos correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido")
    })
    public ResponseEntity<List<Empleado>> buscarTodos(){
        return ResponseEntity.ok(listaEmpleados);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca un empleado por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Empleado> buscarEmpleadoPorId(@PathVariable Integer id){
        Optional<Empleado> empleadoEncontrado = listaEmpleados
                .stream()
                .filter(empleado_aux -> empleado_aux.getId().equals(id))
                .findFirst();
        return ResponseEntity.of(empleadoEncontrado);
    }

    @GetMapping("/empleado")
    @ApiOperation(value = "Busca los empleados que cumplan con los criterios de bsuqueda")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrado(s) correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "Error al ingresar los criterios de busqueda")
    })
    public ResponseEntity<List<Empleado>> buscarEmpleado(@RequestParam(required = false, name= "id") Integer id,@RequestParam(required = false, name= "razonSocial") String razonSocial, @RequestParam(required = false, name= "mail") String mail){
        Optional<List<Empleado>> listaEmpleadosEncontrados = Optional.of(listaEmpleados
            .stream()
            .filter(empleado -> id==null || empleado.getId().equals(id))
            .filter(empleado -> razonSocial==null || empleado.getRazonSocial().equals(razonSocial))
            .filter(empleado -> mail==null || empleado.getMail()==null)
            .collect(Collectors.toList()));
        return ResponseEntity.of(listaEmpleadosEncontrados);
    }

    @PostMapping
    @ApiOperation(value = "Crea un nuevo empleado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Creado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido")
    })
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado nuevoEmpleado){
        nuevoEmpleado.setId(SEQ_ID++);
        listaEmpleados.add(nuevoEmpleado);
        return ResponseEntity.ok(nuevoEmpleado);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Actualiza un empleado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Empleado> actualizarEmpleado(@RequestBody Empleado nuevoEmpleado, @PathVariable Integer id){
        OptionalInt optIndex = IntStream.range(0, listaEmpleados.size())
                .filter(pos -> listaEmpleados.get(pos).getId().equals(id))
                .findFirst();
        if(optIndex.isPresent()){
            listaEmpleados.set(optIndex.getAsInt(), nuevoEmpleado);
            return ResponseEntity.ok(nuevoEmpleado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Elimina un empleado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Eliminado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Empleado> borrarEmpleado(@PathVariable Integer id){
        OptionalInt optIndex = IntStream.range(0, listaEmpleados.size())
                .filter(pos -> listaEmpleados.get(pos).getId().equals(id))
                .findFirst();
        if(optIndex.isPresent()){
            listaEmpleados.remove(optIndex.getAsInt());
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
