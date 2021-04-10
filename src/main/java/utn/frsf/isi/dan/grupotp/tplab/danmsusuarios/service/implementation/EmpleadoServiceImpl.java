package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Empleado;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.repositories.EmpleadoRepository;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.EmpleadoService;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;

    @Override
    public List<Empleado> buscarTodos() {
        return empleadoRepository.findAll();
    }

    @Override
    public Optional<Empleado> buscarEmpleadoPorId(Integer id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public Optional<List<Empleado>> buscarEmpleado(Integer id, String razonSocial, String mail) {
        return empleadoRepository.findAllByQuery(id, razonSocial, mail);
    }

    @Override
    public Empleado crearEmpleado(Empleado nuevoEmpleado) {
        return empleadoRepository.save(nuevoEmpleado);
    }

    @Override
    public Optional<Empleado> actualizarEmpleado(Empleado nuevoEmpleado, Integer id) {
        if(empleadoRepository.existsById(id)){
            return Optional.of(empleadoRepository.save(nuevoEmpleado));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Boolean borrarEmpleado(Integer id) {
        if(empleadoRepository.existsById(id)){
            empleadoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
