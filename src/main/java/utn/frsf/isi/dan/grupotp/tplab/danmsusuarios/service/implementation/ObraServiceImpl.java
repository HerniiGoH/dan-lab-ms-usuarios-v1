package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.stereotype.Service;
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

@Service
public class ObraServiceImpl implements ObraService {
    //lista de las obras creadas
    private static final List<Obra> listaObras = new ArrayList<Obra>();
    //generador de los id de las obras
    private static Integer SEQ_ID = 0;

    @Override
    public List<Obra> buscarTodas() {
        return listaObras;
    }

    @Override
    public Optional<Obra> buscarObraPorId(Integer id) {
        return listaObras
                .stream()
                .filter(obra_aux -> obra_aux.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<List<Obra>> buscarObra(Integer id, String descripcion, Float latitud, Float longitud, String direccion, Integer superficie, TipoObra tipoObra, Cliente cliente) {
        return Optional.of(listaObras
                .stream()
                .filter(obra -> id==null || obra.getId().equals(id))
                .filter(obra -> descripcion==null || obra.getDescripcion().equalsIgnoreCase(descripcion))
                .filter(obra -> latitud==null || obra.getLatitud().equals(latitud))
                .filter(obra -> longitud==null || obra.getLongitud().equals(longitud))
                .filter(obra -> direccion==null || obra.getDireccion().equalsIgnoreCase(direccion))
                .filter(obra -> superficie==null || obra.getSuperficie().equals(superficie))
                .filter(obra -> tipoObra==null || obra.getTipoObra().equals(tipoObra))
                .filter(obra -> cliente==null || obra.getCliente().equals(cliente))
                .collect(Collectors.toList()));
    }

    @Override
    public Obra crearObra(Obra nuevaObra) {
        nuevaObra.setId(SEQ_ID++);
        listaObras.add(nuevaObra);
        return nuevaObra;
    }

    @Override
    public Optional<Obra> actualizarObra(Obra nuevaObra, Integer id) {
        OptionalInt optIndex = IntStream.range(0, listaObras.size())
                .filter(pos -> listaObras.get(pos).getId().equals(id))
                .findFirst();
        if(optIndex.isPresent()) {
            listaObras.set(optIndex.getAsInt(), nuevaObra);
            return Optional.of(nuevaObra);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Boolean borrarObra(Integer id) {
        OptionalInt optIndex = IntStream.range(0, listaObras.size())
                .filter(pos -> listaObras.get(pos).getId().equals(id))
                .findFirst();
        if(optIndex.isPresent()) {
            listaObras.remove(optIndex.getAsInt());
            return true;
        } else {
            return false;
        }
    }
}
