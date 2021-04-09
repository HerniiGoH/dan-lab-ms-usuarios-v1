package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.stereotype.Service;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Empleado;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.EmpleadoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
    //lista de los empleados creados
    private static final List<Empleado> listaEmpleados = new ArrayList<Empleado>();
    //generador de los id de los empleados
    private static Integer SEQ_ID = 0;

    @Override
    public List<Empleado> buscarTodos() {
        return listaEmpleados;
    }

    @Override
    public Optional<Empleado> buscarEmpleadoPorId(Integer id) {
        return listaEmpleados
                .stream()
                .filter(empleado_aux -> empleado_aux.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<List<Empleado>> buscarEmpleado(Integer id, String razonSocial, String mail) {
        return Optional.of(listaEmpleados
                .stream()
                .filter(empleado -> id==null || empleado.getId().equals(id))
                .filter(empleado -> razonSocial==null || empleado.getRazonSocial().equals(razonSocial))
                .filter(empleado -> mail==null || empleado.getMail()==null)
                .collect(Collectors.toList()));
    }

    @Override
    public Empleado crearEmpleado(Empleado nuevoEmpleado) {
        nuevoEmpleado.setId(SEQ_ID++);
        listaEmpleados.add(nuevoEmpleado);
        return nuevoEmpleado;
    }

    @Override
    public Optional<Empleado> actualizarEmpleado(Empleado nuevoEmpleado, Integer id) {
        OptionalInt optIndex = IntStream.range(0, listaEmpleados.size())
                .filter(pos -> listaEmpleados.get(pos).getId().equals(id))
                .findFirst();
        if(optIndex.isPresent()) {
            listaEmpleados.set(optIndex.getAsInt(), nuevoEmpleado);
            return Optional.of(nuevoEmpleado);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Boolean borrarEmpleado(Integer id) {
        OptionalInt optIndex = IntStream.range(0, listaEmpleados.size())
                .filter(pos -> listaEmpleados.get(pos).getId().equals(id))
                .findFirst();
        if(optIndex.isPresent()) {
            listaEmpleados.remove(optIndex.getAsInt());
            return true;
        } else {
            return false;
        }
    }
}
