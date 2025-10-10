## 1. 

### Definición del patrón Repository

El patrón Repository es un patrón de diseño que actúa como una capa de abstracción entre la lógica de negocio de una aplicación y la capa de acceso a datos. Su propósito principal es encapsular la lógica necesaria para acceder a las fuentes de datos, proporcionando una interfaz más orientada a objetos para manipular datos.

### Problema que resuelve

El patrón Repository resuelve varios problemas fundamentales:

- **Acoplamiento directo**: Evita que la lógica de negocio esté fuertemente acoplada con la tecnología de persistencia específica (JPA, JDBC, etc.)
- **Duplicación de código**: Centraliza las consultas y operaciones de acceso a datos, eliminando código repetitivo
- **Testabilidad**: Facilita las pruebas unitarias al permitir crear implementaciones mock del repositorio
- **Mantenibilidad**: Concentra los cambios relacionados con acceso a datos en un solo lugar

### Ejemplo conceptual del flujo

```
Cliente/Controller → Service (Lógica de Negocio) → Repository (Acceso a Datos) → Base de Datos
```

**Flujo de ejemplo:**

1. El **Controller** recibe una petición HTTP para obtener un producto por ID
2. El **Service** valida la petición y llama al método del Repository
3. El **Repository** ejecuta la consulta a la base de datos
4. Los datos regresan por el mismo camino: Repository → Service → Controller → Cliente

---

## 2. Ventajas del Patrón Repository

### 1. Separación de responsabilidades
El patrón Repository separa claramente la lógica de acceso a datos de la lógica de negocio, permitiendo que cada capa se enfoque en su responsabilidad específica. Esto hace que el código sea más organizado y fácil de entender.

### 2. Facilita el testing
Al abstraer el acceso a datos, es posible crear implementaciones mock o stub del repositorio para realizar pruebas unitarias sin necesidad de una base de datos real. Esto acelera las pruebas y las hace más confiables.

### 3. Reutilización de código
Las consultas comunes (buscar por ID, listar todos, buscar por criterio) se definen una sola vez en el repositorio y pueden ser reutilizadas en múltiples servicios, evitando duplicación de código.

### 4. Mantenibilidad mejorada
Si se necesita cambiar la tecnología de persistencia o modificar consultas, solo es necesario actualizar el repositorio sin afectar las capas superiores de la aplicación.

### 5. Abstracción de la complejidad
El repositorio oculta los detalles técnicos de cómo se accede y manipula la base de datos, exponiendo una interfaz simple y orientada al dominio del negocio.

### 6. Compatibilidad con Quarkus Panache
En Quarkus, el uso de Panache Repository simplifica aún más el desarrollo al proporcionar métodos predefinidos para operaciones CRUD, reduciendo el código boilerplate.

---

## 3. Desventajas del Patrón Repository

### 1. Complejidad adicional en proyectos simples
Para aplicaciones pequeñas con operaciones CRUD básicas, implementar el patrón Repository puede agregar capas innecesarias de abstracción, aumentando la complejidad sin beneficios significativos.

### 2. Curva de aprendizaje
Los desarrolladores nuevos en el equipo o sin experiencia en arquitectura en capas pueden encontrar confuso el flujo entre entidad, repositorio, servicio y controlador, requiriendo tiempo adicional de capacitación.

### 3. Posible sobre-ingeniería
Existe el riesgo de crear repositorios demasiado genéricos o con métodos que nunca se utilizan, lo que resulta en código innecesario que debe mantenerse. Es importante diseñar repositorios enfocados en las necesidades reales del negocio.

---

## 4. 

### Nombre del proyecto
**Sistema para la Gestión de Servidores de Videojuegos**

### Descripción de la entidad principal
La entidad principal del proyecto representa un **Servidor de Videojuegos**, que contiene información sobre servidores disponibles para que los jugadores puedan conectarse. Cada servidor tiene propiedades como nombre, juego asociado, capacidad máxima, jugadores actuales, región y estado (activo, inactivo, en mantenimiento).

### Ejemplo de código del repositorio

**Entidad Servidor:**

```java
package com.videogame.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Servidor extends PanacheEntity {
    
    public String nombre;
    public String juego;
    public Integer capacidadMaxima;
    public Integer jugadoresActuales;
    public String region;
    
    @Enumerated(EnumType.STRING)
    public EstadoServidor estado;
    
    public enum EstadoServidor {
        ACTIVO, INACTIVO, MANTENIMIENTO
    }
}
```

**Repositorio con Panache:**

```java
package com.videogame.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import com.videogame.model.Servidor;
import com.videogame.model.Servidor.EstadoServidor;

import java.util.List;

@ApplicationScoped
public class ServidorRepository implements PanacheRepository<Servidor> {
    
    // Métodos personalizados
    
    public List<Servidor> findByJuego(String juego) {
        return list("juego", juego);
    }
    
    public List<Servidor> findByRegion(String region) {
        return list("region", region);
    }
    
    public List<Servidor> findByEstado(EstadoServidor estado) {
        return list("estado", estado);
    }
    
    public List<Servidor> findDisponibles() {
        return list("estado = ?1 and jugadoresActuales < capacidadMaxima", 
                    EstadoServidor.ACTIVO);
    }
    
    public long countByJuego(String juego) {
        return count("juego", juego);
    }
}
```

**Service que utiliza el Repository:**

```java
package com.videogame.service;

import com.videogame.model.Servidor;
import com.videogame.repository.ServidorRepository;
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
    
    public Servidor obtenerPorId(Long id) {
        return servidorRepository.findById(id);
    }
    
    public List<Servidor> buscarDisponibles() {
        return servidorRepository.findDisponibles();
    }
    
    @Transactional
    public Servidor crear(Servidor servidor) {
        servidorRepository.persist(servidor);
        return servidor;
    }
    
    @Transactional
    public void eliminar(Long id) {
        servidorRepository.deleteById(id);
    }
}
```