package c5t4.repository;

import c5t4.entity.Sesion;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class SesionRepository implements PanacheRepository<Sesion> {
    
    public List<Sesion> findByEstado(String estado) {
        return list("estado", estado);
    }
}
