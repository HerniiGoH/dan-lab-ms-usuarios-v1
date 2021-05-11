package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Obra;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.enumerations.RiesgoBCRA;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories.ClienteRepository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.ClienteService;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.ObraService;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.RiesgoBCRAService;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.UsuarioService;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ClienteServiceImpl implements ClienteService {
    final ClienteRepository clienteRepository;
    final UsuarioService usuarioService;
    final ObraService obraService;
    final RiesgoBCRAService riesgoBCRAService;
    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, UsuarioService usuarioService, ObraService obraService, RiesgoBCRAService riesgoBCRAService) {
        this.clienteRepository = clienteRepository;
        this.usuarioService = usuarioService;
        this.obraService = obraService;
        this.riesgoBCRAService=riesgoBCRAService;
    }

    @Override
    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> buscarClientePorId(Integer id) {
        return clienteRepository.findById(id);
    }

    @Override
    public Optional<List<Cliente>> buscarCliente(Integer id, String cuit, String razonSocial, String mail, Double maxCuentaCorriente, Boolean habilitadoOnline) {

        return clienteRepository.findAllByQuerySQL(id, cuit, razonSocial, mail, maxCuentaCorriente, habilitadoOnline);
    }

    @Override
    public Optional<Cliente> crearCliente(Cliente nuevoCliente) {
        nuevoCliente.setRiesgoBCRA(riesgoBCRAService.obtenerRiesgoBCRA(nuevoCliente));
        if(nuevoCliente.getRiesgoBCRA().equals(RiesgoBCRA.NORMAL) || nuevoCliente.getRiesgoBCRA().equals(RiesgoBCRA.RIESGO_MEDIO)){
            nuevoCliente.setUsuario(usuarioService.crearUsuario(nuevoCliente.getUsuario()));
            Cliente clienteCreado = clienteRepository.save(nuevoCliente);
            for (Obra aux : nuevoCliente.getObras()) {
                aux.setCliente(clienteCreado);
            }
            clienteCreado.setObras(obraService.crearObras(nuevoCliente.getObras()));
            return Optional.of(clienteCreado);
        } else return Optional.empty();

    }

    @Override
    public Optional<Cliente> actualizarCliente(Cliente nuevoCliente, Integer id) {
        if(clienteRepository.existsById(id)){
            return Optional.of(clienteRepository.save(nuevoCliente));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Boolean borrarCliente(Integer id) {
        if(clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);
            usuarioService.borrarUsuario(id);
            return true;
        } else {
            return false;
        }
    }
}
