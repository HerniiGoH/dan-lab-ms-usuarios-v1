package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.stereotype.Service;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.ClienteService;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
    @Override
    public List<Cliente> buscarTodos() {
        return null;
    }

    @Override
    public Optional<Cliente> buscarClientePorId(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Cliente>> buscarCliente(Integer id, String cuit, String razonSocial, String mail, Double maxCuentaCorriente, Boolean habilitadoOnline) {
        return Optional.empty();
    }

    @Override
    public Cliente crearCliente(Cliente nuevoCliente) {
        return null;
    }

    @Override
    public Optional<Cliente> actualizarCliente(Cliente nuevoCliente, Integer id) {
        return Optional.empty();
    }

    @Override
    public Boolean borrarCliente(Integer id) {
        return null;
    }
}
