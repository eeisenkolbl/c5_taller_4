package c5t4.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sesiones")
public class Sesion extends PanacheEntity {
    
    @ManyToOne
    @JoinColumn(name = "id_sala", nullable = false)
    public Sala sala;
    
    @ManyToOne
    @JoinColumn(name = "id_jugador", nullable = false)
    public Jugador jugador;
    
    @Column(name = "rol")
    public String rol;
    
    @Column(name = "estado")
    public String estado = "activo";
    
    @Column(name = "hora_entrada")
    public LocalDateTime horaEntrada;
    
    @Column(name = "hora_salida")
    public LocalDateTime horaSalida;
    
    @Column(name = "puntuacion")
    public Integer puntuacion = 0;
    
    @Column(name = "created_at", updatable = false)
    public LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
    
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (horaEntrada == null) {
            horaEntrada = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
