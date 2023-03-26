package com.conexion.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;
import java.sql.ResultSet;

/*
 *
 * @author German Daniel
 * 
 */

public class conexion {
    
    // Nombre de a base de datos
    String bd = "";
    // Nuestro servidor
    String url = "jdbc:mysql://localhost:3306/";
    // Usuario y contraseña
    String user = "root";
    String password = "";
    // Drivers
    String driver = "com.mysql.cj.jdbc.Driver";
    // Uso de las operaciones de sql
    public Statement stmt = null;
    public ResultSet rs = null;
    // Uso de la variable connection
    Connection cx;

    public conexion(String bd) {
    
        this.bd = bd;
        
    }

    public Connection conectar() {
        try {
            Class.forName(driver);
            // Cadena de coneccion
            cx = DriverManager.getConnection(url+bd, user,password);
            System.out.println("Conexion exitosa");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("No se conecto a la BD " + bd);
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return cx;
    }
    
    public void disconnect() {
    
        try {
            cx.close();
        } catch (SQLException ex) {
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    // Ejecuta sentencias sql
    
    public void consultas_sql(String statements) {
    
        try {
            
            stmt = cx.createStatement();
            rs = stmt.executeQuery(statements);
            //System.out.println("Consulta hecha");
        
        } catch (SQLException ex) {
            
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        
        }
        
    }
    
    // UPDATE
    
    public void update(String query) {
        
        try{
        
            stmt = cx.createStatement();
            stmt.executeUpdate(query);
            
        } catch (SQLException ex) {
            
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        
        }
        
    }
    
}
