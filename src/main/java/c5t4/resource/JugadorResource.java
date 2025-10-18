package c5t4.resource;

import c5t4.entity.Jugador;
import c5t4.service.JugadorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/jugadores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JugadorResource {
    
    @Inject
    JugadorService jugadorService;
    
    @GET
    public Response listarTodos() {
        List<Jugador> jugadores = jugadorService.listarTodos();
        return Response.ok(jugadores).build();
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Jugador jugador = jugadorService.buscarPorId(id);
            return Response.ok(jugador).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    
    @POST
    public Response crear(Jugador jugador) {
        try {
            Jugador nuevoJugador = jugadorService.crear(jugador);
            return Response.status(Response.Status.CREATED)
                    .entity(nuevoJugador)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, Jugador jugador) {
        try {
            Jugador jugadorActualizado = jugadorService.actualizar(id, jugador);
            return Response.ok(jugadorActualizado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        try {
            jugadorService.eliminar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    
    // Clase interna para respuestas de error
    public static class ErrorResponse {
        public String message;
        
        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}