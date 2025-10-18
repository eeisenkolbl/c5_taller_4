package c5t4.service;

import c5t4.entity.Sesion;
import c5t4.entity.Sala;
import c5t4.entity.Jugador;
import c5t4.repository.SesionRepository;
import c5t4.repository.SalaRepository;
import c5t4.repository.JugadorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SesionService {
    
    @Inject
    SesionRepository sesionRepository;
    
    @Inject
    SalaRepository salaRepository;
    
    @Inject
    JugadorRepository jugadorRepository;
    
    public List<Sesion> listarTodos() {
        return sesionRepository.listAll();
    }
    
  public Sesion buscarPorId(Long id) {
    Sesion sesion = sesionRepository.findById(id);
    if (sesion == null) {  
        throw new IllegalArgumentException("Sesion con ID " + id + " no encontrada");
    }
    return sesion;
}
    
    @Transactional
    public Sesion crear(Sesion sesion) {
        if (sesion.sala == null || sesion.sala.id == null) {
            throw new IllegalArgumentException("La sala es obligatoria");
        }
        if (sesion.jugador == null || sesion.jugador.id == null) {
            throw new IllegalArgumentException("El jugador es obligatorio");
        }
        
        Sala sala = salaRepository.findById(sesion.sala.id);
        if (sala == null) {
            throw new IllegalArgumentException("La sala no existe");
        }
        Jugador jugador = jugadorRepository.findById(sesion.jugador.id);
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no existe");
        }
        
        sesion.sala = sala;
        sesion.jugador = jugador;
        
        sesionRepository.persist(sesion);
        return sesion;
    }
    
    @Transactional
    public Sesion actualizar(Long id, Sesion sesionActualizada) {
        Sesion sesion = buscarPorId(id);
        if (sesionActualizada.rol != null) {
            sesion.rol = sesionActualizada.rol;
        }
        if (sesionActualizada.estado != null) {
            sesion.estado = sesionActualizada.estado;
        }
        if (sesionActualizada.horaSalida != null) {
            sesion.horaSalida = sesionActualizada.horaSalida;
        }
        if (sesionActualizada.puntuacion != null) {
            sesion.puntuacion = sesionActualizada.puntuacion;
        }
        sesionRepository.persist(sesion);
        return sesion;
    }
    
    @Transactional
    public void eliminar(Long id) {
        Sesion sesion = buscarPorId(id);
        sesionRepository.delete(sesion);
    }
}
