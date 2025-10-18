package c5t4.resource;

import c5t4.entity.Sala;
import c5t4.service.SalaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/salas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalaResource {
    
    @Inject
    SalaService salaService;
    
    @GET
    public Response listarTodos() {
        List<Sala> salas = salaService.listarTodos();
        return Response.ok(salas).build();
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Sala sala = salaService.buscarPorId(id);
            return Response.ok(sala).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    
    @POST
    public Response crear(Sala sala) {
        try {
            Sala nuevaSala = salaService.crear(sala);
            return Response.status(Response.Status.CREATED)
                    .entity(nuevaSala)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, Sala sala) {
        try {
            Sala salaActualizada = salaService.actualizar(id, sala);
            return Response.ok(salaActualizada).build();
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
            salaService.eliminar(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    
    public static class ErrorResponse {
        public String message;
        public ErrorResponse(String message) {
            this.message = message;
        }
    }
}
