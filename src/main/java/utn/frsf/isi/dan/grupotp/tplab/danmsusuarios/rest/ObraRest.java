package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Obra;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.TipoObra;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.ObraService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/obra")
@Api(value = "ObraRest", description = "Permite egstionar las obras de la empresa")
public class ObraRest {
    @Autowired
    ObraService obraService;

    @GetMapping
    @ApiOperation(value = "Devuelve la lista completa de obras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Devueltas correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido")
    })
    public ResponseEntity<List<Obra>> buscarTodas(){
        return ResponseEntity.ok(obraService.buscarTodas());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca una obra por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrada correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Obra> buscarObraPorId(@PathVariable Integer id){
        return ResponseEntity.of(obraService.buscarObraPorId(id));
    }

    @GetMapping("/obra")
    @ApiOperation(value = "Busca las obras que cumplan con los criterios de bsuqueda")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrada(s) correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "Error al ingresar los criterios de busqueda")
    })
    public ResponseEntity<List<Obra>> buscarObra(@RequestParam(required = false, name = "id") Integer id, @RequestParam(required = false, name = "descripcion") String descripcion, @RequestParam(required = false, name = "latitud") Float latitud, @RequestParam(required = false, name = "longitud") Float longitud, @RequestParam(required = false, name = "direccion") String direccion, @RequestParam(required = false, name = "superficie") Integer superficie, @RequestBody(required = false) TipoObra tipoObra, @RequestBody(required = false) Cliente cliente){
        return ResponseEntity.of(obraService.buscarObra(id, descripcion, latitud, longitud, direccion, superficie, tipoObra, cliente));
    }

    @PostMapping
    @ApiOperation(value = "Crea una nueva obra")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Creada correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido")
    })
    public ResponseEntity<Obra> crearObra(@RequestBody Obra nuevaObra){
        return ResponseEntity.ok(obraService.crearObra(nuevaObra));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Actualiza una obra")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Actualizada correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Obra> actualizarObra(@RequestBody Obra nuevaObra, @PathVariable Integer id){
        return ResponseEntity.of(obraService.actualizarObra(nuevaObra, id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Elimina una obra")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Eliminada correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "El ID no existe")
    })
    public ResponseEntity<Obra> borrarObra (@PathVariable Integer id){
        if(obraService.borrarObra(id)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
