package c5t4.repository;

import c5t4.entity.Jugador;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class JugadorRepository implements PanacheRepository<Jugador> {
    
    public Jugador findByUsername(String username) {
        return find("username", username).firstResult();
    }
    
    public Jugador findByEmail(String email) {
        return find("email", email).firstResult();
    }
}

