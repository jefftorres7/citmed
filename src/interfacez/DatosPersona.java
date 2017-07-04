/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 JEFFERSON TORRES
 */




package interfacez;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jefferson
 */
public class DatosPersona extends javax.swing.JInternalFrame {

    DefaultTableModel modelo;
    Border borde = new JTextField().getBorder();

    /**
     * Creates new form viajeCarro
     */
    public DatosPersona() {
        initComponents();
//        Image icon = new ImageIcon(getClass().getResource("/imagenes/paciente_pe.png")).getImage();
//        setIconImage(icon);
        bloquearTextos();
        bloquearBotonesInicio();
        carTablaPaciente(txtBuscarPaciente.getText());
        tblPacientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                int fila = tblPacientes.getSelectedRow();
                if (tblPacientes.getSelectedRow() != -1) {
                    txtCedula.setText(tblPacientes.getValueAt(fila, 0).toString());
                    txtCedula.setEditable(false);
                    txtNombres.setText(tblPacientes.getValueAt(fila, 1).toString());
                    txtApellidos.setText(tblPacientes.getValueAt(fila, 2).toString());
                    jdcFechaNac.setDate(TransformaraFecha(tblPacientes.getValueAt(fila, 4).toString()));
                    txtDireccion.setText(tblPacientes.getValueAt(fila, 5).toString());
                    txtTelefono.setText(tblPacientes.getValueAt(fila, 6).toString());
                    txtCelular.setText(tblPacientes.getValueAt(fila, 7).toString());
                    txtEmail.setText(tblPacientes.getValueAt(fila, 8).toString());
                    txtOcupacion.setText(tblPacientes.getValueAt(fila, 9).toString());
                    desbloquearTextos();
                    bloquearUpdate();
                }
                //To change body of generated methods, choose Tools | Templates.
            }
        });

    }

