package gerardo.sn.demoCrudBasico.service;

//public class SocioService {
//}
import gerardo.sn.demoCrudBasico.model.Socio;

import java.util.List;

public interface SocioService {
    Socio guardarSocio(Socio socio);
    List<Socio> obtenerTodosSocios();
    Socio obtenerSocioPorId(Long id);
    void eliminarSocio(Long id);
}