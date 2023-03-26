package com.trabajo_final;

import com.conexion.bd.conexion;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class register {
    
    public void registerWindow(){
        
        // Nodo para el titulo
        
        Label title = new Label("Registro de usuarios");
        
        // Labels y Inputs
        
        // Nombre
        
        Label nombre = new Label("Nombre: ");
        
        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Ingrese un nombre");
        
        HBox nombre_box = new HBox();
        nombre_box.getChildren().addAll(nombre,txtNombre);
        nombre_box.setAlignment(Pos.CENTER);
        
        // DNI
        
        Label dni = new Label("DNI: ");
        
        TextField txtDni = new TextField();
        txtDni.setPromptText("Ingrese su número de dni");
        
        HBox dni_box = new HBox();
        dni_box.getChildren().addAll(dni,txtDni);
        dni_box.setAlignment(Pos.CENTER);
        
        // Sexo
        
        Label sexo = new Label("Sexo: ");
        
        ComboBox gender = new ComboBox();
        
        gender.getItems().addAll("M","F");
        
        HBox sexo_box = new HBox();
        sexo_box.getChildren().addAll(sexo,gender);
        sexo_box.setAlignment(Pos.CENTER);
        
        // Contraseña
        
        Label contrasenia = new Label("Contraseña: ");
        
        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Ingrese una contraseña");
        
        HBox contrasenia_box = new HBox();
        
        contrasenia_box.getChildren().addAll(contrasenia,txtPass);
        contrasenia_box.setAlignment(Pos.CENTER);
        
        // Boton para registrar los datos
        
        Button registrar_datos = new Button("Crear nueva cuenta");
        registrar_datos.setCursor(Cursor.HAND);
        registrar_datos.setMaxWidth(250);
        // Registro de datos
        
        registrar_datos.setOnAction(new EventHandler<ActionEvent>() {
       
            @Override
            public void handle(ActionEvent e) {
                
                try {
                    
                    conexion bd_reg = new conexion("trabajo_final");
                    
                    bd_reg.conectar();
                    
                    String get_id = "SELECT COUNT(*) FROM clientes";
                    
                    bd_reg.consultas_sql(get_id);
                    
                    if (bd_reg.rs.next()){
                        
                        int idUsuario = bd_reg.rs.getInt(1) + 1;
                    
                        String name = txtNombre.getText();
                        String dni = txtDni.getText();
                        String sex = (String) gender.getValue();
                        String pass = txtPass.getText();

                        String insert = "INSERT INTO clientes(id_usuario, id_estatus, nombres, dni, sexo, contraseña) VALUES ('" + idUsuario + "','2','" + name + "','" + dni + "','" + sex + "','" + pass +"')";

                        bd_reg.update(insert);

                        JOptionPane.showMessageDialog(null,"Registro exitoso!");
                        
                    }
                    
                } catch (SQLException ex) {
                    
                    Logger.getLogger(register.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null,"Error al registrar el usuario");
                
                }
                
            }
        });
        
        // Ponemos todo en un VBox
        
        VBox elementos = new VBox();
        
        elementos.getChildren().addAll(title,nombre_box,dni_box,sexo_box, contrasenia_box, registrar_datos);
        
        elementos.setAlignment(Pos.CENTER);
        
        VBox.setMargin(title, new Insets(10,10,10,10));
        VBox.setMargin(nombre_box, new Insets(10,10,10,10));
        VBox.setMargin(dni_box, new Insets(10,10,10,10));
        VBox.setMargin(sexo_box, new Insets(10,10,10,10));
        VBox.setMargin(contrasenia_box, new Insets(10,10,10,10));
        VBox.setMargin(registrar_datos, new Insets(10,10,10,10));
        
        elementos.setMaxSize(500,Double.MAX_VALUE);
        
        // Creando el escenario
        
        VBox root = new VBox();
        
        root.getChildren().addAll(elementos);
        
        root.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(root,854,503);
        
        Stage register = new Stage();
        
        register.setTitle("Crear nueva cuenta");
        register.setScene(scene);
        register.initModality(Modality.APPLICATION_MODAL);
        register.show();
        
    }
    
}
