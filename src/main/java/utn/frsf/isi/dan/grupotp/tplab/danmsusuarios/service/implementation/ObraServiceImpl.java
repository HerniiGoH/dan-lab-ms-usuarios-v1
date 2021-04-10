package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Obra;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.TipoObra;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories.ObraRepository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.ObraService;

import java.util.List;
import java.util.Optional;

@Service
public class ObraServiceImpl implements ObraService {
    @Autowired
    ObraRepository obraRepository;

    @Override
    public List<Obra> buscarTodas() {
        return obraRepository.findAll();
    }

    @Override
    public Optional<Obra> buscarObraPorId(Integer id) {
        return obraRepository.findById(id);
    }

    @Override
    public Optional<List<Obra>> buscarObra(Integer id, String descripcion, Float latitud, Float longitud, String direccion, Integer superficie, TipoObra tipoObra, Cliente cliente) {
        return obraRepository.findAllByQuery(id, descripcion, latitud, longitud, direccion, superficie, tipoObra, cliente);
    }

    @Override
    public Obra crearObra(Obra nuevaObra) {
        return obraRepository.save(nuevaObra);
    }

    @Override
    public Optional<Obra> actualizarObra(Obra nuevaObra, Integer id) {
        if(obraRepository.existsById(id)){
            return Optional.of(obraRepository.save(nuevaObra));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Boolean borrarObra(Integer id) {
        if(obraRepository.existsById(id)){
            obraRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
