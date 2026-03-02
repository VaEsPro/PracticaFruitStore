package org.utl.fruitstore.desktopapp.components;

import org.utl.fruitstore.model.Producto;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author oswal
 */
public class TableModelProducto extends AbstractTableModel{
    List<Producto> productos;
    
    public TableModelProducto(List<Producto> productos){
        this.productos = productos;
    }
    
    public List<Producto> getProductos(){
        return productos;
    }
    
    public void setProductos(List<Producto> productos){
        this.productos = productos;
    }

    @Override
    public int getRowCount() {
        if(productos == null)
            return 0;
        else
            return productos.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto p = productos.get(rowIndex);
        switch (columnIndex){
            case 0: return p.getNombre();
            case 1: return p.getCategoria().getNombre();
            case 2: return p.getPrecioCompra();
            case 3: return p.getPrecioVenta();
            case 4: return p.getExistencia();
            default: return "Error";
        }
    }
    
    @Override
    public String getColumnName(int columnIndex){
        switch(columnIndex){
            case 0: return "Producto";
            case 1: return "Categoria";
            case 2: return "Precio Compra";
            case 3: return "Precio Venta";
            case 4: return "Existencias (Kg)";
            default: return "Error";
        }
    }
}