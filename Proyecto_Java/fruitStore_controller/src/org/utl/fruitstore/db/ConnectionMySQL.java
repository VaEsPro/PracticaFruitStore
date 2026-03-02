package org.utl.fruitstore.db;
/**
 *
 * @author oswal
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class ConnectionMySQL {
    Connection conn;
    
    public Connection open() throws Exception{
        //Definimos las credenciales de Acceso a MySQL:
        String usuario = "root";
        String contrasenia = "root";
        
        //Definimos la ruta de conexión:
        String url = "jdbc:mysql://127.0.0.1:3306/fruit_store?characterEncoding=utf-8";
        
        //Registramos el Driver JDBC de MySQL:
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        //Abrimos la conexión con el servidor:
        conn = DriverManager.getConnection(url, usuario, contrasenia);
        return conn;
    }
    public void close() throws Exception{
        if (conn != null){
            conn.close();
        }
    }
}
