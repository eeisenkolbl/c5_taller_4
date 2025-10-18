package c5t4.service;

import c5t4.entity.Juego;
import c5t4.repository.JuegoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class JuegoService {
    
    @Inject
    JuegoRepository juegoRepository;
    
    public List<Juego> listarTodos() {
        return juegoRepository.listAll();
    }
    
    public Juego buscarPorId(Long id) {
        Juego juego = juegoRepository.findById(id);
        if (juego == null) {
            throw new IllegalArgumentException("Juego con ID " + id + " no encontrado");
        }
        return juego;
    }
    
    @Transactional
    public Juego crear(Juego juego) {
        if (juego.nombre == null || juego.nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (juegoRepository.findByNombre(juego.nombre) != null) {
            throw new IllegalArgumentException("El nombre ya existe");
        }
        juegoRepository.persist(juego);
        return juego;
    }
    
    @Transactional
    public Juego actualizar(Long id, Juego juegoActualizado) {
        Juego juego = buscarPorId(id);
        if (juegoActualizado.nombre != null) {
            juego.nombre = juegoActualizado.nombre;
        }
        if (juegoActualizado.genero != null) {
            juego.genero = juegoActualizado.genero;
        }
        if (juegoActualizado.desarrollador != null) {
            juego.desarrollador = juegoActualizado.desarrollador;
        }
        if (juegoActualizado.ramRecomendadaGb != null) {
            juego.ramRecomendadaGb = juegoActualizado.ramRecomendadaGb;
        }
        if (juegoActualizado.coresRecomendados != null) {
            juego.coresRecomendados = juegoActualizado.coresRecomendados;
        }
        if (juegoActualizado.almacenamientoGb != null) {
            juego.almacenamientoGb = juegoActualizado.almacenamientoGb;
        }
        if (juegoActualizado.esVr != null) {
            juego.esVr = juegoActualizado.esVr;
        }
        juegoRepository.persist(juego);
        return juego;
    }
    
    @Transactional
    public void eliminar(Long id) {
        Juego juego = buscarPorId(id);
        juegoRepository.delete(juego);
    }
}
