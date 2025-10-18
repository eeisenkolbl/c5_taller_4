package c5t4.resource;

import c5t4.entity.Servidor;
import c5t4.service.ServidorService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/servidores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServidorResource {
    
    @Inject
    ServidorService servidorService;
    
    @GET
    public Response listarTodos() {
        List<Servidor> servidores = servidorService.listarTodos();
        return Response.ok(servidores).build();
    }
    
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        try {
            Servidor servidor = servidorService.buscarPorId(id);
            return Response.ok(servidor).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    
    @POST
    public Response crear(Servidor servidor) {
        try {
            Servidor nuevoServidor = servidorService.crear(servidor);
            return Response.status(Response.Status.CREATED)
                    .entity(nuevoServidor)
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, Servidor servidor) {
        try {
            Servidor servidorActualizado = servidorService.actualizar(id, servidor);
            return Response.ok(servidorActualizado).build();
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
            servidorService.eliminar(id);
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
