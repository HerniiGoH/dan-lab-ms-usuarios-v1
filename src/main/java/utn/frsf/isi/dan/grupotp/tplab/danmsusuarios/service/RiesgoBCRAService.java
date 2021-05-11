package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service;

import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.enumerations.RiesgoBCRA;

public interface RiesgoBCRAService {
    RiesgoBCRA obtenerRiesgoBCRA(Cliente cliente);
}
