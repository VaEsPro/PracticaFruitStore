package com.utl.fruitstore.rest;
/**
 *
 * @author oswal
 * FormParam funciona con peticiones post o update
 * QueryParam funciona con peticiones get
 */

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import com.utl.fruitstore.controller.ControllerUsuario;
import com.utl.fruitstore.model.Usuario;

// Query param solo funciona con Get
// POST, PUT, PUSH, DELETE
// FormData
@Path("usuario")
public class RESTUsuario {
    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("usuario") @DefaultValue("") String usuario, 
                          @FormParam("contrasenia") @DefaultValue("") String contrasenia)
    {
        String out = null;
        
        Usuario u = null;
        System.out.println("Usuario: " + usuario);
        System.out.println("Password: " + contrasenia);
        try 
        {
            ControllerUsuario cu = new ControllerUsuario();
            
            u = cu.validate(usuario, contrasenia);
            if(u != null)
            {
                out = new Gson().toJson(u);
            }
            else
            {
                out = """
                      {"error": "Nombre de usuario o contraseña incorrectos."}
                      """;
            }
        } 
        catch (Throwable e)
        {
            e.printStackTrace();
            out = """
                  {"exception": "%s"}
                  """;
            
            out = String.format(out, e.toString());
        }
        return Response.ok().entity(out).build();
    }
}