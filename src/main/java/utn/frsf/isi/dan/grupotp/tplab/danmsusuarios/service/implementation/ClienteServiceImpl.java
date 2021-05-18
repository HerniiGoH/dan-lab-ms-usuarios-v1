package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.DTO.PedidoDTO;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Obra;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.enumerations.RiesgoBCRA;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories.ClienteRepository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.ClienteService;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.ObraService;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.RiesgoBCRAService;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
        this.riesgoBCRAService = riesgoBCRAService;
    }

    @Override
    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }
    //TODO no devolver los que tienen fecha de baja

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
        nuevoCliente.setHabilitadoOnline(nuevoCliente.getRiesgoBCRA().equals(RiesgoBCRA.NORMAL) || nuevoCliente.getRiesgoBCRA().equals(RiesgoBCRA.RIESGO_BAJO));
        nuevoCliente.getUsuario().setUser(nuevoCliente.getMail());
        nuevoCliente.getUsuario().setPassword("1234");
        nuevoCliente.setUsuario(usuarioService.crearUsuario(nuevoCliente.getUsuario()));
        Cliente clienteCreado = clienteRepository.save(nuevoCliente);
        for (Obra aux : nuevoCliente.getObras()) {
            aux.setCliente(clienteCreado);
        }
        clienteCreado.setObras(obraService.crearObras(nuevoCliente.getObras()));
        return Optional.of(clienteCreado);

    }

    @Override
    public Optional<Cliente> actualizarCliente(Cliente nuevoCliente, Integer id) {
        if (clienteRepository.existsById(id)) {
            return Optional.of(clienteRepository.save(nuevoCliente));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Cliente> borrarCliente(Integer id) {
        //TODO se les debe asignar una fecha de baja, no ELIMINAR
        //TODO verificar si tiene pedidos, no se puede "dar de baja" si ya tiene uno
        Optional<Cliente> clienteEncontrado = clienteRepository.findById(id);
        if(clienteEncontrado.isPresent()){
            Optional<Obra> aux = clienteEncontrado.get().getObras().stream().filter(obra -> {
                WebClient webClient = WebClient.create("http://localhost:4041/api/pedido/obra/" + obra.getId());
                ResponseEntity<List<PedidoDTO>> response = webClient.method(HttpMethod.GET)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .toEntityList(PedidoDTO.class)
                        .block();
                if (response != null && response.getStatusCode().equals(HttpStatus.OK)) {
                    List<PedidoDTO> pedidos = response.getBody();
                    assert pedidos != null;
                    return !pedidos.isEmpty();
                } else return false;
            }).findFirst();

            if (aux.isPresent()) {
                clienteEncontrado.get().setFechaBaja(LocalDateTime.now());
                clienteRepository.save(clienteEncontrado.get());
            } else {
                clienteRepository.deleteById(id);
            }
        }

        return clienteEncontrado;
    }
}
