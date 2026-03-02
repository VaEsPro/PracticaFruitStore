package org.utl.fruitstore.controller;

import org.utl.fruitstore.db.ConnectionMySQL;
import org.utl.fruitstore.model.Vendedor;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author oswal
 */
public class ControllerVendedor {
    /**
     * Inserta un registro en la tabla [vendedor].
     * 
     * @param v Es un objeto de tipo Vendedor con los valores en los atributos
     *          correspondientes.
     * @return  Devuelve el ID de vendedor que se genero al realizar 
     *          la insercion.
     * @throws Exception    Se lanza una excepcion cuando ocurre un fallo en la
     *                      comunicacion con la Base de Datos o se altero de
     *                      forma erronea una sentencia SQL.
     */
    public int insert(Vendedor v) throws Exception
    {
        // Se define la consulta SQL:
        String sql = "INSERT INTO vendedor(nombre, fechaNac, genero, calle, numExt,"
                + "numInt, colonia, cp, ciudad, estado, pais, telefono, fechaAlta, email) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        // Se crea un objeto de conexion con MySQL:
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        
        // Se abre la conexion con MySQL:
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para definir la consulta SQL y se indica que
        // se devolveran los ID's que se generen despues de ejecutarla:
        PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        
        // En este objeto se almacenaran los resultados de la consulta que,
        // en este caso, sera el ID que se genera al realizar la insercion
        // del registro:
        ResultSet rs = null;
        
        // Se llenan los valores de la sentencia SQL. 
        // Es importante recordar que el estandar JDBC es el unico caso especial
        // en el que cuyos indices comienzan en 1, en lugar de 0:
        pstmt.setString(1, v.getNombre());
        pstmt.setString(2, v.getFechaNac());
        pstmt.setString(3, v.getGenero());
        pstmt.setString(4, v.getCalle());
        pstmt.setString(5, v.getNumInt());
        pstmt.setString(6, v.getNumExt());
        pstmt.setString(7, v.getColonia());
        pstmt.setString(8, v.getCp());
        pstmt.setString(9, v.getCiudad());
        pstmt.setString(10, v.getEstado());
        pstmt.setString(11, v.getPais());
        pstmt.setString(12, v.getTelefono());
        pstmt.setString(13, v.getFechaAlta());
        pstmt.setString(14, v.getEmail());
        
        // Se ejecuta la sentencia:
        pstmt.executeUpdate();
        
        // Se recupera el ID del Vendedor que se inserto:
        rs = pstmt.getGeneratedKeys();
        if (rs.next())
            v.setId(rs.getInt(1)); //Se asigna el ID al objeto de tipo Vendedor
        
        // Se cierran los objetos de BD:
        rs.close();
        pstmt.close();
        conn.close();
        
        // Se devuelve el ID que se genero:
        return v.getId();
    }
    
    /**
     * Actualiza un registro en la tabla [vendedor].
     * 
     * @param v Es un objeto de tipo Vendedor con todos los datos que van a 
     *          actualizarse.
     * @throws Exception    Se lanza una excepcion cuando ocurre un fallo en la
     *                      comunicacion con la Base de Datos o se altero de
     *                      forma erronea una sentencia SQL.
     */
    public void update(Vendedor v) throws Exception
    {
        // Se define la consulta SQL:
        String sql = "UPDATE vendedor SET nombre=?, fechaNac=?, genero=?, calle=?, "
                + "numExt=?, numInt=?, colonia=?, cp=?, ciudad=?, estado=?, pais=?, "
                + "telefono=?, fechaAlta=?, email=? WHERE idVendedor=?";
        
        // Se crea un objeto de conexion con MySQL:
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        
        // Se abre la conexion con MySQL:
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para definir la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // Se llenan los valores de la sentencia SQL:
        pstmt.setString(1, v.getNombre());
        pstmt.setString(2, v.getFechaNac());
        pstmt.setString(3, v.getGenero());
        pstmt.setString(4, v.getCalle());
        pstmt.setString(5, v.getNumInt());
        pstmt.setString(6, v.getNumExt());
        pstmt.setString(7, v.getColonia());
        pstmt.setString(8, v.getCp());
        pstmt.setString(9, v.getCiudad());
        pstmt.setString(10, v.getEstado());
        pstmt.setString(11, v.getPais());
        pstmt.setString(12, v.getTelefono());
        pstmt.setString(13, v.getFechaAlta());
        pstmt.setString(14, v.getEmail());
        
        // Se ejecuta la sentencia:
        pstmt.executeUpdate();
        
        // Se cierran los objetos de BD:
        pstmt.close();
        conn.close();
    }
    