////    public void colocarIconoVentana() {
////        Image icon = new ImageIcon(getClass().getResource("Auto.png")).getImage();
////        setIconImage(icon);
////    }
    public Date TransformaraFecha(String Fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {

            fecha = formatoDelTexto.parse(Fecha);

        } catch (ParseException ex) {


        }
        return fecha;
    }

    public void bloquearTextos() {
        txtCedula.setEnabled(false);
        txtNombres.setEnabled(false);
        txtApellidos.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtTelefono.setEnabled(false);
        txtCelular.setEnabled(false);
        txtOcupacion.setEnabled(false);
        jdcFechaNac.setEnabled(false);
        txtEmail.setEnabled(false);
    }

    public void desbloquearTextos() {
        txtCedula.setEnabled(true);
        txtNombres.setEnabled(true);
        txtApellidos.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtTelefono.setEnabled(true);
        txtCelular.setEnabled(true);
        txtOcupacion.setEnabled(true);
        jdcFechaNac.setEnabled(true);
        txtEmail.setEnabled(true);
    }

    public void bloquearBotonesInicio() {
        btnNuevo.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnSalir.setEnabled(true);
        btnBorrar.setEnabled(false);
    }

    public void bloquearBotonesNuevo() {
        txtCedula.setEditable(true);
        btnNuevo.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnActualizar.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnSalir.setEnabled(true);
        btnBorrar.setEnabled(false);
    }

    public void limpiarTextos() {
        txtCedula.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCelular.setText("");
        txtOcupacion.setText("");
        jdcFechaNac.setDateFormatString("");
        txtEmail.setText("");

    }

    public void bloquearBotonesGuardar() {
        btnNuevo.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnSalir.setEnabled(true);
        btnBorrar.setEnabled(false);
    }

    public void controlNumeros(KeyEvent evt) {
        char c;
        c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            evt.consume();
            getToolkit().beep();
        }
    }

    public void controlLetras(KeyEvent evt) {
        char c;
        c = evt.getKeyChar();
        if (Character.isDigit(c)) {
            evt.consume();
            getToolkit().beep();
        }
    }

    public void quitarBordesRojos() {

        txtApellidos.setBorder(borde);
        txtNombres.setBorder(borde);
        txtCedula.setBorder(borde);
        txtDireccion.setBorder(borde);
        txtCelular.setBorder(borde);
        txtEmail.setBorder(borde);
        txtOcupacion.setBorder(borde);
        txtTelefono.setBorder(borde);
    }

    public void ValidacionMayusculas(KeyEvent evt) {
        char caracter = evt.getKeyChar();
        if (Character.isLowerCase(caracter)) {
            String cadena = ("" + caracter);
            caracter = cadena.toUpperCase().charAt(0);
            evt.setKeyChar(caracter);
        }
    }

    public void Borrar() {
        Conexion cc = new Conexion();
        Connection cn = cc.conectar();

        String sql = "";
        sql = "UPDATE persona SET ACTIVO='NO' where ID_PER='" + txtCedula.getText() + "'";
        try {
            PreparedStatement psd = cn.prepareStatement(sql);
            int a = JOptionPane.showConfirmDialog(null, "Desea Borrar la persona con Cédula" + txtCedula.getText());
            if (a == 0) {
                int n = psd.executeUpdate();
                modelo.removeRow(tblPacientes.getSelectedRow());
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "se elimino correctamente el dato");
                    limpiarTextos();
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se Pudo Eliminar el Dato");
        }
    }

    public void Modificar() {
        if (txtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "DEBE INGRESAR LA CÉDULA");
            txtCedula.setBorder(BorderFactory.createLineBorder(Color.red));
        } else if (txtNombres.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "DEBE INGRESAR LOS NOMBRES");
            txtNombres.setBorder(BorderFactory.createLineBorder(Color.red));
        } else if (txtApellidos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "DEBE INGRESAR LOS APELLIDOS");
            txtApellidos.setBorder(BorderFactory.createLineBorder(Color.red));
        } else if (txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESE LA DIRECCION");
            txtDireccion.setBorder(BorderFactory.createLineBorder(Color.red));
        } else if (txtTelefono.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "DEBE INGRESAR EL TELEFONO");
        } else if (jdcFechaNac.getDateFormatString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "DEBE INGRESAR LA FECHA");
        } else if (txtOcupacion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "DEBE INGRESAR LA OCUPACIÓN");
        } else {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String a[] = new String[2];
            String p[] = new String[2];
            a = txtNombres.getText().split(" ");
            p = txtApellidos.getText().split(" ");
            String sql = "";
            sql = "UPDATE persona SET NOM1_PER='" + a[0] + "',"
                    + "NOM2_PER='" + a[1] + "',"
                    + "APEP_PER='" + p[0]+ "',"
                    + "APEM_PER='" + p[1]+ "',"
                    + "FEC_NAC_PER='" + FechaaCadena(jdcFechaNac.getDate()) + "',"
                    + "DIR_PER='" + txtDireccion.getText() + "',"
                    + "CONVENCIONAL='" +txtTelefono.getText() + "',"
                    + "CELULAR='" +txtCelular.getText() + "',"
                    + "E_MAIL_PER='" +txtEmail.getText() + "',"
                    + "OCU_PER='" +txtOcupacion.getText() + "'"
                    + "WHERE ID_PER='" + txtCedula.getText() + "'";
            limpiarTextos();
            bloquearTextos();
            bloquearBotonesGuardar();
            try {
                PreparedStatement psd = cn.prepareStatement(sql);
                int n = psd.executeUpdate();
                if (n > 0) {
//                    JOptionPane.showMessageDialog(null, "Se actualizó Correctamente la fila");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo Actualizar la fila ");
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }

    public void bloquearUpdate() {
        btnNuevo.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnActualizar.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnSalir.setEnabled(true);
        btnBorrar.setEnabled(true);
    }

    public String FechaaCadena(Date fec) {
        String FECHA;
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        FECHA = formatoFecha.format(fec);
        return FECHA;
    }

    public void guardarPersona() {
        String ID_PER, NOM1_PER, NOM2_PER, APEP_PER, APEM_PER, EDAD_PER, FEC_NAC_PER, DIR_PER, CONVENCIONAL, CELULAR, E_MAIL_PER, OCU_PER,ACTIVO;
        if (txtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "DEBE INGRESAR LA CÉDULA");
            txtCedula.setBorder(BorderFactory.createLineBorder(Color.red));
        } else if (txtNombres.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "DEBE INGRESAR LOS NOMBRES");
            txtNombres.setBorder(BorderFactory.createLineBorder(Color.red));
        } else if (txtApellidos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "DEBE INGRESAR LOS APELLIDOS");
            txtApellidos.setBorder(BorderFactory.createLineBorder(Color.red));
        } else if (txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "INGRESE LA DIRECCION");
            txtDireccion.setBorder(BorderFactory.createLineBorder(Color.red));
        } else if (txtTelefono.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "DEBE INGRESAR EL TELEFONO");
        } else if (jdcFechaNac.getDate().toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "DEBE INGRESAR LA FECHA");
        } else if (txtOcupacion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "DEBE INGRESAR LA OCUPACIÓN");
        } else {

            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            ID_PER = txtCedula.getText();
            String a[] = new String[2];
            String p[] = new String[2];
            a = txtNombres.getText().split(" ");
            p = txtApellidos.getText().split(" ");
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int escojido = jdcFechaNac.getDate().getYear() + 1900;
            EDAD_PER = String.valueOf(year - escojido);
            NOM1_PER = a[0];
            NOM2_PER = a[1];
            APEP_PER = p[0];
            APEM_PER = p[1];
            FEC_NAC_PER = FechaaCadena(jdcFechaNac.getDate());
            DIR_PER = txtDireccion.getText();
            CONVENCIONAL = txtTelefono.getText();
            if (txtCelular.getText().isEmpty()) {
                CELULAR = "SIN CELULAR";
            } else {
                CELULAR = txtCelular.getText();
            }
            if (txtEmail.getText().isEmpty()) {
                E_MAIL_PER = "SIN E_MAIL";
            } else {
                E_MAIL_PER = txtEmail.getText();
            }
            OCU_PER = txtOcupacion.getText();
            String sql = "";
            String sql2 = "";
            String placa;
            sql = "insert into persona(ID_PER, NOM1_PER, NOM2_PER, APEP_PER, APEM_PER, EDAD_PER, FEC_NAC_PER, DIR_PER, CONVENCIONAL, CELULAR, E_MAIL_PER, OCU_PER, ACTIVO) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            sql2 = "SELECT * FROM persona";
            try {

                Statement psd2 = cn.createStatement();
                ResultSet rs = psd2.executeQuery(sql2);
                while (rs.next()) {
                    placa = rs.getString("ID_PER");
                    if (placa.equals(txtCedula.getText())) {
                        JOptionPane.showMessageDialog(null, "LA PERSONA YA EXISTENTE");
                        txtCedula.setText("");
                    }
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
            try {
                PreparedStatement psd = cn.prepareStatement(sql);
                psd.setString(1, ID_PER);
                psd.setString(2, NOM1_PER);
                psd.setString(3, NOM2_PER);
                psd.setString(4, APEP_PER);
                psd.setString(5, APEM_PER);
                psd.setInt(6, Integer.valueOf(EDAD_PER));
                psd.setString(7, FEC_NAC_PER);
                psd.setString(8, DIR_PER);
                psd.setString(9, CONVENCIONAL);
                psd.setString(10, CELULAR);
                psd.setString(11, E_MAIL_PER);
                psd.setString(12, OCU_PER);
                psd.setString(13, "SI");
                int n = psd.executeUpdate();

                if (n > 0) {
//                    JOptionPane.showMessageDialog(null, psd);
//                    JOptionPane.showMessageDialog(null, "Se inserto correctamente el dato");
                    Paciente e=new Paciente();
                    e.txtId_Persona.setText(txtCedula.getText());
                    e.show();
                    limpiarTextos();
                    bloquearBotonesGuardar();
                    bloquearTextos();
                   
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se Inserto el Dato");
                
            }
        }
        carTablaPaciente(txtBuscarPaciente.getText());
    }

    public void carTablaPaciente(String dato) {
        String[] titulos = {"CEDULA", "NOMBRES", "APELLIDOS", "EDAD", "FECHA NAC", "DIRECCION", "TELEFONO", "CELULAR", "E_MAIL", "OCUPACIÓN"};
        String[] registros = new String[10];
        modelo = new DefaultTableModel(null, titulos);
        Conexion cc = new Conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "SELECT * FROM PERSONA where ID_PER LIKE'%" + dato + "%'AND ACTIVO='SI'";
        try {

            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("ID_PER");
                registros[1] = rs.getString("NOM1_PER") + " " + rs.getString("NOM2_PER");
                registros[2] = rs.getString("APEP_PER") + " " + rs.getString("APEM_PER");
                registros[3] = rs.getString("EDAD_PER");
                registros[4] = rs.getString("FEC_NAC_PER");
                registros[5] = rs.getString("DIR_PER");
                registros[6] = rs.getString("CONVENCIONAL");
                registros[7] = rs.getString("CELULAR");
                registros[8] = rs.getString("E_MAIL_PER");
                registros[9] = rs.getString("OCU_PER");
                modelo.addRow(registros);
            }
            tblPacientes.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        tblPacientes.setModel(modelo);

    }


  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        txtNombres = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jdcFechaNac = new com.toedter.calendar.JDateChooser();
        txtTelefono = new javax.swing.JTextField();
        txtCelular = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtOcupacion = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPacientes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtBuscarPaciente = new javax.swing.JTextField();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DATOS PERSONALES");

        jLabel4.setText("FECHA DE NACIMIENTO:");

        jLabel3.setText("APELLIDOS:");

        jLabel2.setText("NOMBRES:");

        jLabel1.setText("CÉDULA:");

        jLabel6.setText("TELEFONO:");

        jLabel5.setText("DIRECCIÓN:");

        jLabel9.setText("CELULAR:");

        jLabel10.setText("E_MAIL");

        jLabel11.setText("OCUPACIÓN:");

        txtCedula.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
        });

        txtNombres.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombresKeyTyped(evt);
            }
        });

        txtApellidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtApellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidosActionPerformed(evt);
            }
        });
        txtApellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidosKeyTyped(evt);
            }
        });

        txtDireccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });

        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        txtCelular.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelularKeyTyped(evt);
            }
        });

        txtEmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtOcupacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtOcupacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOcupacionKeyTyped(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(204, 0, 0));
        jLabel12.setText("(*)");

        jLabel13.setForeground(new java.awt.Color(204, 0, 0));
        jLabel13.setText("(*)");

        jLabel14.setForeground(new java.awt.Color(204, 0, 0));
        jLabel14.setText("(*)");

        jLabel15.setForeground(new java.awt.Color(204, 0, 0));
        jLabel15.setText("(*)");

        jLabel16.setForeground(new java.awt.Color(204, 0, 0));
        jLabel16.setText("(*)");

        jLabel17.setForeground(new java.awt.Color(204, 0, 0));
        jLabel17.setText("(*)");

        jLabel18.setForeground(new java.awt.Color(204, 0, 0));
        jLabel18.setText("(*)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel1))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombres)
                            .addComponent(txtApellidos)
                            .addComponent(txtDireccion)
                            .addComponent(jdcFechaNac, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                            .addComponent(txtTelefono)
                            .addComponent(txtCelular)
                            .addComponent(txtEmail)
                            .addComponent(txtOcupacion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)
                        .addComponent(jdcFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtOcupacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(31, 31, 31))
        );

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/new-file_40454.png"))); // NOI18N
        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/savetheapplication_guardar_2958.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/refresh-curve-arrows_icon-icons.com_68503.png"))); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconocancelar.png"))); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconosalir.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/seo-social-web-network-internet_262_icon-icons.com_61518.png"))); // NOI18N
        btnBorrar.setText("BORRAR");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBorrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSalir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Copperplate Gothic Bold", 1, 18)); // NOI18N
        jLabel7.setText("DATOS PERSONALES");

        tblPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblPacientes);

        jLabel8.setText("BUSCAR POR CÉDULA");

        txtBuscarPaciente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarPacienteKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel8)
                .addGap(55, 55, 55)
                .addComponent(txtBuscarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(374, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(277, 277, 277))
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(272, 272, 272))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        txtCedula.setEditable(true);
        quitarBordesRojos();
        limpiarTextos();
        bloquearTextos();
        bloquearBotonesGuardar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        bloquearBotonesNuevo();
        desbloquearTextos();// TODO add your handling code here:

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        guardarPersona();


    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        Modificar();

        carTablaPaciente(txtBuscarPaciente.getText());
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        Borrar();

    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtAnioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAnioFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnioFocusLost

    private void txtPlacaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlacaKeyTyped
        // TODO add your handling code here:
        ValidacionMayusculas(evt);
    }//GEN-LAST:event_txtPlacaKeyTyped

    private void txtPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlacaActionPerformed

    private void txtApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidosActionPerformed

    private void txtBuscarPacienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarPacienteKeyReleased
        // TODO add your handling code here:
        carTablaPaciente(txtBuscarPaciente.getText());
    }//GEN-LAST:event_txtBuscarPacienteKeyReleased

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
        // TODO add your handling code here:
        controlNumeros(evt);
        ValidacionMayusculas(evt);
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void txtNombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombresKeyTyped
        // TODO add your handling code here:
        ValidacionMayusculas(evt);
        controlLetras(evt);
    }//GEN-LAST:event_txtNombresKeyTyped

    private void txtApellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidosKeyTyped
        // TODO add your handling code here:
        ValidacionMayusculas(evt);
        controlLetras(evt);
    }//GEN-LAST:event_txtApellidosKeyTyped

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        // TODO add your handling code here:
         ValidacionMayusculas(evt);
        controlLetras(evt);
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        // TODO add your handling code here:
        controlNumeros(evt);
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtCelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyTyped
        // TODO add your handling code here:
        controlNumeros(evt);
    }//GEN-LAST:event_txtCelularKeyTyped

    private void txtOcupacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOcupacionKeyTyped
        // TODO add your handling code here:
        controlLetras(evt);
        ValidacionMayusculas(evt);
    }//GEN-LAST:event_txtOcupacionKeyTyped


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DatosPersona.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatosPersona.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatosPersona.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatosPersona.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DatosPersona().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
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
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private com.toedter.calendar.JDateChooser jdcFechaNac;
    private javax.swing.JTable tblPacientes;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtBuscarPaciente;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtOcupacion;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
