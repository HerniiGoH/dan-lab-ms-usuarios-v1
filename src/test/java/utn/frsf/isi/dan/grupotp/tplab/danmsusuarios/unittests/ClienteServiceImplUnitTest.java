package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.unittests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.*;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.enumerations.RiesgoBCRA;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories.ClienteRepository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.ObraService;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.RiesgoBCRAService;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.UsuarioService;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation.ClienteServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClienteServiceImplUnitTest {
    @Autowired
    ClienteServiceImpl clienteService;
    @MockBean
    ClienteRepository clienteRepository;
    @MockBean
    UsuarioService usuarioService;
    @MockBean
    ObraService obraService;
    @MockBean
    RiesgoBCRAService riesgoBCRAService;

    static List<Cliente> clientes;

    @BeforeAll
    static void setUp() throws Exception{
        clientes = new ArrayList<>();

        Cliente cliente1 = new Cliente();
        cliente1.setId(1);
        cliente1.setRazonSocial("Cliente 01");
        cliente1.setCuit("00000000001");
        cliente1.setMail("cliente01@mail.com");
        cliente1.setHabilitadoOnline(true);
        cliente1.setRiesgoBCRA(RiesgoBCRA.NORMAL);
        cliente1.setObras(new ArrayList<>());

        Usuario usuario1 = new Usuario();
        usuario1.setId(1);
        usuario1.setUser(cliente1.getMail());
        usuario1.setPassword("1234");
        usuario1.setTipoUsuario(new TipoUsuario(1, "CLIENTE"));

        cliente1.setUsuario(usuario1);

        Obra obra1 = new Obra();
        obra1.setId(1);
        obra1.setDescripcion("Descripcion obra 01");
        obra1.setLatitud(420.0F);
        obra1.setLongitud(420.0F);
        obra1.setDireccion("Avenida Ramirez 1313");
        obra1.setSuperficie(69);
        obra1.setCliente(cliente1);
        obra1.setTipoObra(new TipoObra(1, "Obra para trabajar en una reforma"));

        cliente1.getObras().add(obra1);

        clientes.add(cliente1);
    }

    @Test
    void testBuscarTodos(){
        when(clienteRepository.findAll()).thenReturn(new ArrayList<>(clientes));

        List<Cliente> clientesEncontrados = clienteService.buscarTodos();

        Assertions.assertEquals(clientesEncontrados.size(), 1);

        Mockito.verify(clienteRepository,times(1)).findAll();
    }

    @Test
    void testBuscarClientePorIdExistenteyNoExistente(){
        when(clienteRepository.findById(1)).thenReturn(Optional.of(clientes.get(0)));
        when(clienteRepository.findById(2)).thenReturn(Optional.empty());

        Optional<Cliente> cliente1 = clienteService.buscarClientePorId(1);
        Optional<Cliente> cliente2 = clienteService.buscarClientePorId(2);

        Assertions.assertTrue(cliente1.isPresent());
        Assertions.assertFalse(cliente2.isPresent());

        Mockito.verify(clienteRepository, times(2)).findById(any(Integer.class));

    }

    @Test
    void testBuscarClientePorCuityRazonSocialExistenteyNoExistente(){
        when(clienteRepository.findAllByQuerySQL(null, "00000000001",null,null,null,null)).thenReturn(Optional.of(new ArrayList<>(clientes)));
        when(clienteRepository.findAllByQuerySQL(null, "00000000001","Cliente 01",null,null,null)).thenReturn(Optional.of(new ArrayList<>(clientes)));
        when(clienteRepository.findAllByQuerySQL(null, "00000000001","Cliente 02",null,null,null)).thenReturn(Optional.empty());
        when(clienteRepository.findAllByQuerySQL(null, "00000000002",null,null,null,null)).thenReturn(Optional.empty());
        when(clienteRepository.findAllByQuerySQL(null, "00000000002","Cliente 02",null,null,null)).thenReturn(Optional.empty());
        when(clienteRepository.findAllByQuerySQL(null, "00000000002","Cliente 01",null,null,null)).thenReturn(Optional.empty());

        Optional<List<Cliente>>clientes1 = clienteService.buscarCliente(null, "00000000001",null,null,null,null);
        Optional<List<Cliente>>clientes2 = clienteService.buscarCliente(null, "00000000001","Cliente 01",null,null,null);
        Optional<List<Cliente>>clientes3 = clienteService.buscarCliente(null, "00000000001","Cliente 02",null,null,null);
        Optional<List<Cliente>>clientes4 = clienteService.buscarCliente(null, "00000000002",null,null,null,null);
        Optional<List<Cliente>>clientes5 = clienteService.buscarCliente(null, "00000000002","Cliente 02",null,null,null);
        Optional<List<Cliente>>clientes6 = clienteService.buscarCliente(null, "00000000002","Cliente 01",null,null,null);

        Assertions.assertTrue(clientes1.isPresent());
        Assertions.assertTrue(clientes2.isPresent());
        Assertions.assertFalse(clientes3.isPresent());
        Assertions.assertFalse(clientes4.isPresent());
        Assertions.assertFalse(clientes5.isPresent());
        Assertions.assertFalse(clientes6.isPresent());

        Mockito.verify(clienteRepository,times(6)).findAllByQuerySQL(any(), any(), any(),any(),any(),any());
    }

    @Test
    void testCrearCliente(){
        when(riesgoBCRAService.obtenerRiesgoBCRA(any(Cliente.class))).thenReturn(RiesgoBCRA.NORMAL);
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(clientes.get(0).getUsuario());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clientes.get(0));
        when(obraService.crearObras(any())).thenReturn(clientes.get(0).getObras());

        Optional<Cliente> clienteCreado = clienteService.crearCliente(clientes.get(0));

        Assertions.assertTrue(clienteCreado.isPresent());

        Mockito.verify(riesgoBCRAService, times(1)).obtenerRiesgoBCRA(any(Cliente.class));
        Mockito.verify(usuarioService, times(1)).crearUsuario(any(Usuario.class));
        Mockito.verify(clienteRepository, times(1)).save(any(Cliente.class));
        Mockito.verify(obraService, times(1)).crearObras(any());
    }

    @Test
    void testActualizarClienteConIdExistenteyNoExistente(){
        when(clienteRepository.existsById(1)).thenReturn(true);
        when(clienteRepository.existsById(2)).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clientes.get(0));

        Optional<Cliente> cliente1 = clienteService.actualizarCliente(clientes.get(0),1);
        Optional<Cliente> cliente2 = clienteService.actualizarCliente(clientes.get(0),2);

        Assertions.assertTrue(cliente1.isPresent());
        Assertions.assertFalse(cliente2.isPresent());

        Mockito.verify(clienteRepository, times(2)).existsById(any(Integer.class));
        Mockito.verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testBorrarClienteConIdExistenteyNoExistenteSinPedidos(){
        when(clienteRepository.findById(1)).thenReturn(Optional.of(clientes.get(0)));
        when(clienteRepository.findById(2)).thenReturn(Optional.empty());
        doNothing().when(clienteRepository).deleteById(1);

        Optional<Cliente>cliente1 = clienteService.borrarCliente(1);
        Optional<Cliente>cliente2 = clienteService.borrarCliente(2);

        Assertions.assertTrue(cliente1.isPresent());
        Assertions.assertNull(cliente1.get().getFechaBaja());
        Assertions.assertFalse(cliente2.isPresent());

        Mockito.verify(clienteRepository, times(2)).findById(any(Integer.class));
        Mockito.verify(clienteRepository, times(1)).deleteById(any(Integer.class));
    }
}
