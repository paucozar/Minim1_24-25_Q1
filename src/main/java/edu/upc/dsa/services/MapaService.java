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
    public Response addUsuario(@PathParam("id") String id, @PathParam("nombre") String nombre, @PathParam("apellido") String apellido, @PathParam("email") String email, @PathParam("fechaNacimiento") String fechaNacimiento) {
        if (id == null || nombre == null || apellido == null || email == null || fechaNacimiento == null)
            return Response.status(500).entity("Validation Error: Missing parameters").build();
        Usuario usuario = this.mm.addUsuario(id, nombre, apellido, email, fechaNacimiento);
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
        if (usuario == null) return Response.status(404).build();
        else return Response.status(200).entity(usuario).build();
    }

    @POST
    @ApiOperation(value = "add punto de interes", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = PuntoInteres.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/puntoInteres")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPuntoInteres(@PathParam("horizontal") int horizontal, @PathParam("vertical") int vertical, @PathParam("type") ElementType type) {
        if (horizontal < 0 || vertical < 0 || type == null)
            return Response.status(500).entity(null).build();
        PuntoInteres puntoInteres = this.mm.addPuntoInteres(horizontal, vertical, type);
        return Response.status(200).entity(puntoInteres).build();
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
    public Response registrarPuntoUsuario(@PathParam("id") String id, @PathParam("horizontal") int horizontal, @PathParam("vertical") int vertical) {
        if (id == null || horizontal < 0 || vertical < 0)
            return Response.status(500).entity(null).build();
        Usuario usuario = this.mm.registrarPuntoUsuario(id, horizontal, vertical);
        if (usuario == null) return Response.status(404).build();
        else return Response.status(200).entity(usuario).build();
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
        if (puntosInteres == null) return Response.status(404).build();
        else {
            GenericEntity<List<PuntoInteres>> entity = new GenericEntity<List<PuntoInteres>>(puntosInteres) {};
            return Response.status(200).entity(entity).build();
        }
    }

    @GET
    @ApiOperation(value = "get usuarios que han pasado por un punto de interes", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuario.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Punto de interes not found")
    })
    @Path("/puntoInteres/{horizontal}/{vertical}/usuarios")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuariosHanPasadoPorAqui(@PathParam("horizontal") int horizontal, @PathParam("vertical") int vertical) {
        List<Usuario> usuarios = this.mm.getUsuariosHanPasadoPorAqui(horizontal, vertical);
        if (usuarios == null) return Response.status(404).build();
        else {
            GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(usuarios) {};
            return Response.status(200).entity(entity).build();
        }
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
        if (puntosInteres == null) return Response.status(404).build();
        else {
            GenericEntity<List<PuntoInteres>> entity = new GenericEntity<List<PuntoInteres>>(puntosInteres) {};
            return Response.status(200).entity(entity).build();
        }
    }
}
