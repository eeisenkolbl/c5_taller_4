package c5t4.service;

import c5t4.entity.Jugador;
import c5t4.repository.JugadorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class JugadorService {
    
    @Inject
    JugadorRepository jugadorRepository;
    
    public List<Jugador> listarTodos() {
        return jugadorRepository.listAll();
    }
    
    public Jugador buscarPorId(Long id) {
        Jugador jugador = jugadorRepository.findById(id);
        if (jugador == null) {
            throw new IllegalArgumentException("Jugador con ID " + id + " no encontrado");
        }
        return jugador;
    }
    
    @Transactional
    public Jugador crear(Jugador jugador) {
        // Validar campos obligatorios
        if (jugador.username == null || jugador.username.trim().isEmpty()) {
            throw new IllegalArgumentException("El username es obligatorio");
        }
        if (jugador.email == null || jugador.email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        
        // Validar que el username no exista
        if (jugadorRepository.findByUsername(jugador.username) != null) {
            throw new IllegalArgumentException("El username ya existe");
        }
        
        // Validar que el email no exista
        if (jugadorRepository.findByEmail(jugador.email) != null) {
            throw new IllegalArgumentException("El email ya existe");
        }
        
        jugadorRepository.persist(jugador);
        return jugador;
    }
    
    @Transactional
    public Jugador actualizar(Long id, Jugador jugadorActualizado) {
        Jugador jugador = buscarPorId(id);
        
        // Actualizar campos
        if (jugadorActualizado.username != null) {
            jugador.username = jugadorActualizado.username;
        }
        if (jugadorActualizado.email != null) {
            jugador.email = jugadorActualizado.email;
        }
        if (jugadorActualizado.nivel != null) {
            jugador.nivel = jugadorActualizado.nivel;
        }
        if (jugadorActualizado.estadoConexion != null) {
            jugador.estadoConexion = jugadorActualizado.estadoConexion;
        }
        
        jugadorRepository.persist(jugador);
        return jugador;
    }
    
    @Transactional
    public void eliminar(Long id) {
        Jugador jugador = buscarPorId(id);
        jugadorRepository.delete(jugador);
    }
}