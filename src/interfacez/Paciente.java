/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfacez;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jefferson
 */
public class Paciente extends javax.swing.JFrame {
    DefaultComboBoxModel modeloCodMEd= new  DefaultComboBoxModel();
    DefaultComboBoxModel modeloCodAlergias= new  DefaultComboBoxModel();
       DefaultComboBoxModel modeloCodEnfermedades= new  DefaultComboBoxModel();
       DefaultTableModel modeloTabla=new DefaultTableModel();
    /**
     * Creates new form Paciente
     */
    public Paciente() {
        initComponents();
        bloquearTextos();
        bloquearBotonesInicio();
        cargarCampos();
        InicializarCodMed();
        InicializarIdAlergias();
        InicializarCodEnfermedades();
//        carTablaPaciente();
        
        
    }
    boolean verif=false;
    public void guardar(){
        if(txtHist_Cli.getText().isEmpty()){
             JOptionPane.showMessageDialog(null, "CAMPO OBLIGATORIO HISTORIA-CLINICA");
        }else{
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String sql="";
            sql = "insert into  paciente (ID_PACI,ID_PER,HCL_PAC,COD_MED,ID_ALE,COD_ENF,OTROS,ESTADO) VALUES(?,?,?,?,?,?,?,?);";
            try {
               PreparedStatement psd = cn.prepareStatement(sql);
                 psd.setString(1, null);
                psd.setString(2, txtId_Paci.getText());
                psd.setString(3, txtHist_Cli.getText());
                String codMed=obtenerCodMed(cbxCod_med);
                psd.setString(4,codMed );
                String codAle=obtenerCodAlerfias(cbxId_alergia);
                psd.setString(5,codAle );
                String codEnf=obtenerCodEnfer(cbxCod_enf);
                psd.setString(6, codEnf);
                 psd.setString(7, txtOtros.getText());
                  psd.setString(8,"SI");
//                 System.out.println(codMed +" "+ codAle+"  "+codEnf);
//                 psd.setString(8, "SI");
                 int n = psd.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se inserto correctamente el dato");
                    verif=true;
                    carTablaPaciente();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Paciente.class.getName()).log(Level.SEVERE, null, ex);
            }
               
        }
    }
    
     public void carTablaPaciente() {
        String[] titulos = {"ID PACIENTE", "ID PERSONA", "HISTORIA CLI", "MEDICAMENTO",  "ALERGIA", "ENFERMEDAD", "OTROS"};
        String[] registros = new String[10];
        modeloTabla = new DefaultTableModel(null, titulos);
        Conexion cc = new Conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "SELECT * FROM paciente ";
        try {

            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("ID_PACI");
                registros[1] = rs.getString("ID_PER");
                registros[2] = rs.getString("HCL_PAC");
                registros[3] = rs.getString("COD_MED");
                registros[4] = rs.getString("ID_ALE");
                registros[5] = rs.getString("COD_ENF");
                registros[6] = rs.getString("OTROS");
                modeloTabla.addRow(registros);
            }
//            tblPacientes.setModel(modeloTabla);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
       

    }
    
    public String obtenerCodEnfer(JComboBox e){
      Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String sql="",codMedi="",nomMed="",cod="";
            sql = "SELECT * FROM enfermedades ";
            
        try {

            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                codMedi=rs.getString("COD_ENF");
                nomMed=rs.getString("NOM_ENF");
                    if (e.getSelectedItem().equals(nomMed)) {
                        cod=codMedi;
//                        System.out.println("cod Enf = "+cod);
                        return  cod;
                       
                }
            }
          
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return cod;
 }
    
    
     public String obtenerCodAlerfias(JComboBox e){
      Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String sql="",codMedi="",nomMed="",cod="";
            sql = "SELECT * FROM alergias ";
            
        try {

            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                codMedi=rs.getString("ID_ALE");
                nomMed=rs.getString("NOM_ALE");
                    if (e.getSelectedItem().equals(nomMed)) {
                        
                        cod=codMedi;
//                        System.out.println("cod Alergia = "+cod);
                        return  cod;
                       
                }
            }
           
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return cod;
 }
    
    public String obtenerCodMed(JComboBox e){
      Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String sql="",codMedi="",nomMed="",cod="";
            sql = "SELECT * FROM medicamentos ";
            
        try {

            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                codMedi=rs.getString("COD_MED");
                nomMed=rs.getString("NOM_MED");
                    if (e.getSelectedItem().equals(nomMed)) {
                        cod=codMedi;
//                        System.out.println("cod Med= "+cod);
                        return  cod;
                       
                }
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return cod;
 }
    
public void bloquearTextos() {
        txtId_Paci.setEnabled(false);
        txtId_Persona.setEnabled(false);
       }
 public void bloquearBotonesInicio() {
//        btnNuevo.setEnabled(true);
//        btnCancelar.setEnabled(true);
//        btnActualizar.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnSalir.setEnabled(true);
//        btnBorrar.setEnabled(false);
    }
 
 public void cargarCampos(){
      Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String sql="";
            sql = "SELECT * FROM paciente ";
            int n=0;
        try {

            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                n++;
            }
            n=n+1;
            txtId_Paci.setText(String.valueOf(n));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
 }
 
 public void InicializarCodMed(){
      Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String sql="",codMedi="";
            sql = "SELECT * FROM medicamentos ";
            int n=0;
             modeloCodMEd.addElement("");
        try {

            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                codMedi=rs.getString("NOM_MED");
                modeloCodMEd.addElement(codMedi);
            }
            cbxCod_med.setModel(modeloCodMEd);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
 }
 
  public void InicializarIdAlergias(){
      Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String sql="",codMedi="";
            sql = "SELECT * FROM alergias ";
            int n=0;
             modeloCodAlergias.addElement("");
        try {

            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                codMedi=rs.getString("NOM_ALE");
                modeloCodAlergias.addElement(codMedi);
            }
            cbxId_alergia.setModel(modeloCodAlergias);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
 }
  
  
   public void InicializarCodEnfermedades(){
      Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String sql="",codMedi="";
            sql = "SELECT * FROM enfermedades ";
            int n=0;
             modeloCodEnfermedades.addElement("");
        try {

            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                codMedi=rs.getString("NOM_ENF");
                modeloCodEnfermedades.addElement(codMedi);
            }
            cbxCod_enf.setModel(modeloCodEnfermedades);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtId_Paci = new javax.swing.JTextField();
        txtId_Persona = new javax.swing.JTextField();
        txtHist_Cli = new javax.swing.JTextField();
        txtOtros = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        cbxCod_med = new javax.swing.JComboBox();
        cbxId_alergia = new javax.swing.JComboBox();
        cbxCod_enf = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("ID PACIENTE");

        jLabel2.setText("ID PERSONA");

        jLabel3.setText("HISTORIA CLINICA");

        jLabel4.setText("MEDICAMENTO");

        jLabel5.setText("ALERGIAS");

        jLabel6.setText("ENFERMEDAD");

        jLabel7.setText("OTROS");

        txtId_Paci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtId_PaciActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/savetheapplication_guardar_2958.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iconosalir.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Copperplate Gothic Bold", 1, 18));
        jLabel9.setText("NUEVO PACIENTE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addComponent(jLabel9)
                .addContainerGap(237, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbxCod_enf, 0, 110, Short.MAX_VALUE)
                            .addComponent(txtHist_Cli, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(txtId_Persona, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(cbxId_alergia, javax.swing.GroupLayout.Alignment.LEADING, 0, 110, Short.MAX_VALUE)
                            .addComponent(cbxCod_med, javax.swing.GroupLayout.Alignment.LEADING, 0, 110, Short.MAX_VALUE)
                            .addComponent(txtId_Paci, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                        .addGap(185, 185, 185)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                        .addGap(32, 32, 32))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtOtros, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtId_Paci, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtId_Persona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHist_Cli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbxCod_med, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cbxId_alergia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(cbxCod_enf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir)
                        .addGap(134, 134, 134)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtOtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void txtId_PaciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtId_PaciActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtId_PaciActionPerformed

private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

    
    guardar();
    //        // TODO add your handling code here:
//        guardarPersona();

}//GEN-LAST:event_btnGuardarActionPerformed

private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
    if (verif==false) {
        int i=JOptionPane.showConfirmDialog(null, "SEGURO QUIERES SALIR SIN GUARDAR");
        if (i==0) {
            this.dispose();
        }
    }
        
}//GEN-LAST:event_btnSalirActionPerformed

        // TODO add your handling code here:
        // TODO add your handling code here:
        //guardarPersona();

        // TODO add your handling code here:
        
    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Paciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Paciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cbxCod_enf;
    private javax.swing.JComboBox cbxCod_med;
    private javax.swing.JComboBox cbxId_alergia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtHist_Cli;
    private javax.swing.JTextField txtId_Paci;
    public static javax.swing.JTextField txtId_Persona;
    private javax.swing.JTextField txtOtros;
    // End of variables declaration//GEN-END:variables
}
