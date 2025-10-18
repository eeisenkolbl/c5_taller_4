package c5t4.repository;

import c5t4.entity.Sala;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class SalaRepository implements PanacheRepository<Sala> {
    
    public List<Sala> findByEstado(String estado) {
        return list("estado", estado);
    }
    
    public Sala findByCodigoAcceso(String codigoAcceso) {
        return find("codigoAcceso", codigoAcceso).firstResult();
    }
}
