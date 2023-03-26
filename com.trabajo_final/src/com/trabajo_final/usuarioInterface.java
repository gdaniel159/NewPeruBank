package com.trabajo_final;

import com.Retiro.Retiro;
import com.Transferencia.transferencia;
import com.conexion.bd.conexion;
import com.deposito.Deposito;
import com.transacciones.Ultimas_transacciones;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class usuarioInterface extends Stage{
    
    public void usuarioWindow(String nombre,String dni, String permisos, String sexo, String id) throws FileNotFoundException{
        
        // ======== Desplegable ======== //
        
        // Creamos el menu
        
        MenuBar menuButton = new MenuBar();
        
        // Agregamos el elemento
        
        Menu sesion = new Menu("Sesion");
        
        MenuItem elemento1 = new MenuItem("Cerrar Sesion");
        
        // Logica para cerrar la ventana
        
        sesion.getItems().add(elemento1);
        
        // Lo añadimos al Menu Button
        
        menuButton.getMenus().add(sesion);
        
        // ======== Contenedor del contenido de la izquierda ======== //
        
        VBox leftContent = new VBox();
        
        // ======== HEADER =========
        
        // Imagen referencial para el logo
        
        ImageView imageLogo;
        FileInputStream file = new FileInputStream("E:\\Exposicion\\Java_Trabajo_Final\\com.trabajo_final\\src\\com\\media\\img\\bank.png");
        Image image = new Image(file);        
        imageLogo = new ImageView(image);
        
        imageLogo.setFitHeight(80);
        imageLogo.setFitWidth(80);
        
        // Titulo
        
        Label title = new Label("New Perú Bank");
        title.setFont(new Font(50));
        
        // Contenedor del header
        
        HBox containerHeader = new HBox();
        
        containerHeader.setBackground(new Background(new BackgroundFill(Color.web("#f1f2f6"), CornerRadii.EMPTY, Insets.EMPTY)));
        
        HBox.setMargin(imageLogo, new Insets(10,10,10,10));
        HBox.setMargin(title, new Insets(10,10,10,10));
        
        containerHeader.getChildren().addAll(imageLogo,title);
        
        // Definimos su ancho
        
        containerHeader.setMaxWidth(Double.MAX_VALUE);
        containerHeader.setAlignment(Pos.CENTER);
        
        // ======= Apartado de Botones =========
        
        Button btn_retirar = new Button("Retirar");
        btn_retirar.setFont(new Font(17));
        Button btn_depositar = new Button("Depositar");
        btn_depositar.setFont(new Font(17));
        Button btn_transferir = new Button("Transferir");
        btn_transferir.setFont(new Font(17));
        Button btn_ultimas_transacciones = new Button("Ultimas transacciones");
        btn_ultimas_transacciones.setFont(new Font(17));
        
        // Definimos su tamaño
        
        btn_retirar.setPrefWidth(200);
        btn_retirar.setPrefHeight(90);
        
        btn_depositar.setPrefWidth(200);
        btn_depositar.setPrefHeight(90);
        
        btn_transferir.setPrefWidth(200);
        btn_transferir.setPrefHeight(90);
        
        btn_ultimas_transacciones.setPrefWidth(200);
        btn_ultimas_transacciones.setPrefHeight(90);
        
        // Funcionamiento
        
        btn_retirar.setOnAction(new EventHandler(){
        
            @Override
            public void handle(Event event) {
            
                try {
                    
                    Retiro rt = new Retiro();
                    rt.retiroWindow(nombre,id);
                    
                } catch (Exception ex) {
                    
                    Logger.getLogger(usuarioInterface.class.getName()).log(Level.SEVERE, null, ex);
                
                }
            
            }
        
        });
        
        btn_depositar.setOnAction(new EventHandler(){
        
            @Override
            public void handle(Event event) {
            
                try {
                    
                    Deposito dp = new Deposito();
                    dp.depositoWindow(id,nombre);
                    
                } catch (Exception ex) {
                    
                    Logger.getLogger(usuarioInterface.class.getName()).log(Level.SEVERE, null, ex);
                
                }
            
            }
        
        });
        
        btn_transferir.setOnAction(new EventHandler(){
        
            @Override
            public void handle(Event event) {
            
                try {
                    
                    transferencia tf = new transferencia();
                    tf.transferenciaWindow(nombre,id);
                    
                } catch (Exception ex) {
                    
                    Logger.getLogger(usuarioInterface.class.getName()).log(Level.SEVERE, null, ex);
                
                }
            
            }
        
        });
        
        btn_ultimas_transacciones.setOnAction(new EventHandler(){
        
            @Override
            public void handle(Event event) {
            
                try {
                    
                    Ultimas_transacciones ut = new Ultimas_transacciones();
                    ut.UltTransationsWindow(nombre);
                    
                } catch (Exception ex) {
                    
                    Logger.getLogger(usuarioInterface.class.getName()).log(Level.SEVERE, null, ex);
                
                }
            
            }
        
        });
        
        // Contenedor de los botones
        
        GridPane btn_group = new GridPane();
       
        btn_group.setVgap(10);
        btn_group.setHgap(10);
        btn_group.setAlignment(Pos.CENTER);
        
        btn_group.add(btn_retirar,0,0);
        btn_group.add(btn_depositar,1,0);
        btn_group.add(btn_transferir,0,1);
        btn_group.add(btn_ultimas_transacciones,1,1);
        
        btn_group.setBackground(new Background(new BackgroundFill(Color.web("#f5f6fa"), CornerRadii.EMPTY, Insets.EMPTY)));
        
        btn_group.setPrefHeight(503);
        
        // Ahora añadiremos esos dos componentes o containers a nuestro layout leftContent
        
        leftContent.getChildren().addAll(containerHeader,btn_group);
        leftContent.setPrefWidth(504);
        leftContent.setPrefHeight(503);
        
        VBox.setVgrow(containerHeader, Priority.ALWAYS);
        VBox.setVgrow(btn_group, Priority.ALWAYS);
        
        // ======== Contenedor del contenido de la derecha ======== //
        
        // Ahora si crearemos el container raiz
        
        VBox rightContent = new VBox();
        
        // Imagen del usuario
        
        ImageView imageProfile;
        FileInputStream file_2 = new FileInputStream("E:\\Exposicion\\Java_Trabajo_Final\\com.trabajo_final\\src\\com\\media\\img\\profile-picture.png");
        Image imagePro = new Image(file_2);        
        imageProfile = new ImageView(imagePro);
        
        imageProfile.setFitHeight(80);
        imageProfile.setFitWidth(80);
        
        VBox.setMargin(imageProfile, new Insets(30,10,10,10));
        
        // Descripcion del usuario
        
        Label nombre_cliente = new Label(nombre);
        nombre_cliente.setTextFill(Color.web("#fff"));
        nombre_cliente.setFont(new Font(20));
        VBox.setMargin(nombre_cliente, new Insets(0,10,10,10));
        
        Label user_dni = new Label("DNI: " + dni);
        user_dni.setTextFill(Color.web("#fff"));
        user_dni.setFont(new Font(16));
        VBox user_dni_box = new VBox();
        user_dni_box.getChildren().add(user_dni);
        // INSETS : arriba, derecha, abajo, izquierda
        VBox.setMargin(user_dni_box, new Insets(10,0,10,20));

        // Sexo y permisos de cuenta
        
        Label user_sexo = new Label();
        
        if (sexo.equals("M")){
            
            sexo = "Masculino";
            user_sexo.setText("Sexo:  " + sexo);
            
            
        } else if (sexo.equals("F")){
            
            sexo = "Femenino";
            user_sexo.setText("Sexo:  " + sexo);
            
        }
        
        user_sexo.setTextFill(Color.web("#fff"));
        user_sexo.setFont(new Font(16));
        
        // Validamos los permisos del usuario
        Label perm = new Label();
        if (permisos.equals("2")){
            permisos = "Cliente";
            perm.setText("Permisos: " + permisos);
        }
        
        perm.setTextFill(Color.web("#fff"));
        perm.setFont(new Font(16));
        
        HBox seperm = new HBox();
        seperm.getChildren().addAll(user_sexo,perm);
        seperm.setSpacing(50);
        // INSETS : arriba, derecha, abajo, izquierda
        VBox.setMargin(seperm, new Insets(10,0,10,20));
                
        // Numero de cuenta
        
        // Para traer los datos de el numero de cuenta traemos la consulta de la base de datos sobre cuenta
        // bancaria
        
        conexion bd_cub = new conexion("trabajo_final");
        bd_cub.conectar();
        
        String sql_num_cuenta = "SELECT cuenta_bancaria.numero_cuenta, clientes.nombres FROM cuenta_bancaria LEFT JOIN clientes ON cuenta_bancaria.id_usuario = clientes.id_usuario;";
        bd_cub.consultas_sql(sql_num_cuenta);
        
        Label num_cuenta = new Label();
        
        try {
            
            while (bd_cub.rs.next()) {
                
                if (bd_cub.rs.getString(2).equals(nombre)){
                
                    String num = bd_cub.rs.getString(1);
                    num_cuenta.setText("Numero de cuenta: " + num);
                    
                }
                
            }
            
        } catch (SQLException ex) {
            
            Logger.getLogger(usuarioInterface.class.getName()).log(Level.SEVERE, null, ex); 
        
        }
        
        num_cuenta.setTextFill(Color.web("#fff"));
        VBox num_cuenta_box = new VBox();
        num_cuenta_box.getChildren().add(num_cuenta);
        
        VBox.setMargin(num_cuenta_box, new Insets(10,0,10,20));
        
        num_cuenta.setFont(new Font(16));
        
        bd_cub.disconnect();
        
        // Saldo actual
        
        conexion bd_saldo = new conexion("trabajo_final");
        bd_saldo.conectar();
        
        String sql_saldo = "SELECT cuenta_bancaria.saldo, clientes.nombres \n" +
                           "FROM cuenta_bancaria \n" +
                           "LEFT JOIN clientes ON clientes.id_usuario = cuenta_bancaria.id_usuario\n" +
                           "WHERE clientes.id_usuario = '" + id + "';";
        
        bd_saldo.consultas_sql(sql_saldo);
        
        Label saldo = new Label();
        
        try {
            
            while (bd_saldo.rs.next()) {
                
                String num_saldo = bd_saldo.rs.getString(1);
                
                actualizarSaldo(saldo, Integer.parseInt(num_saldo));
                
            }
            
        } catch (SQLException ex) {
            
            Logger.getLogger(usuarioInterface.class.getName()).log(Level.SEVERE, null, ex); 
        
        }
        
        saldo.setTextFill(Color.web("#fff"));
        saldo.setFont(new Font(16));
        VBox saldo_box = new VBox();
        saldo_box.getChildren().add(saldo);
        
        VBox.setMargin(saldo_box, new Insets(10,0,10,20));
        
        bd_saldo.disconnect();
        
        // Añadimos todo al containerRight
        
        rightContent.setBackground(new Background(new BackgroundFill(Color.web("#30373e"), CornerRadii.EMPTY, Insets.EMPTY)));

        rightContent.getChildren().addAll(imageProfile,nombre_cliente,user_dni_box,seperm,num_cuenta_box,saldo_box);
        rightContent.setPrefWidth(350);
        rightContent.setPrefHeight(503);
        rightContent.setAlignment(Pos.TOP_CENTER);
        
        VBox.setVgrow(imageProfile, Priority.ALWAYS);
        VBox.setVgrow(nombre_cliente, Priority.ALWAYS);
        VBox.setVgrow(user_dni_box, Priority.ALWAYS);
        VBox.setVgrow(seperm, Priority.ALWAYS);
        VBox.setVgrow(num_cuenta_box, Priority.ALWAYS);
        VBox.setVgrow(saldo_box, Priority.ALWAYS);
        
        // ==== Container Principal de los elementos de la izquierda y derecha ==== //
        
        HBox mainContainer = new HBox();
        
        mainContainer.getChildren().addAll(leftContent,rightContent);
        
        HBox.setHgrow(leftContent, Priority.ALWAYS);
        HBox.setHgrow(rightContent, Priority.ALWAYS);
        
        // ==== Container Principal de la ventana ==== //
        
        VBox root = new VBox();
        
        root.getChildren().addAll(menuButton,mainContainer);
        
        VBox.setVgrow(menuButton, Priority.ALWAYS);
        VBox.setVgrow(mainContainer, Priority.ALWAYS);
        
        Scene user_scene = new Scene(root,854,503);

        // Creamos un nuevo escenario

        Stage usuarios = new Stage();
        usuarios.setTitle("Aparatado Usuario");
        usuarios.setScene(user_scene);
        usuarios.initModality(Modality.APPLICATION_MODAL);
        usuarios.show();
        
        // Cerrar la pagina
        
        elemento1.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent t) {
                
                usuarios.close();
                
            }
        
        });
        
    }
    
    public void actualizarSaldo(Label s, double saldo){
        
        s.setText("Saldo: " + saldo + " soles");
        
    }
    
}
