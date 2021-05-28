package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.unittests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

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
}
