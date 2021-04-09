package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.stereotype.Service;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
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

    @Override
    public List<Cliente> buscarTodos() {
        return listaClientes;
    }

    @Override
    public Optional<Cliente> buscarClientePorId(Integer id) {
        return listaClientes
                .stream()
                .filter(cliente_aux -> cliente_aux.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<List<Cliente>> buscarCliente(Integer id, String cuit, String razonSocial, String mail, Double maxCuentaCorriente, Boolean habilitadoOnline) {
        return Optional.of(listaClientes
                .stream()
                .filter(cliente -> id==null || cliente.getId().equals(id))
                .filter(cliente -> cuit==null || cliente.getCuit().equalsIgnoreCase(cuit))
                .filter(cliente -> razonSocial==null || cliente.getRazonSocial().equalsIgnoreCase(razonSocial))
                .filter(cliente -> mail==null || cliente.getMail().equalsIgnoreCase(mail))
                .filter(cliente -> maxCuentaCorriente==null || cliente.getMaxCuentaCorriente().equals(maxCuentaCorriente))
                .filter(cliente -> habilitadoOnline==null || cliente.getHabilitadoOnline().equals(habilitadoOnline))
                .collect(Collectors.toList()));
    }

    @Override
    public Cliente crearCliente(Cliente nuevoCliente) {
        nuevoCliente.setId(SEQ_ID++);
        listaClientes.add(nuevoCliente);
        return nuevoCliente;
    }

    @Override
    public Optional<Cliente> actualizarCliente(Cliente nuevoCliente, Integer id) {
        OptionalInt optIndex = IntStream.range(0, listaClientes.size())
                .filter(pos -> listaClientes.get(pos).getId().equals(id))
                .findFirst();
        if(optIndex.isPresent()){
            listaClientes.set(optIndex.getAsInt(), nuevoCliente);
            return Optional.of(nuevoCliente);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Boolean borrarCliente(Integer id) {
        OptionalInt optIndex = IntStream.range(0, listaClientes.size())
                .filter(pos -> listaClientes.get(pos).getId().equals(id))
                .findFirst();
        if(optIndex.isPresent()){
            listaClientes.remove(optIndex.getAsInt());
            return true;
        } else {
            return false;
        }
    }
}
