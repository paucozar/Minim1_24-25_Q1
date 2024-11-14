package edu.upc.dsa.services;

import edu.upc.dsa.MapaManager;
import edu.upc.dsa.MapaManagerImpl;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.PuntoInteres;
import edu.upc.dsa.models.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/mapa", description = "Endpoint to Mapa Service")
@Path("/mapa")
public class MapaService {
    private MapaManager mm;

    public MapaService() {
        this.mm = MapaManagerImpl.getInstance();
        if (mm.size() == 0) {
            this.mm.addUsuario("1", "Juan", "Perez", "email1", "fecha1");
            this.mm.addUsuario("2", "Maria", "Lopez", "email2", "fecha2");
            this.mm.addUsuario("3", "Pedro", "Garcia", "email3", "fecha3");
            this.mm.addUsuario("4", "Laura", "Gomez", "email4", "fecha4");
            this.mm.addPuntoInteres(1, 1, ElementType.DOOR);
            this.mm.addPuntoInteres(2, 2, ElementType.BRIDGE);
            this.mm.addPuntoInteres(3, 3, ElementType.WALL);
            this.mm.addPuntoInteres(4, 4, ElementType.POTION);
        }
    }

    @POST
    @ApiOperation(value = "add nuevo usuario", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuario.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/usuario")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUsuario(Usuario usuario) {
        this.mm.addUsuario(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getFechaNacimiento());
        return Response.status(200).entity(usuario).build();
    }

    @GET
    @ApiOperation(value = "get all usuarios ordenados alfabeticamente", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuario.class, responseContainer="List"),
    })
    @Path("/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarioOrdenadoAlfabeticamente() {
        List<Usuario> usuarios = this.mm.getUsuarioOrdenadoAlfabeticamente();
        GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(usuarios) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get usuario", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuario.class),
            @ApiResponse(code = 404, message = "Usuario not found")
    })
    @Path("/usuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario(@PathParam("id") String id) {
        Usuario usuario = this.mm.getUsuario(id);
        return Response.status(200).entity(usuario).build();
    }

    @POST
    @ApiOperation(value = "add punto de interes", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = PuntoInteres.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/puntoInteres")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response addPuntoInteres(PuntoInteres p) {
        this.mm.addPuntoInteres(p.getHorizontal(), p.getVertical(), p.getType());
        return Response.status(200).entity(p).build();
    }

    @POST
    @ApiOperation(value = "registrar puntos de interes de usuario", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuario.class),
            @ApiResponse(code = 404, message = "Usuario not found"),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/usuario/{id}/puntoInteres")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarPuntoUsuario(@PathParam("id") String id, PuntoInteres p) {
        this.mm.registrarPuntoUsuario(id, p.getHorizontal(), p.getVertical());
        return Response.status(200).entity("Punto de interes registrado correctamente").build();
    }

    @GET
    @ApiOperation(value = "get puntos de interes de usuario", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = PuntoInteres.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Usuario not found")
    })
    @Path("/usuario/{id}/puntosInteres")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntosInteresUsuario(@PathParam("id") String id) {
        List<PuntoInteres> puntosInteres = this.mm.getPuntosInteresUsuario(id);
        GenericEntity<List<PuntoInteres>> entity = new GenericEntity<List<PuntoInteres>>(puntosInteres) {};
        return Response.status(200).entity(entity).build();
        }

    @GET
    @ApiOperation(value = "get usuarios que han pasado por un punto de interes", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuario.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Punto de interes not found")
    })
    @Path("/puntoInteres/{horizontal}/{vertical}/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuariosHanPasadoPorAqui(PuntoInteres puntoInteres) {
        List<Usuario> usuarios = this.mm.getUsuariosHanPasadoPorAqui(puntoInteres.getHorizontal(), puntoInteres.getVertical());
        GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(usuarios) {};
        return Response.status(200).entity(entity).build();
    }

    @GET
    @ApiOperation(value = "get puntos de interes por tipo", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = PuntoInteres.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Punto de interes not found")
    })
    @Path("/puntosInteres/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntosInteresPorTipo(@PathParam("type") ElementType type) {
        List<PuntoInteres> puntosInteres = this.mm.getPuntosInteresPorTipo(type);
        GenericEntity<List<PuntoInteres>> entity = new GenericEntity<List<PuntoInteres>>(puntosInteres) {};
        return Response.status(200).entity(entity).build();
    }
}
