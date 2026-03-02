package org.utl.fruitstore.controller;
/**
 *
 * @author oswal
 */

import org.utl.fruitstore.model.Usuario;
import org.utl.fruitstore.model.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.utl.fruitstore.db.ConnectionMySQL;

public class ControllerUsuario {
    public Usuario validate(String nombre, String contrasenia) throws Exception {
        String sql = "SELECT * FROM v_usuario WHERE nombreUsuario=? AND contrasenia=?";
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = null;
        Usuario u = null;
        
        pstmt.setString(1, nombre);
        pstmt.setString(2, contrasenia);
        
        rs = pstmt.executeQuery();
        
        if (rs.next())
            u = fill(rs);
        
        rs.close();
        pstmt.close();
        conn.close();
        
        return u;
    }
    
    private Usuario fill(ResultSet rs) throws Exception {
        Usuario u = new Usuario();
        Vendedor v = new Vendedor();
        
        v.setCalle(rs.getString("calle"));
        v.setCiudad(rs.getString("ciudad"));
        v.setColonia(rs.getString("colonia"));
        v.setCp(rs.getString("cp"));
        v.setEmail(rs.getString("email"));
        v.setEstado(rs.getString("estado"));
        v.setStatus(rs.getInt("estatus"));
        v.setFechaAlta(rs.getString("fechaAlta"));
        v.setFechaNac(rs.getString("fechaNac"));
        v.setGenero(rs.getString("genero"));
        v.setId(rs.getInt("idVendedor"));
        v.setNombre(rs.getString("nombre"));
        v.setNumExt(rs.getString("numExt"));
        v.setNumInt(rs.getString("numInt"));
        v.setPais(rs.getString("pais"));
        v.setTelefono(rs.getString("telefono"));
        
        u.setContrasenia(rs.getString("contrasenia"));
        u.setId(rs.getInt("idUsuario"));
        u.setNombre(rs.getString("nombreUsuario"));
        u.setVendedor(v);
        
        return u;
    }
}