package c5t4.repository;

import c5t4.entity.Juego;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JuegoRepository implements PanacheRepository<Juego> {
    
    public Juego findByNombre(String nombre) {
        return find("nombre", nombre).firstResult();
    }
}
