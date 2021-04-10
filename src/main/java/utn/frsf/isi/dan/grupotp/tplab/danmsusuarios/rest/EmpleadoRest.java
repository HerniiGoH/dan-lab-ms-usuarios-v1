package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Empleado;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.EmpleadoService;

import java.util.List;

@RestController
@RequestMapping("/api/empleado")
@Api(value = "EmpleadoRest", description = "Permite gestionar los empleados de la empresa")
public class EmpleadoRest {
    final EmpleadoService empleadoService;
    @Autowired
    public EmpleadoRest(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    @ApiOperation(value = "Devuelve la lista completa de empleados")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Devueltos correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<List<Empleado>> buscarTodos(){
        return ResponseEntity.ok(empleadoService.buscarTodos());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca un empleado por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<Empleado> buscarEmpleadoPorId(@PathVariable Integer id){
        return ResponseEntity.of(empleadoService.buscarEmpleadoPorId(id));
    }

    @GetMapping("/empleado")
    @ApiOperation(value = "Busca los empleados que cumplan con los criterios de bsuqueda")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrado(s) correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "Error al ingresar los criterios de busqueda"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<List<Empleado>> buscarEmpleado(@RequestParam(required = false, name= "id") Integer id,@RequestParam(required = false, name= "razonSocial") String razonSocial, @RequestParam(required = false, name= "mail") String mail){
        return ResponseEntity.of(empleadoService.buscarEmpleado(id, razonSocial, mail));
    }

    @PostMapping
    @ApiOperation(value = "Crea un nuevo empleado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Creado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado nuevoEmpleado){
        return ResponseEntity.ok(empleadoService.crearEmpleado(nuevoEmpleado));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Actualiza un empleado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<Empleado> actualizarEmpleado(@RequestBody Empleado nuevoEmpleado, @PathVariable Integer id){
        return ResponseEntity.of(empleadoService.actualizarEmpleado(nuevoEmpleado, id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Elimina un empleado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Eliminado correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe"),
            @ApiResponse(code = 405, message = "Metodo no permitido"),
            @ApiResponse(code = 500, message = "Error del servidor")
    })
    public ResponseEntity<Empleado> borrarEmpleado(@PathVariable Integer id){
        if(empleadoService.borrarEmpleado(id)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
