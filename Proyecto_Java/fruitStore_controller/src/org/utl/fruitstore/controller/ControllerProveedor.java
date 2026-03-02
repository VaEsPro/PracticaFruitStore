package org.utl.fruitstore.controller;
/**
 *
 * @author oswal
 */

import org.utl.fruitstore.model.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.utl.fruitstore.db.ConnectionMySQL;

public class ControllerProveedor {
    public int insert(Proveedor pr) throws Exception {
        String sql = "INSERT INTO proveedor(nombre, razonSocial, rfc, direccion, email, telefonoFijo, telefonoMovil) VALUES (?, ?, ?, ?, ?, ?, ?)";
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ResultSet rs = null;
        
        pstmt.setString(1, pr.getNombre());
        pstmt.setString(2, pr.getRazonSocial());
        pstmt.setString(3, pr.getRfc());
        pstmt.setString(4, pr.getDireccion());
        pstmt.setString(5, pr.getEmail());
        pstmt.setString(6, pr.getTelefonoFijo());
        pstmt.setString(7, pr.getTelefonoMovil());
        
        pstmt.executeUpdate();
        rs = pstmt.getGeneratedKeys();
        if (rs.next()){
            pr.setId(rs.getInt(1));
        }
        rs.close();
        pstmt.close();
        conn.close();
        return pr.getId();
    }
    
    public void update(Proveedor pr) throws Exception {
        // Se agregaron todos los campos y el ID al final para el WHERE
        String sql = "UPDATE proveedor SET nombre=?, razonSocial=?, rfc=?, direccion=?, email=?, telefonoFijo=?, telefonoMovil=? WHERE idProveedor=?";
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, pr.getNombre());
        pstmt.setString(2, pr.getRazonSocial());
        pstmt.setString(3, pr.getRfc());
        pstmt.setString(4, pr.getDireccion());
        pstmt.setString(5, pr.getEmail());
        pstmt.setString(6, pr.getTelefonoFijo());
        pstmt.setString(7, pr.getTelefonoMovil());
        pstmt.setInt(8, pr.getId());

        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public void delete(int id) throws Exception {
        // Corregido: se cambió 'producto' por 'proveedor'
        String sql = "UPDATE proveedor SET estatus = 0 WHERE idProveedor=?";
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    public List<Proveedor> getAll(String filtro) throws Exception {
        String sql = "SELECT * FROM proveedor WHERE estatus=1 ORDER BY nombre ASC";
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        Connection conn = connMySQL.open();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        List<Proveedor> proveedores = new ArrayList<>();
        while (rs.next()) {
            proveedores.add(fill(rs));
        }
        rs.close();
        pstmt.close();
        conn.close();
        return proveedores;
    }

    private Proveedor fill(ResultSet rs) throws Exception {
        Proveedor pr = new Proveedor();
        pr.setId(rs.getInt("idProveedor"));
        pr.setNombre(rs.getString("nombre"));
        pr.setRazonSocial(rs.getString("razonSocial"));
        pr.setRfc(rs.getString("rfc"));
        pr.setDireccion(rs.getString("direccion"));
        pr.setEmail(rs.getString("email"));
        pr.setTelefonoFijo(rs.getString("telefonoFijo"));
        pr.setTelefonoMovil(rs.getString("telefonoMovil"));
        pr.setStatus(rs.getInt("estatus"));
        return pr;
    }
}