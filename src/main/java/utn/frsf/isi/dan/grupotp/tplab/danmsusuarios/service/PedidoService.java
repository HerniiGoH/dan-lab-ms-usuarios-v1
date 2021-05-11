package utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.service;

import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.Cliente;
import utn.frsf.isi.dan.grupotp.tplab.danmsusuarios.model.DTO.PedidoDTO;
//soy un easter egg :D
public interface PedidoService {
    PedidoDTO getPedidos(Cliente cliente);
}
