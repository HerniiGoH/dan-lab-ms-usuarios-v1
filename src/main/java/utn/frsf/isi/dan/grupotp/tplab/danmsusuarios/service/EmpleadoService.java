package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service;

import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    List<Empleado> buscarTodos();
    Optional<Empleado> buscarEmpleadoPorId(Integer id);
    Optional<List<Empleado>> buscarEmpleado(Integer id, String razonSocial, String mail);
    Empleado crearEmpleado(Empleado nuevoEmpleado);
    Optional<Empleado> actualizarEmpleado(Empleado nuevoEmpleado, Integer id);
    Boolean borrarEmpleado(Integer id);
}
