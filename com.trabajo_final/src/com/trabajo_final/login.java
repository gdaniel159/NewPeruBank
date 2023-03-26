package com.trabajo_final;

import com.conexion.bd.conexion;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class login extends Application {
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        // Titulo
        
        Label label_title = new Label("Iniciar Sesion");
        label_title.setFont(new Font(40));
        
        // Input 1
        
        Label label_user = new Label("User");
        label_user.setFont(new Font(20));
        
        TextField txtUser = new TextField();
        txtUser.setFont(new Font(18));
        txtUser.setPromptText("Ingrese su usuario"); // A placeholder
        txtUser.setMaxWidth(Double.MAX_VALUE);
        txtUser.setPrefWidth(341);
        txtUser.setPrefHeight(44);
        
        // Input 2
        
        Label label_pass = new Label("Pass");
        label_pass.setFont(new Font(20));
        
        PasswordField txtPass = new PasswordField();
        txtPass.setFont(new Font(18));
        txtPass.setPromptText("Ingrese la contraseña"); // Placeholder
        txtPass.setMaxWidth(Double.MAX_VALUE);
        txtPass.setPrefWidth(341);
        txtPass.setPrefHeight(44);
        
        // Button Login
        
        Button btn_login = new Button("Login");
        btn_login.setMaxWidth(Double.MAX_VALUE);
        btn_login.setPrefWidth(370);
        btn_login.setFont(new Font(20));
        btn_login.setCursor(Cursor.HAND);
        
        // Button Register
        
        Button btn_register = new Button("Register");
        btn_register.setMaxWidth(Double.MAX_VALUE);
        btn_register.setPrefWidth(370);
        btn_register.setFont(new Font(20));
        btn_register.setCursor(Cursor.HAND);
        
        btn_login.setOnAction(new EventHandler() {
        
            @Override
            public void handle(Event event) {
            
                conexion bd = new conexion("trabajo_final");
                bd.conectar();
                
                String dni = txtUser.getText();
                String pass = txtPass.getText();
                
                String sql = String.format("select * from (select * from clientes union select * from administradores) as usuario where usuario.dni='%s' and usuario.contraseña = '%s'",dni,pass);
                bd.consultas_sql(sql);
                
                try {
                    
                    while (bd.rs.next()) {
                        
                        if (dni.equals(bd.rs.getString(4)) && pass.equals(bd.rs.getString(6))) {
                        
                            // System.out.println("Los datos son correctos");
                            // System.out.println(bd.rs.getString(3));
                            
                            String id_usuario = bd.rs.getString(1);
                            String nombre = bd.rs.getString(3);
                            String dni_us = bd.rs.getString(4);
                            String permisos = bd.rs.getString(2);
                            String sexo = bd.rs.getString(5);
                            
                            // Administrativo
                            
                            if (bd.rs.getString(2).equals("1")){
                                
                                administradorInterface adm = new administradorInterface();
                                try {
                                    
                                    adm.administradorWindow(nombre,dni_us,permisos,sexo);
                                    
                                } catch (FileNotFoundException ex) {
                                    
                                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                                
                                }
                                
                            // Usuarios, mientras que tampoco tenga cuenta bancaria no podra ingresar al apartado administrativo
                            
                            } else if (bd.rs.getString(2).equals("2")) {
                                
                                usuarioInterface usr = new usuarioInterface();
                                
                                try {
                                    
                                    usr.usuarioWindow(nombre,dni_us,permisos,sexo,id_usuario);
                                    
                                } catch (FileNotFoundException ex) {
                                    
                                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                
                            }

                        } else {
                            
                            JOptionPane.showMessageDialog(null,"Ingrese los datos correctamente");
                            break;
                          
                        }
                        
                        // Limpiando los campos
                        
                        txtUser.clear();
                        txtPass.clear();
                        
                    }
                    
                } catch (SQLException ex) {
                    
                    System.out.println("No se encontraron datos");
                    JOptionPane.showMessageDialog(null, "Usuario no registrado");
                    
                }
                
            }
            
        });
        
        btn_register.setOnAction(new EventHandler(){
        
            @Override
            public void handle(Event event) {
            
                register rg = new register();
                rg.registerWindow();
            
            }
        
        });
        
        VBox container_login = new VBox();
        VBox container_login_elementos = new VBox();
        
        // Añadiendo elementos al container de los elementos
        container_login_elementos.getChildren().addAll(label_user,txtUser,label_pass,txtPass,btn_login,btn_register);
        // Le daremos una alineacion al centro
        container_login_elementos.setAlignment(Pos.TOP_LEFT);
        
        // Le agregaremos margin a los elementos label y al boton
        
        VBox.setMargin(label_user, new Insets(10,0,0,0));
        VBox.setMargin(label_pass, new Insets(10,0,0,0));
        VBox.setMargin(btn_login, new Insets(10,0,0,0));
        VBox.setMargin(btn_register, new Insets(10,0,0,0));
        
        // Ahora lo añadiremos al contianer principal
        
        container_login.getChildren().addAll(label_title,container_login_elementos);
        
        // Le añadiremos un pref width
        
        container_login.prefWidth(422);
        
        // Y lo posicionaremos en el centro
        
        container_login.setAlignment(Pos.CENTER);
        
        // Le agregaremos un margin al contenedor de los elementos
        
        VBox.setMargin(container_login_elementos, new Insets(0,30,0,30));
        
        // Agregaremos una imagen y un titulo al costado de nuestro login
        
        ImageView imageLogo;
        FileInputStream file = new FileInputStream("E:\\Exposicion\\Java_Trabajo_Final\\com.trabajo_final\\src\\com\\media\\img\\bank.png");
        Image image = new Image(file);        
        imageLogo = new ImageView(image);
        
        // Alto definido para la imagen
        imageLogo.setFitHeight(350);
        
        // Le agregamos un margen a la imagen
        
        VBox.setMargin(imageLogo, new Insets(10,10,10,10));
        
        // Ponemos un titulo
        
        Label txtCompanyName = new Label("New Perú Bank");
        txtCompanyName.setTextFill(Color.web("#fff"));
        txtCompanyName.setFont(new Font(35));
        
        // Añadimos los elementos al layout
        
        VBox containerImagen = new VBox();
        
        containerImagen.getChildren().addAll(txtCompanyName,imageLogo);
        
        // Le damos una respectiva pref width
        
        containerImagen.prefWidth(350);
        
        // Lo posicionamos en el centro
        
        containerImagen.setAlignment(Pos.CENTER);
        
        // Le damos un background de fondo
        
        containerImagen.setBackground(new Background(new BackgroundFill(Color.web("#30373e"), CornerRadii.EMPTY, Insets.EMPTY)));
                        
        // Todos estos elementos que hemos creados van a ser deplegados dentro de un Horizontal Box
        
        HBox root = new HBox();
        
        root.getChildren().addAll(containerImagen,container_login);
        
        // Este metodo define que es lo que pasa con estos nodos cuando hacemos cambios en la pantalla 
        // (redimenzionar) asi se actualizara la dimension de
        // la ventana
        
        HBox.setHgrow(container_login, Priority.ALWAYS);
        HBox.setHgrow(containerImagen, Priority.ALWAYS);
        
        Scene scene = new Scene(root, 854, 503);
        
        
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
            
        launch(args);
    
    }
    
}
