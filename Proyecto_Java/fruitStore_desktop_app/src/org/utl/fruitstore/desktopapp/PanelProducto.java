package org.utl.fruitstore.desktopapp;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.utl.fruitstore.controller.ControllerCategoria;
import org.utl.fruitstore.controller.ControllerProducto;
import org.utl.fruitstore.desktopapp.components.TableModelProducto;
import org.utl.fruitstore.model.Categoria;
import org.utl.fruitstore.model.Producto;

/**
 *
 * @author oswal
 */
public class PanelProducto extends javax.swing.JPanel {

    // Este objeto se requiere para saber cual es en el panel (padre)
    // que contiene al PanelProducto
    JPanel padre;

    // En esta lista se almacenaran las categorias (tipos de fruta o verdura)
    // registrados en la BD, para posteriormente llenar el ComboBox de la
    // interfaz de usuario:
    List<Categoria> categorias;

    // Este objeto proveera dde datos a la JTable que despliega el catalogo
    // de productos.
    TableModelProducto tableModelProducto;

    /**
     * Creates new form PanelProducto
     */
    public PanelProducto(JPanel padre) {
        this.padre = padre;
        initComponents();
        inicializarComponentes();
    }

    public void inicializarComponentes() {
        cargarTablaProductos();
    }

    /**
     * Inserta o Actualiza un registro de Producto en la BD.
     */
    private void guardarProducto() {
        // Se crea una instancia del controlador de productos:
        ControllerProducto cp = new ControllerProducto();

        // Se crea un nuevo Producto:
        Producto p = new Producto();

        // Aqui se almacena el Grupo al que corresponde el producto:
        Categoria g = null;
        int posCategoria = -1;
        try {
            // Se obtiene el Grupo que el usuario selecciono para el producto:
            posCategoria = cmbCategorias.getSelectedIndex();

            // Se verifica que exista un grupo valido:
            if (posCategoria < 0) {
                // Si se entra aqui, significa que no se selecciono un grupo o
                // que no existe ninguno en la BD, por lo tanto, no tiene 
                // sentido seguir en el metodo y mejor se trunca su ejecucion
                // enviando antes un mensaje al usuario:
                JOptionPane.showMessageDialog(this,
                        "No se puede continuar.\n\nSeleccione una categoria valida para el producto.",
                        "Seleccione una Categoría de Producto válida.",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            g = categorias.get(posCategoria);

            // Se revisa si el campo del ID tiene datos:
            if (txtIdProducto.getText().trim().length() > 0) {
                p.setId(Integer.parseInt(txtIdProducto.getText()));
            }

            // Obtenemos el texto primero para verificarlo
            String textoStatus = lblStatus.getText();
            
            // Verificamos si tiene algo escrito Y si el primer caracter es '0'
            if (textoStatus != null && textoStatus.length() > 0 && textoStatus.charAt(0) == '0') {
                p.setStatus(0); // Inactivo
            } else {
                // Si está vacío o es cualquier otra cosa, lo ponemos como Activo por defecto
                p.setStatus(1);
            }

            p.setExistencia(Double.parseDouble(txtExistencias.getText()));
            p.setCategoria(g);
            p.setNombre(txtNombre.getText());
            p.setPrecioCompra(Double.parseDouble(txtPrecioCompra.getText().trim()));
            p.setPrecioVenta(Double.parseDouble(txtPrecioVenta.getText().trim()));

            // Una vez que los atributos del objeto de tipo Producto tienen
            // valores asignados, se revisa si se inserta o se actualiza
            // en la BD, considerando su ID:
            if (p.getId() == 0) {
                // Se realiza la insercion del registro en la BD:
                cp.insert(p);

                // Se actualiza el ID que se genero en la caja de texto.
                // Esto es para evitar que el usuario inserte varias veces
                // el mismo registro, pues posterior a la primera vez, 
                // solo se ejecutaran actualizaciones:
                txtIdProducto.setText("" + p.getId());
            } else {
                cp.update(p);
            }

            // Se recarga la tabla de productos:
            cargarTablaProductos();

            // Una vez realizada la operacion, se envia un mensaje al usuario:
            JOptionPane.showMessageDialog(this,
                    "Datos de Producto guardados.",
                    "Operación realizada.",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarTablaProductos() {
        //Se genera una instancia del Controlador de Productos:
        ControllerProducto cp = new ControllerProducto();

        // Aqui se guardan en memoria los productos recuperados de la BD:
        List<Producto> productos = null;

        try {
            productos = cp.getAll(null);

            // Se revisa si no existe una instancia previa del TableModelProducto:
            if (tableModelProducto == null) {
                // Si tableModelProducto es null, generamos una nueva instancia:
                tableModelProducto = new TableModelProducto(productos);

                // Asignamos el TableModelProducto a la JTable tblProductos:
                tblProductos.setModel(tableModelProducto);
            } else {
                // Si tableModelProducto no es null, simplemente le asignamos
                // los nuevos datos:
                tableModelProducto.setProductos(productos);

                // Despues, se debe forzar a que la JTable se refresque con los 
                // nuevos datos:
                tableModelProducto.fireTableDataChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargarDatosProducto() {
        Producto p = null;
        if (tblProductos.getSelectedRow() >= 0) {
            p = tableModelProducto.getProductos().get(tblProductos.getSelectedRow());
            txtIdProducto.setText(p.getId() + "");
            txtNombre.setText(p.getNombre());
            txtPrecioCompra.setText(p.getPrecioCompra() + "");
            txtPrecioVenta.setText(p.getPrecioVenta() + "");
            txtExistencias.setText(p.getExistencia() + "");
            lblStatus.setText(p.getStatus() + "");
            cmbCategorias.setSelectedItem(p.getCategoria().getNombre());
        }
    }

    public void cargarComboBoxCategorias() throws Exception {
        // Creamos una instancia de ControllerGrupo:
        ControllerCategoria cg = new ControllerCategoria();

        // Consultamos los grupos que hay en la BD:
        categorias = cg.getAll(null);

        // Limpiamos el JComboBox:
        cmbCategorias.removeAllItems();

        // Recorremos la lista de grupos y agregamos el nombre de cada grupo
        // como un item del JComboBox:
        for (Categoria g : categorias) {
            cmbCategorias.addItem(g.getNombre());
        }
    }

    public void limpiarFormulario() {
        txtIdProducto.setText("");
        txtNombre.setText("");
        txtPrecioCompra.setText("");
        txtPrecioVenta.setText("");
        txtExistencias.setText("");
        lblStatus.setText("");
        cmbCategorias.setSelectedIndex(0);
    }

    public void eliminarProducto() {
        ControllerProducto cp = new ControllerProducto();
        try {
            if (!txtIdProducto.getText().trim().isEmpty()) {
                cp.delete(Integer.parseInt(txtIdProducto.getText()));
                cargarTablaProductos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cerrarModulo() {
        if (padre != null) {
            padre.remove(this);
            padre.paintAll(padre.getGraphics());
        }
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
        ControlProducto = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtIdProducto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmbCategorias = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        txtPrecioCompra = new javax.swing.JTextField();
        txtPrecioVenta = new javax.swing.JTextField();
        txtExistencias = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        lblStatus = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(102, 204, 255));

        ControlProducto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ControlProducto.setForeground(new java.awt.Color(255, 255, 255));
        ControlProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/32/package.png"))); // NOI18N
        ControlProducto.setText("Control de Productos");

        jButton1.setBackground(new java.awt.Color(102, 204, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/16/close_icon_16.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ControlProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ControlProducto)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 102, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/32/table.png"))); // NOI18N
        jLabel3.setText("Detalle del Producto");

        jButton2.setBackground(new java.awt.Color(153, 204, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/16/close_icon_16.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("Código del producto:");

        txtIdProducto.setEditable(false);
        txtIdProducto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel4.setText("Estatus:");

        jLabel6.setText("Nombre:");

        jLabel7.setText("Grupo:");

        cmbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Precio compra:");

        jLabel9.setText("Precio Venta:");

        jLabel10.setText("Existencias:");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/16/diskette.png"))); // NOI18N
        jButton3.setText("Guardar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/16/delete.png"))); // NOI18N
        jButton4.setText("Eliminar");
        jButton4.setToolTipText("");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/16/new_data.png"))); // NOI18N
        jButton5.setText("Nuevo");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        txtPrecioVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioVentaActionPerformed(evt);
            }
        });

        txtExistencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExistenciasActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(205, 228, 250));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 102, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/32/table.png"))); // NOI18N
        jLabel11.setText("Catálogo de Productos");

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/16/new_data.png"))); // NOI18N

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/16/table_refresh.png"))); // NOI18N

        jLabel12.setText("Buscar:");

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons/24/filter_reapply.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(29, 29, 29)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addGap(106, 106, 106)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel11))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField6)
                        .addComponent(jLabel12)
                        .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7)
                            .addComponent(jButton6))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        lblStatus.setEditable(false);
        lblStatus.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addGap(48, 48, 48))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cmbCategorias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel8))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10))
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtExistencias, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(cmbCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(txtPrecioVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtExistencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cerrarModulo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPrecioVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioVentaActionPerformed

    private void txtExistenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExistenciasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExistenciasActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        guardarProducto();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            //System.out.println("Hiciste doble click en el renglón " + tblProductos.getSelectedRow());
            cargarDatosProducto();
        }
    }//GEN-LAST:event_tblProductosMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        limpiarFormulario();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        eliminarProducto();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ControlProducto;
    private javax.swing.JComboBox<String> cmbCategorias;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField lblStatus;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtExistencias;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecioCompra;
    private javax.swing.JTextField txtPrecioVenta;
    // End of variables declaration//GEN-END:variables
}
