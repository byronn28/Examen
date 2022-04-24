/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author byron
 */
public class ConectionPg {
     Connection con;
    String cadConexion="jdbc:postgresql://localhost:5432/MVC";
    String pgUser = "postgres";
    String pgPass="1234";

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public ConectionPg(){
        try{
            Class.forName("org.postgresql.Driver");
            
        }catch(ClassNotFoundException ex){
            Logger.getLogger(ConectionPg.class.getName()).log(Level.SEVERE,null,ex);
        }
        
        try {
            con = DriverManager.getConnection(cadConexion, pgUser,pgPass);
            System.out.println("CONEXION EXITOSA");
        } catch (SQLException ex) {
            Logger.getLogger(ConectionPg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet consulta(String sql){
        try {
            Statement st = con.createStatement();
            return st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ConectionPg.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean accion(String sql){
        boolean correcto;
        try {
            Statement st = con.createStatement();
            correcto=st.execute(sql);
            st.close();
            correcto=true;
        } catch (SQLException ex) {
            Logger.getLogger(ConectionPg.class.getName()).log(Level.SEVERE, null, ex);
            correcto = false;
        }
        return correcto;
    }
}
