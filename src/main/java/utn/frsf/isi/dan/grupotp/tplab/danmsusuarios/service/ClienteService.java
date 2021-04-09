package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service;

import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> buscarTodos();
    Optional<Cliente> buscarClientePorId(Integer id);
    Optional<List<Cliente>> buscarCliente(Integer id, String cuit, String razonSocial, String mail, Double maxCuentaCorriente, Boolean habilitadoOnline);
    Cliente crearCliente(Cliente nuevoCliente);
    Optional<Cliente> actualizarCliente(Cliente nuevoCliente, Integer id);
    Boolean borrarCliente(Integer id);
}
