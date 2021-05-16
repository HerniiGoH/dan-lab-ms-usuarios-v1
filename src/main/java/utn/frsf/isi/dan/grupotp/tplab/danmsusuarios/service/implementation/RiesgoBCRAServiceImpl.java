package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.implementation;

import org.springframework.stereotype.Service;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.enumerations.RiesgoBCRA;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service.RiesgoBCRAService;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class RiesgoBCRAServiceImpl implements RiesgoBCRAService {

    @Override
    public RiesgoBCRA obtenerRiesgoBCRA(Cliente cliente) {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 2);
        return RiesgoBCRA.values()[randomNum];
    }
}
