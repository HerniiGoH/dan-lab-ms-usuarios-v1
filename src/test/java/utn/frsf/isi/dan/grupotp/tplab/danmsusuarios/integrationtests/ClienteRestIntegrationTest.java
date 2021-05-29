package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.integrationtests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.MSUsuariosApplication;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.*;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.enumerations.RiesgoBCRA;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation.ClienteServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = MSUsuariosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteRestIntegrationTest {
    private final RestTemplate restTemplate = new RestTemplate();
    @LocalServerPort String puerto;
    static List<Cliente> clientes;
    @MockBean
    ClienteServiceImpl clienteService;

    @BeforeAll
    static void setUp(){
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
    @SuppressWarnings("unchecked")
    void deberiaBuscarTodosLosClientes(){
        when(clienteService.buscarTodos()).thenReturn(clientes);

        String server = "http://localhost:"+puerto+"/api/cliente";
        ResponseEntity<List<Cliente>> response = restTemplate.exchange(server, HttpMethod.GET, null, ((Class<List<Cliente>>)(Class<?>)List.class));

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().size(), 1);

        Mockito.verify(clienteService, times(1)).buscarTodos();
    }

    @Test
    @SuppressWarnings("unchecked")
    void deberiaEncontrarListaVacia(){
        when(clienteService.buscarTodos()).thenReturn(new ArrayList<Cliente>());

        String server = "http://localhost:"+puerto+"/api/cliente";
        ResponseEntity<List<Cliente>> response = restTemplate.exchange(server, HttpMethod.GET, null, ((Class<List<Cliente>>)(Class<?>)List.class));

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().size(), 0);

        Mockito.verify(clienteService, times(1)).buscarTodos();
    }

    @Test
    void deberiaEncontrarUnClientePorId(){
        when(clienteService.buscarClientePorId(1)).thenReturn(Optional.of(clientes.get(0)));

        String server = "http://localhost:"+puerto+"/api/cliente/1";
        ResponseEntity<Cliente> response = restTemplate.exchange(server, HttpMethod.GET, null, Cliente.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getId(), 1);

        Mockito.verify(clienteService, times(1)).buscarClientePorId(any(Integer.class));
    }

    @Test
    void deberiaTirarUnaExcepcionPorClienteNoExistente(){
        when(clienteService.buscarClientePorId(2)).thenReturn(Optional.empty());

        String server = "http://localhost:"+puerto+"/api/cliente/2";

        Assertions.assertThrows(HttpClientErrorException.class, ()->{
            restTemplate.exchange(server, HttpMethod.GET, null, Cliente.class);
        });

        Mockito.verify(clienteService, times(1)).buscarClientePorId(any(Integer.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    void deberiaEncontrarElClienteConCuitIngresado(){
        when(clienteService.buscarCliente(any(),eq("00000000001"), any(), any(), any(), any())).thenReturn(Optional.of(clientes));

        String server = "http://localhost:"+puerto+"/api/cliente/cliente?cuit=00000000001";

        ResponseEntity<List<Cliente>> response = restTemplate.exchange(server, HttpMethod.GET, null, ((Class<List<Cliente>>)(Class<?>)List.class));

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().size(), 1);

        Mockito.verify(clienteService, times(1)).buscarCliente(any(), any(), any(), any(), any(), any());
    }

    @Test
    @SuppressWarnings("unchecked")
    void deberiaTirarUnaExcepcionPorClienteNoExistente2(){
        when(clienteService.buscarCliente(any(),eq("00000000002"), any(), any(), any(), any())).thenReturn(Optional.empty());

        String server = "http://localhost:"+puerto+"/api/cliente/cliente?cuit=00000000002";

        Assertions.assertThrows(HttpClientErrorException.class, ()->{
            restTemplate.exchange(server, HttpMethod.GET, null, ((Class<List<Cliente>>)(Class<?>)List.class));
        });

        Mockito.verify(clienteService, times(1)).buscarCliente(any(), any(), any(), any(), any(), any());
    }

    @Test
    void deberiaCrearElCliente(){
        when(clienteService.crearCliente(any(Cliente.class))).thenReturn(Optional.of(clientes.get(0)));

        String server = "http://localhost:"+puerto+"/api/cliente";
        HttpEntity<Cliente> requestCliente = new HttpEntity<>(clientes.get(0));

        ResponseEntity<Cliente> response = restTemplate.exchange(server, HttpMethod.POST, requestCliente, Cliente.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getId(), 1);

        Mockito.verify(clienteService, times(1)).crearCliente(any());
    }

    @Test
    void deberiaArrojarExcepcionPorFaltaDeUsuario(){
        String server = "http://localhost:"+puerto+"/api/cliente";
        HttpEntity<Cliente> requestCliente = new HttpEntity<>(new Cliente());

        Assertions.assertThrows(HttpClientErrorException.class, ()->{
            restTemplate.exchange(server, HttpMethod.POST, requestCliente, Cliente.class);
        });

        Mockito.verify(clienteService, times(0)).crearCliente(any());
    }

    @Test
    void deberiaArrojarExcepcionPorObraNull(){
        String server = "http://localhost:"+puerto+"/api/cliente";
        Cliente aux = new Cliente();
        aux.setUsuario(new Usuario(1,"","",new TipoUsuario(1, "CLIENTE")));
        HttpEntity<Cliente> requestCliente = new HttpEntity<>(aux);

        Assertions.assertThrows(HttpClientErrorException.class, ()->{
            restTemplate.exchange(server, HttpMethod.POST, requestCliente, Cliente.class);
        });

        Mockito.verify(clienteService, times(0)).crearCliente(any());
    }

    @Test
    void deberiaArrojarExcepcionPorObrasVacias(){
        String server = "http://localhost:"+puerto+"/api/cliente";
        Cliente aux = new Cliente();
        aux.setUsuario(new Usuario(1,"","",new TipoUsuario(1, "CLIENTE")));
        aux.setObras(new ArrayList<>());
        HttpEntity<Cliente> requestCliente = new HttpEntity<>(aux);

        Assertions.assertThrows(HttpClientErrorException.class, ()->{
            restTemplate.exchange(server, HttpMethod.POST, requestCliente, Cliente.class);
        });

        Mockito.verify(clienteService, times(0)).crearCliente(any());
    }

    @Test
    void deberiaActualizarElCliente(){
        when(clienteService.actualizarCliente(any(Cliente.class), eq(1))).thenReturn(Optional.of(clientes.get(0)));
        String server = "http://localhost:"+puerto+"/api/cliente/1";
        HttpEntity<Cliente> requestCliente = new HttpEntity<>(clientes.get(0));

        ResponseEntity<Cliente> response = restTemplate.exchange(server, HttpMethod.PUT, requestCliente, Cliente.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getId(), 1);

        Mockito.verify(clienteService, times(1)).actualizarCliente(any(), any());
    }

    @Test
    void deberiaTirarUnaExcepcionPorClienteNoExistente3(){
        when(clienteService.actualizarCliente(any(Cliente.class), eq(2))).thenReturn(Optional.empty());
        String server = "http://localhost:"+puerto+"/api/cliente/2";
        HttpEntity<Cliente> requestCliente = new HttpEntity<>(clientes.get(0));

       Assertions.assertThrows(HttpClientErrorException.class, ()->{
           restTemplate.exchange(server, HttpMethod.PUT, requestCliente, Cliente.class);
       });

        Mockito.verify(clienteService, times(1)).actualizarCliente(any(), any());
    }

    @Test
    void deberiaBorrarElCliente(){
        when(clienteService.borrarCliente(1)).thenReturn(Optional.of(clientes.get(0)));
        String server = "http://localhost:"+puerto+"/api/cliente/1";

        ResponseEntity<Cliente> response = restTemplate.exchange(server, HttpMethod.DELETE, null, Cliente.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(response.getBody().getId(), 1);

        Mockito.verify(clienteService, times(1)).borrarCliente(any());
    }

    @Test
    void deberiaTirarUnaExcepcionPorClienteNoExistente4(){
        when(clienteService.borrarCliente(2)).thenReturn(Optional.empty());
        String server = "http://localhost:"+puerto+"/api/cliente/2";

        Assertions.assertThrows(HttpClientErrorException.class, ()->{
            restTemplate.exchange(server, HttpMethod.DELETE, null, Cliente.class);
        });

        Mockito.verify(clienteService, times(1)).borrarCliente(any());
    }
}
