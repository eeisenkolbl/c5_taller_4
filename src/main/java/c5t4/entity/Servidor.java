package c5t4.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "servidores")
public class Servidor extends PanacheEntity {
    
    @Column(name = "nombre", nullable = false)
    public String nombre;
    
    @Column(name = "ip_address", nullable = false)
    public String ipAddress;
    
    @Column(name = "puerto", nullable = false)
    public Integer puerto;
    
    @Column(name = "region")
    public String region;
    
    @Column(name = "ram_total_gb")
    public Integer ramTotalGb;
    
    @Column(name = "ram_disponible_gb")
    public Integer ramDisponibleGb;
    
    @Column(name = "cores_totales")
    public Integer coresTotales;
    
    @Column(name = "cores_disponibles")
    public Integer coresDisponibles;
    
    @Column(name = "almacenamiento_total_gb")
    public Integer almacenamientoTotalGb;
    
    @Column(name = "almacenamiento_disponible_gb")
    public Integer almacenamientoDisponibleGb;
    
    @Column(name = "estado")
    public String estado = "activo";
    
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
