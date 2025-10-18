package c5t4.repository;

import c5t4.entity.Servidor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ServidorRepository implements PanacheRepository<Servidor> {
    
    public List<Servidor> findByRegion(String region) {
        return list("region", region);
    }
    
    public List<Servidor> findByEstado(String estado) {
        return list("estado", estado);
    }
}
