package c5t4.entity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "jugadores")
public class Jugador extends PanacheEntity {
    
    @Column(name = "username", nullable = false, unique = true)
    public String username;
    
    @Column(name = "email", nullable = false, unique = true)
    public String email;
    
    @Column(name = "password_hash", nullable = false)
    public String passwordHash;
    
    @Column(name = "nivel")
    public Integer nivel = 1;
    
    @Column(name = "estado_conexion")
    public String estadoConexion = "desconectado";
    
    @Column(name = "ultima_conexion")
    public LocalDateTime ultimaConexion;
    
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