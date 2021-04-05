package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.logic.entities.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.logic.entities.Obra;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.logic.entities.TipoObra;

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
    //lista de las obras creadas
    private static final List<Obra> listaObras = new ArrayList<Obra>();
    //generador de los id de las obras
    private static Integer SEQ_ID = 0;

    @GetMapping
    @ApiOperation(value = "Devuelve la lista completa de obras")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Devueltas correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido")
    })
    public ResponseEntity<List<Obra>> buscarTodas(){
        return ResponseEntity.ok(listaObras);
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
        Optional<Obra> obraEncontrada = listaObras
                .stream()
                .filter(obra_aux -> obra_aux.getId().equals(id))
                .findFirst();
        return ResponseEntity.of(obraEncontrada);
    }

    @GetMapping("/obra")
    @ApiOperation(value = "Busca las obras que cumplan con los criterios de bsuqueda")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Encontrada(s) correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido"),
            @ApiResponse(code = 404, message = "Error al ingresar los criterios de busqueda")
    })
    public ResponseEntity<List<Obra>> buscarObra(@RequestParam(required = false, name = "id") Integer id, @RequestParam(required = false, name = "descripcion") String descripcion, @RequestParam(required = false, name = "latitud") Float latitud, @RequestParam(required = false, name = "longitud") Float longitud, @RequestParam(required = false, name = "direccion") String direccion, @RequestParam(required = false, name = "superficie") Integer superficie, @RequestBody(required = false) TipoObra tipoObra, @RequestParam(required = false, name = "idCliente") Integer idCliente){
        Optional<List<Obra>> listaObrasEncontradas = Optional.of(listaObras
            .stream()
            .filter(obra -> id==null || obra.getId().equals(id))
            .filter(obra -> descripcion==null || obra.getDescripcion().equalsIgnoreCase(descripcion))
            .filter(obra -> latitud==null || obra.getLatitud().equals(latitud))
            .filter(obra -> longitud==null || obra.getLongitud().equals(longitud))
            .filter(obra -> direccion==null || obra.getDireccion().equalsIgnoreCase(direccion))
            .filter(obra -> superficie==null || obra.getSuperficie().equals(superficie))
            .filter(obra -> tipoObra==null || obra.getTipoipoObra().equals(tipoObra))
            .filter(obra -> idCliente==null || obra.getIdCliente().equals(idCliente))
            .collect(Collectors.toList()));
        return ResponseEntity.of(listaObrasEncontradas);
    }

    @PostMapping
    @ApiOperation(value = "Crea una nueva obra")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Creada correctamente"),
            @ApiResponse(code = 401, message = "No autorizado"),
            @ApiResponse(code = 403, message = "Prohibido")
    })
    public ResponseEntity<Obra> crearObra(@RequestBody Obra nuevaObra){
        nuevaObra.setId(SEQ_ID++);
        return ResponseEntity.ok(nuevaObra);
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
        OptionalInt optIndex = IntStream.range(0, listaObras.size())
                .filter(pos -> listaObras.get(pos).getId().equals(id))
                .findFirst();
        if(optIndex.isPresent()){
            listaObras.set(optIndex.getAsInt(), nuevaObra);
            return ResponseEntity.ok(nuevaObra);
        } else {
            return ResponseEntity.notFound().build();
        }
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
        OptionalInt optIndex = IntStream.range(0, listaObras.size())
                .filter(pos -> listaObras.get(pos).getId().equals(id))
                .findFirst();
        if(optIndex.isPresent()){
            listaObras.remove(optIndex.getAsInt());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
