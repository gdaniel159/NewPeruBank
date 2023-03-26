package com.Retiro;

import com.conexion.bd.conexion;
import com.trabajo_final.usuarioInterface;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class Retiro extends Stage {

    public void retiroWindow(String nombre, String id) throws Exception {

        //BorderPane principal
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(30));

        // Cabecera
        HBox header = new HBox();

        ImageView imageLogo;
        FileInputStream file = new FileInputStream("E:\\Exposicion\\Java_Trabajo_Final\\com.trabajo_final\\src\\com\\media\\img\\bank.png");
        Image image = new Image(file);
        imageLogo = new ImageView(image);

        imageLogo.setFitHeight(60);
        imageLogo.setPreserveRatio(true);

        Label title = new Label("New Peru Bank");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
        title.setAlignment(Pos.CENTER_LEFT);

        Label retiroLabel = new Label("RETIRO");
        retiroLabel.setFont(Font.font("Arial", FontWeight.BOLD, 19));
        retiroLabel.setAlignment(Pos.CENTER_RIGHT);

        HBox rightBox = new HBox(retiroLabel);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(retiroLabel, new Insets(0, 10, 0, 0));
        HBox.setHgrow(rightBox, Priority.ALWAYS);

        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(10);

        header.getChildren().addAll(imageLogo, title, rightBox);
        borderPane.setTop(header);

        //------------------------------------//       
        //           Separador                //
        //------------------------------------//
        Separator separator = new Separator();
        separator.setMaxWidth(Double.MAX_VALUE);
        separator.setPadding(new Insets(15, 10, 0, 0));

        //------------------------------------------------------//       
        // VBox para el nombre del cliente y el saldo actual :)
        //------------------------------------------------------//
        conexion con_ret = new conexion("trabajo_final");
        con_ret.conectar();

        String sql = "SELECT clientes.nombres, cuenta_bancaria.saldo FROM clientes LEFT JOIN cuenta_bancaria ON cuenta_bancaria.id_usuario = clientes.id_usuario;";
        con_ret.consultas_sql(sql);

        // Nombre
        VBox nombre_box = new VBox();
        nombre_box.setAlignment(Pos.CENTER);
        nombre_box.setSpacing(20);

        Label nombreLabel = new Label();
        Label montoLabel = new Label();

        try {

            while (con_ret.rs.next()) {

                if (con_ret.rs.getString(1).equals(nombre)) {

                    String nombre_r = con_ret.rs.getString(1);
                    String monto = con_ret.rs.getString(2);
                    nombreLabel.setText(nombre_r);
                    montoLabel.setText(monto + " soles");

                }

            }

        } catch (SQLException ex) {

            Logger.getLogger(usuarioInterface.class.getName()).log(Level.SEVERE, null, ex);

        }

        nombreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        nombreLabel.setAlignment(Pos.CENTER);

        // Saldo
        HBox SaldoActualBox = new HBox();
        SaldoActualBox.setAlignment(Pos.CENTER);
        SaldoActualBox.setSpacing(10);

        Label saldoLabel = new Label("Saldo actual:");
        saldoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        montoLabel.setFont(Font.font("Arial", 30));
        montoLabel.setStyle("-fx-text-fill: blue;");

        // Se muestra la informacion :) y se juntan las etiquetas 
        SaldoActualBox.getChildren().addAll(saldoLabel, montoLabel);

        nombre_box.getChildren().addAll(nombreLabel, SaldoActualBox);

        //-------------------------------------------------//
        //  GridPane para los botones de montos de retiro :|
        //-------------------------------------------------//
        GridPane botonesGrid = new GridPane();

        botonesGrid.setAlignment(Pos.CENTER);
        botonesGrid.setHgap(25);
        botonesGrid.setVgap(25);
        botonesGrid.setPadding(new Insets(10));

        Button btn_diez = new Button("10");
        btn_diez.setPrefSize(400, 200);
        btn_diez.setFont(Font.font("Arial", FontWeight.BOLD, 19));

        Button btn_cincuenta = new Button("50");
        btn_cincuenta.setPrefSize(400, 200);
        btn_cincuenta.setFont(Font.font("Arial", FontWeight.BOLD, 19));

        Button btn_cien = new Button("100");
        btn_cien.setPrefSize(400, 200);
        btn_cien.setFont(Font.font("Arial", FontWeight.BOLD, 19));

        Button btn_quinientos = new Button("500");
        btn_quinientos.setPrefSize(400, 200);
        btn_quinientos.setFont(Font.font("Arial", FontWeight.BOLD, 19));

        Button btn_mil = new Button("1000");
        btn_mil.setPrefSize(400, 200);
        btn_mil.setFont(Font.font("Arial", FontWeight.BOLD, 19));

        Button btn_cincomil = new Button("5000");
        btn_cincomil.setPrefSize(400, 200);
        btn_cincomil.setFont(Font.font("Arial", FontWeight.BOLD, 19));

        botonesGrid.add(btn_diez, 0, 0);
        botonesGrid.add(btn_cincuenta, 1, 0);
        botonesGrid.add(btn_cien, 0, 1);
        botonesGrid.add(btn_quinientos, 1, 1);
        botonesGrid.add(btn_mil, 0, 2);
        botonesGrid.add(btn_cincomil, 1, 2);

        borderPane.setCenter(botonesGrid);

        // La seccion de ingresar dinero
        //----------------------------//
        //    Hbox saldo y boton      //
        //----------------------------//
        TextField montoTextField = new TextField();
        montoTextField.setPromptText("Monto a retirar");
        montoTextField.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        montoTextField.setPrefSize(220, 35);

        Button retirarButton = new Button("Retirar");
        retirarButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        retirarButton.setPrefSize(85, 35);

        HBox saldoHBox = new HBox();
        saldoHBox.getChildren().addAll(new Label("Ingresar dinero de Retiro: "), montoTextField, retirarButton);

        HBox.setMargin(montoTextField, new Insets(0, 10, 0, 10));
        HBox.setMargin(retirarButton, new Insets(0, 10, 0, 10));

        saldoHBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(saldoHBox);

        //-----------------------------//
        //       VBOX PRINCIPAL        //
        //-----------------------------//
        VBox mainContainer = new VBox();
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setSpacing(50);
        mainContainer.getChildren().addAll(separator, nombre_box, botonesGrid, saldoHBox);
        borderPane.setCenter(mainContainer);

        //-------------------------//
        //    SE MUESTRA SCENE     //
        //-------------------------//
        Scene Escena = new Scene(borderPane, 800, 600);

        Stage retiro = new Stage();

        retiro.setTitle("Retiro New Perú Bank");

        // Tamaño minimo y maximo
        retiro.setMinWidth(640);
        retiro.setMinHeight(480);
        retiro.setMaxWidth(1024);
        retiro.setMaxHeight(768);

        retiro.setScene(Escena);

        retiro.show();

        // Funcionalidad de los botones
        retiro_funcionalidad rt = new retiro_funcionalidad();

        // Funcionalidad del boton 10
        rt.funcionalidad(btn_diez, 10, retiro,id);
        // Funcionalidad del boton 50
        rt.funcionalidad(btn_cincuenta, 50, retiro,id);
        // Funcionalidad del boton 100
        rt.funcionalidad(btn_cien, 100, retiro,id);
        // Funcionalidad del boton 500
        rt.funcionalidad(btn_quinientos, 500, retiro,id);
        // Funcionalidad del boton 1000
        rt.funcionalidad(btn_mil, 1000, retiro,id);
        // Funcionalidad del boton 5000
        rt.funcionalidad(btn_cincomil, 5000, retiro,id);

        // Funcionalidad del boton
        retirarButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {

                    int amount = Integer.parseInt(montoTextField.getText());

                    conexion bd_cuenta = new conexion("trabajo_final");
                    bd_cuenta.conectar();
                    String sql1 = "SELECT clientes.nombres, cuenta_bancaria.numero_cuenta, cuenta_bancaria.saldo FROM clientes LEFT JOIN cuenta_bancaria ON cuenta_bancaria.id_usuario = clientes.id_usuario;";
                    bd_cuenta.consultas_sql(sql1);

                    while (bd_cuenta.rs.next()) {

                        double saldo_actual = bd_cuenta.rs.getDouble("saldo");

                        if (saldo_actual >= amount) {

                            double nuevo_saldo = saldo_actual - amount;

                            String sql2 = "UPDATE cuenta_bancaria SET saldo = " + nuevo_saldo + " WHERE numero_cuenta = " + bd_cuenta.rs.getString(2);

                            bd_cuenta.update(sql2);

                            JOptionPane.showMessageDialog(null, "Retiro realizado con exito");

                            // Generacion de el movimiento
                            conexion tbMov = new conexion("trabajo_final");
                            tbMov.conectar();

                            String maxIdT = "SELECT COUNT(*) FROM movimiento";

                            tbMov.consultas_sql(maxIdT);

                            if (tbMov.rs.next()){
                                
                                int idCuenta = tbMov.rs.getInt(1) + 1;
                                int idUser = Integer.parseInt(id);
                                int tipo = 0;
                                int idNumCount = 0;

                                String movTip = "SELECT * FROM tipo_movimiento";
                                tbMov.consultas_sql(movTip);
                                
                                if (tbMov.rs.next()) {

                                    if (tbMov.rs.getString(1).equals("Deposito")) {

                                        tipo = 1;

                                    } else {

                                        tipo = 2;

                                    }

                                }
                                
                                String cuenta = "SELECT numero_cuenta FROM cuenta_bancaria WHERE id_usuario = '" + id + "'";
                                
                                tbMov.consultas_sql(cuenta);
                                
                                String num_cuenta = "";
                                
                                if (tbMov.rs.next()){
                                    
                                    num_cuenta = tbMov.rs.getString(1);
                                    
                                }
                                
                                String idAccount = "SELECT cb.id_cuenta_bancaria FROM cuenta_bancaria cb WHERE cb.numero_cuenta = '" + num_cuenta + "'";

                                tbMov.consultas_sql(idAccount);
                                
                                if (tbMov.rs.next()) {

                                    idNumCount = Integer.parseInt(tbMov.rs.getString(1));

                                }
                                
                                LocalDateTime datetime = LocalDateTime.now();
                                
                                String insertMov = "INSERT INTO movimiento (id_transaccion,id_usuario,id_tipo_movimiento,id_cuenta_bancaria,monto,fecha) "
                                    + " VALUES ('" + idCuenta + "', '" + idUser + "', '" + tipo + "', '" + idNumCount + "', '" + amount + "', '" + datetime + "') ";

                                tbMov.update(insertMov);
                                
                                
                                
                            }
                            
                            break;

                        } else {

                            JOptionPane.showMessageDialog(null, "Saldo Insuficiente");
                            break;
                            
                        }
                        
                    }

                } catch (SQLException ex) {

                    Logger.getLogger(Retiro.class.getName()).log(Level.SEVERE, null, ex);

                }

                close(retiro);

            }
        });

    }

    public void close(Stage win) {

        win.close();

    }
}
