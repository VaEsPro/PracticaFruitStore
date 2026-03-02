package org.utl.fruitstore.desktopapp;

import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.utl.fruitstore.controller.ControllerVendedor;
import org.utl.fruitstore.desktopapp.components.TableModelVendedor;
import org.utl.fruitstore.model.Vendedor;


/**
 *
 * @author oswal
 */
public class PanelVendedores extends javax.swing.JPanel {
    
    // Este objeto se requiere para saber cual es en el panel (padre)
    // que contiene al PanelVendedores.
    JPanel padre;
    
    // En esta lista se almacenara la categoría (vendedores)
    // registrados en la BD.
    List<Vendedor> vendedores;
    
    // Este objeto proveera dde datos a la JTable que despliega a los vendedores.
    TableModelVendedor tableModelVendedor;
    
    /**
     * Creates new form PanelVendedores
     */
    public PanelVendedores(JPanel padre) {
        this.padre = padre;
        initComponents();
        inicializarComponentes();
    }
    
    public void inicializarComponentes(){
        cargarTablaVendedores();
    }
    
    /**
     * Inserta o Actualiza un registro de Vendedor en la BD.
     */
    private void guardarVendedor()
    {
        // Se crea una instancia del controlador de vendedores:
        ControllerVendedor cv = new ControllerVendedor();
        
        // Se crea un nuevo Vendedor:
        Vendedor v = new Vendedor();
        
        // Aqui se almacena el Grupo al que corresponde el Vendedor:
        try
        {       
            v.setNombre(txtNombre.getText());
            v.setFechaNac(txtFecNac.getText());
            v.setGenero((String) cmbGenero.getSelectedItem());
            v.setTelefono(txtTel.getText());
            v.setCalle(txtCalle.getText());
            v.setNumExt(txtNumExt.getText());
            v.setNumInt(txtNumInt.getText());
            v.setColonia(txtColonia.getText());
            v.setCp(txtCp.getText());
            v.setEmail(txtEmail.getText());
            v.setCiudad(txtCiudad.getText());
            v.setEstado(txtEstado.getText());
            v.setPais(txtPais.getText());
            v.setFechaAlta(txtFechaAlta.getText());
            
            // Una vez que los atributos del objeto de tipo Vendedor tienen
            // valores asignados, se revisa si se inserta o se actualiza
            // en la BD, considerando su ID:
            if (v.getId() == 0)
            {
                // Se realiza la inserción del registro en la BD:
                cv.insert(v);
                
                // Se actualiza el ID que se genero en la caja de texto.
                // Esto es para evitar que el usuario inserte varias veces
                // el mismo registro, pues posterior a la primera vez, 
                // solo se ejecutaran actualizaciones:
                txtIdVendedor.setText("" + v.getId());
            }
            else
            {
                cv.update(v);
            }
            
            // Se recarga la tabla de vendedores:
            cargarTablaVendedores();
            
            // Una vez realizada la operacion, se envia un mensaje al usuario:
            JOptionPane.showMessageDialog(this, 
                                          "Datos de Vendedores guardados.", 
                                          "Operación realizada.",
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void cargarTablaVendedores(){
        //Se genera una instancia del Controlador de Vendedores:
        ControllerVendedor cv = new ControllerVendedor();
        
        // Aqui se guardan en memoria los vendedores recuperados de la BD:
        List<Vendedor> vendedores = null;
        
        try {
            vendedores = cv.getAll(null);
            
            // Se revisa si no existe una instancia previa del TableModelVendedor:
            if (tableModelVendedor == null) {
                // Si tableModelVendedor es null, generamos una nueva instancia:
                tableModelVendedor = new TableModelVendedor(vendedores);

                // Asignamos el TableModelVendedor a la JTable tblVendedores:
                tblVendedores.setModel(tableModelVendedor);
            } else {
                // Si tableModelVendedor no es null, simplemente le asignamos
                // los nuevos datos:
                tableModelVendedor.setVendedores(vendedores);

                // Despues, se debe forzar a que la JTable se refresque con los 
                // nuevos datos:
                tableModelVendedor.fireTableDataChanged();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void cerrarModulo(){
        if(padre != null){
            padre.remove(this);
            padre.paintAll(padre.getGraphics());
        }
    
    }
    
    private void limpiarModulo(){
        txtNombre.setText("");
        txtFecNac.setText("");
        txtTel.setText("");
        txtCalle.setText("");
        txtNumInt.setText("");
        txtNumExt.setText("");
        txtColonia.setText("");
        txtCp.setText("");
        txtEmail.setText("");
        txtCiudad.setText("");
        txtEstado.setText("");
        txtPais.setText("");
        txtFechaAlta.setText("");
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtIdVendedor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtFecNac = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbGenero = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtTel = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtNumInt = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtNumExt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtColonia = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCp = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtPais = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtFechaAlta = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVendedores = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/32/user.png"))); // NOI18N
        jLabel1.setText("Control de Vendedores");

        jButton4.setBackground(new java.awt.Color(204, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/16/close_icon_16.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/32/hide_detail.png"))); // NOI18N
        jLabel2.setText("Detalle del Vendedor");

        jButton5.setBackground(new java.awt.Color(242, 242, 242));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/16/close_icon_16.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("Código del Vendedor:");

        txtIdVendedor.setEditable(false);
        txtIdVendedor.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel5.setText("Nombre:");

        txtNombre.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel6.setText("Fecha de nacimiento:");

        jLabel7.setText("(aaaa/mm/dd)");

        txtFecNac.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel4.setText("Género:");

        cmbGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "H", "M", "O" }));
        cmbGenero.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel8.setText("Teléfono:");

        txtTel.setMinimumSize(new java.awt.Dimension(88, 22));
        txtTel.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Estatus:");

        jTextField5.setEditable(false);
        jTextField5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField5.setText("-");
        jTextField5.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel10.setText("Calle:");

        txtCalle.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel11.setText("Num. Int:");

        txtNumInt.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel12.setText("Num. Ext:");

        txtNumExt.setPreferredSize(new java.awt.Dimension(88, 22));
        txtNumExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumExtActionPerformed(evt);
            }
        });

        jLabel13.setText("Colonia:");

        txtColonia.setMinimumSize(new java.awt.Dimension(15, 22));
        txtColonia.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel14.setText("CP:");

        txtCp.setMinimumSize(new java.awt.Dimension(88, 22));
        txtCp.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel15.setText("Ciudad:");

        txtCiudad.setPreferredSize(new java.awt.Dimension(88, 22));
        txtCiudad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiudadActionPerformed(evt);
            }
        });

