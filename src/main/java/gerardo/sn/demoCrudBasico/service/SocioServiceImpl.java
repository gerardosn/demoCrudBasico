package gerardo.sn.demoCrudBasico.service;

//public class SocioServiceImpl {
//}
import gerardo.sn.demoCrudBasico.model.Socio;
import gerardo.sn.demoCrudBasico.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocioServiceImpl implements SocioService {

    @Autowired
    private SocioRepository socioRepository;

    @Override
    public Socio guardarSocio(Socio socio) {
        return socioRepository.save(socio);
    }

    @Override
    public List<Socio> obtenerTodosSocios() {
        return socioRepository.findAll();
    }

    @Override
    public Socio obtenerSocioPorId(Long id) {
        return socioRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarSocio(Long id) {
        socioRepository.deleteById(id);
    }
}