    /**
     * Elimina de forma logica el registro de la tabla [vendedor] 
     * correspondiente al valor del identificador (ID) pasado como parametro.
     * 
     * @param id    El valor del ID del vendedor que desea eliminarse.
     * @throws Exception    Se lanza una excepcion cuando ocurre un fallo en la
     *                      comunicacion con la Base de Datos o se altero de
     *                      forma erronea una sentencia SQL.
     */
    public void delete(int id) throws Exception
    {
        // Se define la consulta SQL:
        String sql = "UPDATE vendedor SET estatus=0 WHERE idVendedor=?";
        
        // Se crea un objeto de conexion con MySQL:
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        
        // Se abre la conexion con MySQL:
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para definir la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // Se llenan los valores de la sentencia SQL:
        pstmt.setInt(1, id);
        
        // Se ejecuta la sentencia:
        pstmt.executeUpdate();
        
        // Se cierran los objetos de BD:
        pstmt.close();
        conn.close();
    }
    
    /**
     * Devuelve todos los registros de la tabla producto.
     * 
     * @param filtro    Un valor que sera buscado por coincidencia parcial
     *                  en todos los campos de la vista que contiene los
     *                  registros de vendedores.
     * @return          Devuelve una lista List&lt;Vendedor&gt;
     *                  que contiene todos los registros encontrados en la BD.
     * @throws Exception 
     */
    public List<Vendedor> getAll(String filtro) throws Exception
    {
        // Se define la consulta SQL que devuelve a todos los vendedores
        // ordenados por nombre de manera ascendente:
        String sql = "SELECT * FROM v_vendedor WHERE estatus=1 ORDER BY nombre ASC";
        
        // Se crea un objeto de conexion con MySQL:
        ConnectionMySQL connMySQL = new ConnectionMySQL();
        
        // Se abre la conexion con MySQL:
        Connection conn = connMySQL.open();
        
        // Se genera un objeto para definir la consulta SQL:
        PreparedStatement pstmt = conn.prepareStatement(sql);
        
        // Se ejecuta la consulta SQL y se almacena el resultado:
        ResultSet rs = pstmt.executeQuery();
        
        // En este objeto de tipo lista se agregara cada registro recuperado
        // de la BD:
        List<Vendedor> vendedores = new ArrayList<>();
        
        // Se itera sobre cada renglon (Row) del ResultSet:
        while(rs.next())
            vendedores.add(fill(rs)); //Por cada registro, se genera un nuevo objeto
        
        // Se cierran los objetos de BD:
        rs.close();
        pstmt.close();
        conn.close();
        
        // Se devuelve la lista con los vendedores recuperados de la BD.
        return vendedores;
    }
    
    /**
     * Este metodo genera un objeto de tipo <code>Vendedor<code> extrayendo 
     * los datos de la posicion en la que se encuentra el <i>cursor</i> del
     * <code>ResultSet<code> pasado como parametro.
     * @param rs
     * @return
     * @throws Exception 
     */
    private Vendedor fill(ResultSet rs) throws Exception
    {
        Vendedor v = new Vendedor();
        
        v.setId(rs.getInt("idVendedor"));
        v.setNombre(rs.getString("nombre"));
        v.setFechaNac(rs.getString("fechaNac"));
        v.setGenero(rs.getString("genero"));
        v.setCalle(rs.getString("calle"));
        v.setNumExt(rs.getString("numExt"));
        v.setNumInt(rs.getString("numInt"));
        v.setColonia(rs.getString("colonia"));
        v.setCp(rs.getString("cp"));
        v.setCiudad(rs.getString("ciudad"));
        v.setEstado(rs.getString("estado"));
        v.setPais(rs.getString("pais"));
        v.setTelefono(rs.getString("telefono"));
        v.setFechaAlta(rs.getString("fechaAlta"));
        v.setEmail(rs.getString("email"));
        v.setStatus(rs.getInt("estatus"));
        
        return v;
    }
}