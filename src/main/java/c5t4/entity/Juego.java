package c5t4.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "juegos")
public class Juego extends PanacheEntity {
    
    @Column(name = "nombre", nullable = false, unique = true)
    public String nombre;
    
    @Column(name = "genero")
    public String genero;
    
    @Column(name = "desarrollador")
    public String desarrollador;
    
    @Column(name = "ram_recomendada_gb")
    public Integer ramRecomendadaGb;
    
    @Column(name = "cores_recomendados")
    public Integer coresRecomendados;
    
    @Column(name = "almacenamiento_gb")
    public Integer almacenamientoGb;
    
    @Column(name = "es_vr")
    public Boolean esVr = false;
    
    @Column(name = "created_at", updatable = false)
    public LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
    
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
