package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories.ClienteRepository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.ClienteService;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.UsuarioService;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    final ClienteRepository clienteRepository;
    final UsuarioService usuarioService;
    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, UsuarioService usuarioService) {
        this.clienteRepository = clienteRepository;
        this.usuarioService = usuarioService;
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
    public Cliente crearCliente(Cliente nuevoCliente) {
        nuevoCliente.setUsuario(usuarioService.crearUsuario(nuevoCliente.getUsuario()));
        return clienteRepository.save(nuevoCliente);
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
