package c5t4.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "salas")
public class Sala extends PanacheEntity {
    
    @ManyToOne
    @JoinColumn(name = "id_juego", nullable = false)
    public Juego juego;
    
    @ManyToOne
    @JoinColumn(name = "id_servidor", nullable = false)
    public Servidor servidor;
    
    @ManyToOne
    @JoinColumn(name = "id_jugador_creador", nullable = false)
    public Jugador creador;
    
    @Column(name = "nombre_sala", nullable = false)
    public String nombreSala;
    
    @Column(name = "codigo_acceso", unique = true)
    public String codigoAcceso;
    
    @Column(name = "estado")
    public String estado = "esperando";
    
    @Column(name = "capacidad_maxima")
    public Integer capacidadMaxima;
    
    @Column(name = "jugadores_actuales")
    public Integer jugadoresActuales = 0;
    
    @Column(name = "hora_creacion")
    public LocalDateTime horaCreacion;
    
    @Column(name = "hora_inicio_juego")
    public LocalDateTime horaInicioJuego;
    
    @Column(name = "hora_fin_juego")
    public LocalDateTime horaFinJuego;
    
    @Column(name = "created_at", updatable = false)
    public LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
    
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (horaCreacion == null) {
            horaCreacion = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
