package com.utl.fruitstore.rest;

/**
 *
 * @author oswal
 */

import com.google.gson.Gson;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.util.List;
import com.utl.fruitstore.controller.ControllerVendedor;
import com.utl.fruitstore.model.Vendedor;

@Path("vendedor")
public class RESTVendedor {

    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {
        String out = null;
        List<Vendedor> vendedores = null;
        ControllerVendedor cp = new ControllerVendedor();
        Gson gson = new Gson();
        try {
            vendedores = cp.getAll(filtro);
            out = gson.toJson(vendedores);
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
    public Response save(@QueryParam("vendedor") @DefaultValue("") String datosVendedor) {
        // System.out.println(datosProducto);
        String out = null;
        ControllerVendedor cp = new ControllerVendedor();
        Gson gson = new Gson();
        Vendedor c = null;
        try {
            c = gson.fromJson(datosVendedor, Vendedor.class);
            if (c == null) {
                out = """
                      {
                      "error":"no se proporcionaron datos"
                      }
                      """;
            } else {
                if (c.getId() == 0) {
                    cp.insert(c);
                } else {
                    cp.update(c);
                }
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
        ControllerVendedor cp = new ControllerVendedor();
        Gson gson = new Gson();
        try {
            if (id < 1) {
                out = "{\"error\": \"ID de Vendedor no válido.\"}";
            } else {
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