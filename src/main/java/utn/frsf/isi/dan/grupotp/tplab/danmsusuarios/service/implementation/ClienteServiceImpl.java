package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories.ClienteRepository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.ClienteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ClienteServiceImpl implements ClienteService {

    //lista de los clientes creados
    private static final List<Cliente> listaClientes = new ArrayList<>();
    //generador de los id de los clientes
    private static Integer SEQ_ID = 0;

    @Autowired
    ClienteRepository clienteRepository;

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

        return clienteRepository.findAllByIdAndCuitAndRazonSocialAndMailAndMaxCuentaCorrienteAndHabilitadoOnline(id, cuit, razonSocial, mail, maxCuentaCorriente, habilitadoOnline);

        /*return Optional.of(listaClientes
                .stream()
                .filter(cliente -> id==null || cliente.getId().equals(id))
                .filter(cliente -> cuit==null || cliente.getCuit().equalsIgnoreCase(cuit))
                .filter(cliente -> razonSocial==null || cliente.getRazonSocial().equalsIgnoreCase(razonSocial))
                .filter(cliente -> mail==null || cliente.getMail().equalsIgnoreCase(mail))
                .filter(cliente -> maxCuentaCorriente==null || cliente.getMaxCuentaCorriente().equals(maxCuentaCorriente))
                .filter(cliente -> habilitadoOnline==null || cliente.getHabilitadoOnline().equals(habilitadoOnline))
                .collect(Collectors.toList()));*/
    }

    @Override
    public Cliente crearCliente(Cliente nuevoCliente) {
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
            return true;
        } else {
            return false;
        }
    }
}
