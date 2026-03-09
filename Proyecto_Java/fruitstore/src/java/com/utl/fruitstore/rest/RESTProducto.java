package com.utl.fruitstore.rest;
/**
 *
 * @author oswal
 */

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import com.utl.fruitstore.controller.ControllerProducto;
import com.utl.fruitstore.model.Producto;

@Path("producto")
public class RESTProducto {
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro){
        String out = null;
        List<Producto> productos = null;
        ControllerProducto cp = new ControllerProducto();
        Gson gson = new Gson();
        try {
            productos = cp.getAll(filtro);
            out = gson.toJson(productos);
        } catch (Exception e){
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
    public Response save(@QueryParam("producto") @DefaultValue("") String datosProducto){
        System.out.println(datosProducto);
        String out = null;
        ControllerProducto cp = new ControllerProducto();
        Gson gson = new Gson();
        Producto p = null;
        try {
            p = gson.fromJson(datosProducto, Producto.class);
            if (p == null){
                out = """
                      {
                      "error":"no se proporcionaron datos"
                      }
                      """;
            } else {
                if (p.getId() == 0){
                    cp.insert(p);
                } else {
                    cp.update(p);
                }
                out = new Gson().toJson(p);
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
    public Response delete(@QueryParam("id") @DefaultValue("0") int id) {
        String out = null;
        ControllerProducto cp = new ControllerProducto();
        Gson gson = new Gson();
        Producto p = null;
        try {
            if (id < 1) {
                out = """
                      {
                      "error":"no se proporcionaron datos"
                      }
                      """;
            } else {
                cp.delete(id);
                out = """
                      {
                      "result":"OK"
                      }
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
