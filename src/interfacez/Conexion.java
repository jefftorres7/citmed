package interfacez;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;



public class Conexion {

    Connection conecta;

   public Connection conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // parametros para conectar a la base de datos y servidor
            conecta=DriverManager.getConnection("jdbc:mysql://localhost/clinica","root","");
//            JOptionPane.showMessageDialog(null,"CONECCION CORRECTA");
        } catch (Exception ex) {
            //JOptionPane.showMessageDialog(null, ex);
            JOptionPane.showMessageDialog(null,"FALLO LA CONECCION INTENTALO MAS TARDE");
        }
        return conecta;
    }
    public static void main(String[] args) {
        Conexion cc = new Conexion();
        Connection cn = cc.conectar();
        
    }
}