        jLabel16.setText("Estado:");

        txtEstado.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel17.setText("País:");

        txtPais.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel18.setText("Fecha de Alta:");

        txtFechaAlta.setMinimumSize(new java.awt.Dimension(88, 22));
        txtFechaAlta.setPreferredSize(new java.awt.Dimension(88, 22));

        jLabel19.setText("Email:");

        txtEmail.setText("@");
        txtEmail.setPreferredSize(new java.awt.Dimension(88, 22));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/32/add.png"))); // NOI18N
        jButton1.setText("Nuevo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/32/diskette.png"))); // NOI18N
        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/32/delete.png"))); // NOI18N
        jButton3.setText("Eliminar");

        jPanel3.setBackground(new java.awt.Color(153, 255, 255));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/32/users_5.png"))); // NOI18N
        jLabel20.setText("Vendedores");

        tblVendedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblVendedores);

        jButton6.setBackground(new java.awt.Color(153, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/16/table_refresh.png"))); // NOI18N

        jButton7.setBackground(new java.awt.Color(153, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/16/new_slide.png"))); // NOI18N

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("BUSCAR:");

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/24/filter_reapply.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(56, 56, 56)
                        .addComponent(jButton6)
                        .addGap(53, 53, 53)
                        .addComponent(jButton7)
                        .addGap(89, 89, 89)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField16)
                        .addGap(18, 18, 18)
                        .addComponent(jButton8)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addComponent(jLabel20)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTextField16, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(jLabel21))
                    .addComponent(jButton7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(41, 41, 41)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtIdVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(46, 46, 46)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFecNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17))
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3)
                            .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNumInt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumExt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(txtFechaAlta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 9, Short.MAX_VALUE)))))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(479, 479, 479)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtIdVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtFecNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(cmbGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtNumInt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtNumExt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtCp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtFechaAlta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumExtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumExtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumExtActionPerformed

    private void txtCiudadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiudadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiudadActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        limpiarModulo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        cerrarModulo();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        guardarVendedor();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbGenero;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTable tblVendedores;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtColonia;
    private javax.swing.JTextField txtCp;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEstado;
    private javax.swing.JTextField txtFecNac;
    private javax.swing.JTextField txtFechaAlta;
    private javax.swing.JTextField txtIdVendedor;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumExt;
    private javax.swing.JTextField txtNumInt;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtTel;
    // End of variables declaration//GEN-END:variables
}
