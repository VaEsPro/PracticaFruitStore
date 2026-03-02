package org.utl.fruitstore.desktopapp.components;

import java.util.List;
import org.utl.fruitstore.model.Vendedor;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author oswal
 */
public class TableModelVendedor extends AbstractTableModel{
    List<Vendedor> vendedores;

    public TableModelVendedor(List<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }

    
    public List<Vendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(List<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }
 

    @Override
    public int getRowCount() {
        if(vendedores == null)
            return 0;
        else
            return vendedores.size();
    }

    @Override
    public int getColumnCount() {
        return 14;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vendedor v = vendedores.get(rowIndex);
        switch (columnIndex){
            case 0: return v.getNombre();
            case 1: return v.getFechaNac();
            case 2: return v.getGenero();
            case 3: return v.getCalle();
            case 4: return v.getNumExt();
            case 5: return v.getNumInt();
            case 6: return v.getColonia();
            case 7: return v.getCp();
            case 8: return v.getCiudad();
            case 9: return v.getEstado();
            case 10: return v.getPais();
            case 11: return v.getTelefono();
            case 12: return v.getFechaAlta();
            case 13: return v.getEmail();
            default: return "Error";
        }
    }
    
    @Override
    public String getColumnName(int columnIndex){
        switch(columnIndex){
            case 0: return "Vendedor";
            case 1: return "Fecha de Nacimiento";
            case 2: return "Género";
            case 3: return "Calle";
            case 4: return "Número Exterior";
            case 5: return "Número Interior";
            case 6: return "Colonia";
            case 7: return "Código Postal";
            case 8: return "Ciudad";
            case 9: return "Estado";
            case 10: return "País";
            case 11: return "Teléfono";
            case 12: return "Fecha de Alta";
            case 13: return "Email";
            default: return "Error";
        }
    }   
}