package c5t4.service;

import c5t4.entity.Sala;
import c5t4.entity.Juego;
import c5t4.entity.Servidor;
import c5t4.entity.Jugador;
import c5t4.repository.SalaRepository;
import c5t4.repository.JuegoRepository;
import c5t4.repository.ServidorRepository;
import c5t4.repository.JugadorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SalaService {
    
    @Inject
    SalaRepository salaRepository;
    
    @Inject
    JuegoRepository juegoRepository;
    
    @Inject
    ServidorRepository servidorRepository;
    
    @Inject
    JugadorRepository jugadorRepository;
    
    public List<Sala> listarTodos() {
        return salaRepository.listAll();
    }
    
    public Sala buscarPorId(Long id) {
        Sala sala = salaRepository.findById(id);
        if (sala == null) {
            throw new IllegalArgumentException("Sala con ID " + id + " no encontrada");
        }
        return sala;
    }
    
    @Transactional
    public Sala crear(Sala sala) {
        if (sala.nombreSala == null || sala.nombreSala.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de sala es obligatorio");
        }
        if (sala.juego == null || sala.juego.id == null) {
            throw new IllegalArgumentException("El juego es obligatorio");
        }
        if (sala.servidor == null || sala.servidor.id == null) {
            throw new IllegalArgumentException("El servidor es obligatorio");
        }
        if (sala.creador == null || sala.creador.id == null) {
            throw new IllegalArgumentException("El creador es obligatorio");
        }
        
        Juego juego = juegoRepository.findById(sala.juego.id);
        if (juego == null) {
            throw new IllegalArgumentException("El juego no existe");
        }
        Servidor servidor = servidorRepository.findById(sala.servidor.id);
        if (servidor == null) {
            throw new IllegalArgumentException("El servidor no existe");
        }
        Jugador creador = jugadorRepository.findById(sala.creador.id);
        if (creador == null) {
            throw new IllegalArgumentException("El jugador creador no existe");
        }
        
        sala.juego = juego;
        sala.servidor = servidor;
        sala.creador = creador;
        
        salaRepository.persist(sala);
        return sala;
    }
    
    @Transactional
    public Sala actualizar(Long id, Sala salaActualizada) {
        Sala sala = buscarPorId(id);
        if (salaActualizada.nombreSala != null) {
            sala.nombreSala = salaActualizada.nombreSala;
        }
        if (salaActualizada.estado != null) {
            sala.estado = salaActualizada.estado;
        }
        if (salaActualizada.capacidadMaxima != null) {
            sala.capacidadMaxima = salaActualizada.capacidadMaxima;
        }
        if (salaActualizada.jugadoresActuales != null) {
            sala.jugadoresActuales = salaActualizada.jugadoresActuales;
        }
        if (salaActualizada.horaInicioJuego != null) {
            sala.horaInicioJuego = salaActualizada.horaInicioJuego;
        }
        if (salaActualizada.horaFinJuego != null) {
            sala.horaFinJuego = salaActualizada.horaFinJuego;
        }
        salaRepository.persist(sala);
        return sala;
    }
    
    @Transactional
    public void eliminar(Long id) {
        Sala sala = buscarPorId(id);
        salaRepository.delete(sala);
    }
}
