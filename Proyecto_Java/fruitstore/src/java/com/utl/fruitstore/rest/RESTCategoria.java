package com.utl.fruitstore.rest;
/**
 *
 * @author oswal
 */

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import org.utl.fruitstore.controller.ControllerCategoria;
import org.utl.fruitstore.model.Categoria;

@Path("categoria")
public class RESTCategoria {
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro){
        String out = null;
        List<Categoria> categorias = null;
        ControllerCategoria cc = new ControllerCategoria();
        Gson gson = new Gson();
        try {
            categorias = cc.getAll(filtro);
            out = gson.toJson(categorias);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {
                  "exception":"%s"
                  }
                  """;
            out = String.format(out, e.toString().replaceAll("\"", ""));
        }
        return Response.ok(out).build();
    }
    
    @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@QueryParam("categoria") @DefaultValue("") String datosCategoria) {
        System.out.println(datosCategoria);
        String out = null;
        ControllerCategoria cp = new ControllerCategoria();
        Gson gson = new Gson();
        Categoria c = null;
        try {
            c = gson.fromJson(datosCategoria, Categoria.class);
            if (c == null) {
                out = """
                      {
                      "error":"no se proporcionaron datos"
                      }
                      """;
            } else 
            {
                if (c.getId() == 0) 
                    cp.insert(c);
                else 
                    cp.update(c);
                out = new Gson().toJson(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {
                  "exception":"%s"
                  }
                  """;
            out = String.format(out, e.toString().replaceAll("\"", ""));
        }
        return Response.ok(out).build();
    }
    
    @POST
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@QueryParam("id") @DefaultValue("0") int id) {
        String out = null;
        ControllerCategoria cp = new ControllerCategoria();
        Gson gson = new Gson();
        try {
            if(id < 1)
            {
                out = "{\"error\": \"ID de Categoria no válido.\"}";
            }
            else
            {
                cp.delete(id);
                out = """
                      {"result": "OK"}
                      """;
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {
                  "exception":"%s"
                  }
                  """;
            out = String.format(out, e.toString().replaceAll("\"", ""));
        }
        return Response.ok(out).build();
    }
}