package com.registros;

import com.conexion.bd.conexion;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class Formulario extends Stage {

    public void FormularioWindow(String name, String dni, String sexo, String contraseña, int indice, ObservableList<datos_registro> registro) {

        //header
        HBox encabezado = new HBox();
        Label txt = new Label("FORMULARIO");
        txt.setFont(Font.font("Times New Roman", 31));
        encabezado.getChildren().add(txt);
        encabezado.setMaxWidth(Double.MAX_VALUE);
        encabezado.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(txt, new Insets(15, 0, 0, 25));

        HBox st_nombre = new HBox();

        Label txt_nombre = new Label("Nombre:");
        txt_nombre.setFont(Font.font("Times New Roman", 20));

        Label dat_nombre = new Label(name);
        dat_nombre.setFont(Font.font("Arial", 20));

        st_nombre.getChildren().addAll(txt_nombre, dat_nombre);
        st_nombre.setMaxWidth(Double.MAX_VALUE);
        st_nombre.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(txt_nombre, new Insets(20, 10, 0, 45));
        HBox.setMargin(dat_nombre, new Insets(20, 0, 0, 0));

        HBox st_dni = new HBox();

        Label txt_dni = new Label("DNI:");
        txt_dni.setFont(Font.font("Times New Roman", 20));

        Label dat_dni = new Label(dni);
        dat_dni.setFont(Font.font("Arial", 20));

        st_dni.getChildren().addAll(txt_dni, dat_dni);
        st_dni.setMaxWidth(Double.MAX_VALUE);
        st_dni.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(txt_dni, new Insets(10, 10, 0, 45));
        HBox.setMargin(dat_dni, new Insets(10, 0, 0, 0));

        HBox st_sexo = new HBox();

        Label txt_sexo = new Label("Sexo:");
        txt_sexo.setFont(Font.font("Times New Roman", 20));

        Label dat_sexo = new Label(sexo);
        dat_sexo.setFont(Font.font("Arial", 20));

        st_sexo.getChildren().addAll(txt_sexo, dat_sexo);
        st_sexo.setMaxWidth(Double.MAX_VALUE);
        st_sexo.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(txt_sexo, new Insets(10, 10, 0, 45));
        HBox.setMargin(dat_sexo, new Insets(10, 0, 0, 0));

        HBox st_pass = new HBox();

        Label txt_passw = new Label("Contraseña:");
        txt_passw.setFont(Font.font("Times New Roman", 20));

        Label dat_passw = new Label(contraseña);
        dat_passw.setFont(Font.font("Arial", 20));

        st_pass.getChildren().addAll(txt_passw, dat_passw);
        st_pass.setMaxWidth(Double.MAX_VALUE);
        st_pass.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(txt_passw, new Insets(10, 10, 0, 45));
        HBox.setMargin(dat_passw, new Insets(10, 0, 0, 0));
        
        // Botones
        
        HBox st_buttons = new HBox();

        Button b_acept = new Button("Registrar");
        b_acept.setFont(Font.font("Times New Roman", 24));

        b_acept.setOnAction((event) -> {
           
            try {
                
                // Creamos la cuenta bancaria
                
                int numero_cuenta = (int) (Math.random() * 15 + 1);
                
                conexion bd_new_account = new conexion("trabajo_final");
                
                bd_new_account.conectar();
                
                String sql = "SELECT COUNT(*) FROM cuenta_bancaria";
                
                bd_new_account.consultas_sql(sql);
                
                while (bd_new_account.rs.next()){
                    
                    int idCuenta = bd_new_account.rs.getInt(1) + 1;
                    int saldo = 0;
                    
                    String update = "INSERT INTO cuenta_bancaria (id_cuenta_bancaria,id_usuario,numero_cuenta,saldo) VALUES ('" + idCuenta + "','"+ (indice -  1) +"', '" + numero_cuenta + "', '"+ saldo +"')";
                    
                    bd_new_account.update(update);
                    
                    JOptionPane.showMessageDialog(null,"Cuenta bancaria creada correctamente");
                    
                }
                
                // Lo removemos de la tabla
                
            } catch (SQLException ex) {
                
                Logger.getLogger(Formulario.class.getName()).log(Level.SEVERE, null, ex);
            
            }
            
        });
        
        Button b_del = new Button("Descartar");
        
        b_del.setOnAction((event) -> {
           
            try {
                
                conexion bd_sel = new conexion("trabajo_final");
                bd_sel.conectar();
                
                String user = "SELECT id_usuario FROM clientes";
                bd_sel.consultas_sql(user);
                
                while (bd_sel.rs.next()){
                    
                    int idBDuser = bd_sel.rs.getInt("id_usuario");
                
                    if ( idBDuser == indice){

                        conexion bd_del = new conexion("trabajo_final");
                        bd_del.conectar();

                        String sql = "DELETE FROM clientes WHERE id_usuario = '" + indice + "'";
                        bd_del.update(sql);
                        
                        registro.remove(indice - 1);
                        
                        JOptionPane.showMessageDialog(null,"Usuario descartado y eliminado correctamente");

                    }
                    
                }
                
            } catch (SQLException ex) {
                
                Logger.getLogger(Formulario.class.getName()).log(Level.SEVERE, null, ex);
            
            }
            
        });
        
        b_del.setFont(Font.font("Times New Roman", 24));

        st_buttons.getChildren().addAll(b_acept, b_del);
        st_buttons.setMaxWidth(Double.MAX_VALUE);
        st_buttons.setAlignment(Pos.CENTER);
        HBox.setMargin(b_acept, new Insets(20, 50, 0, 0));
        HBox.setMargin(b_del, new Insets(20, 0, 0, 50));
        VBox root = new VBox();

        root.getChildren().addAll(encabezado, st_nombre, st_dni, st_sexo, st_pass, st_buttons);
        Scene scene = new Scene(root, 500, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Formulario Recivido");
        stage.show();
        
    }
}
