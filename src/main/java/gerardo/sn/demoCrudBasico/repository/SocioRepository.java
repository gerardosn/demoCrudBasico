package gerardo.sn.demoCrudBasico.repository;

//public class SocioRepository {
//}
import gerardo.sn.demoCrudBasico.model.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
}