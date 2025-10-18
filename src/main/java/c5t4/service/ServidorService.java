package c5t4.service;

import c5t4.entity.Servidor;
import c5t4.repository.ServidorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ServidorService {
    
    @Inject
    ServidorRepository servidorRepository;
    
    public List<Servidor> listarTodos() {
        return servidorRepository.listAll();
    }
    
    public Servidor buscarPorId(Long id) {
        Servidor servidor = servidorRepository.findById(id);
        if (servidor == null) {
            throw new IllegalArgumentException("Servidor con ID " + id + " no encontrado");
        }
        return servidor;
    }
    
    @Transactional
    public Servidor crear(Servidor servidor) {
        if (servidor.nombre == null || servidor.nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (servidor.ipAddress == null || servidor.ipAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("La IP es obligatoria");
        }
        if (servidor.puerto == null) {
            throw new IllegalArgumentException("El puerto es obligatorio");
        }
        servidorRepository.persist(servidor);
        return servidor;
    }
    
    @Transactional
    public Servidor actualizar(Long id, Servidor servidorActualizado) {
        Servidor servidor = buscarPorId(id);
        if (servidorActualizado.nombre != null) {
            servidor.nombre = servidorActualizado.nombre;
        }
        if (servidorActualizado.ipAddress != null) {
            servidor.ipAddress = servidorActualizado.ipAddress;
        }
        if (servidorActualizado.puerto != null) {
            servidor.puerto = servidorActualizado.puerto;
        }
        if (servidorActualizado.region != null) {
            servidor.region = servidorActualizado.region;
        }
        if (servidorActualizado.ramTotalGb != null) {
            servidor.ramTotalGb = servidorActualizado.ramTotalGb;
        }
        if (servidorActualizado.ramDisponibleGb != null) {
            servidor.ramDisponibleGb = servidorActualizado.ramDisponibleGb;
        }
        if (servidorActualizado.coresTotales != null) {
            servidor.coresTotales = servidorActualizado.coresTotales;
        }
        if (servidorActualizado.coresDisponibles != null) {
            servidor.coresDisponibles = servidorActualizado.coresDisponibles;
        }
        if (servidorActualizado.almacenamientoTotalGb != null) {
            servidor.almacenamientoTotalGb = servidorActualizado.almacenamientoTotalGb;
        }
        if (servidorActualizado.almacenamientoDisponibleGb != null) {
            servidor.almacenamientoDisponibleGb = servidorActualizado.almacenamientoDisponibleGb;
        }
        if (servidorActualizado.estado != null) {
            servidor.estado = servidorActualizado.estado;
        }
        servidorRepository.persist(servidor);
        return servidor;
    }
    
    @Transactional
    public void eliminar(Long id) {
        Servidor servidor = buscarPorId(id);
        servidorRepository.delete(servidor);
    }
}
