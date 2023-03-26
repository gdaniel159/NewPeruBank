package com.deposito;

import com.conexion.bd.conexion;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class Deposito extends Stage {

    public void depositoWindow(String id, String nombre) {

        //Creacion del fondo
        BorderPane borderPane = new BorderPane();
        Pane panel = new Pane();

        // Agrega el panel al centro del BorderPane
        borderPane.setCenter(panel);

        // Define los márgenes del panel
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        // Crear etiqueta de título
        Label titulo = new Label("Depositación");
        titulo.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-underline: true;");
        titulo.setPadding(new Insets(0, 0, 5, 310));

        //Crear un panel para el pie
        Pane pie = new Pane();
        pie.setStyle("-fx-background-color: gray; -fx-opacity: 1.0;");

        // Establecer el tamaño y la posición del pie
        pie.setPadding(new Insets(70, 0, 0, 0));
        borderPane.setBottom(pie);

        //Creacion de Subtitulos
        // Subtitulo
        Label Subtitulo = new Label("Datos de la Cuenta:");
        Subtitulo.setStyle("-fx-font-size: 21px; -fx-font-weight: bold; -fx-underline: true;");
        Subtitulo.setPadding(new Insets(0, 0, 30, 30));

        // Subtitulo2
        Label Subtitulo2 = new Label("Monto a depositar:");
        Subtitulo2.setStyle("-fx-font-size: 21px; -fx-font-weight: bold; -fx-underline: true;");
        Subtitulo2.setPadding(new Insets(0, 0, 40, 30));

        // Crear etiquetas y campos de texto
        //Crear nombre
        Label nombreLabel = new Label("Nombre:");
        nombreLabel.setStyle("-fx-font-size: 18px;");

        ComboBox nombreField = new ComboBox();

        nombreField.setPrefSize(300, 30);

        // Consutla de los nombres en la tabla clientes
        conexion bd_nombres = new conexion("trabajo_final");
        bd_nombres.conectar();

        String sql = "SELECT nombres FROM clientes";

        List<String> nombres = new ArrayList<>();

        bd_nombres.consultas_sql(sql);

        try {

            while (bd_nombres.rs.next()) {

                nombres.add(bd_nombres.rs.getString("nombres"));

            }

        } catch (SQLException ex) {

            Logger.getLogger(Deposito.class.getName()).log(Level.SEVERE, null, ex);

        }

        nombreField.setItems(FXCollections.observableArrayList(nombres));

        // ============================
        HBox nombreInput = new HBox();
        nombreInput.getChildren().addAll(nombreLabel, nombreField);
        nombreInput.setSpacing(10);

        //Crear fecha
        Label fechaLabel = new Label("Fecha:");
        fechaLabel.setStyle("-fx-font-size: 20px;");
        DatePicker fechaPicker = new DatePicker();
        fechaPicker.setPrefSize(130, 30);

        HBox fechaInput = new HBox();
        fechaInput.getChildren().addAll(fechaLabel, fechaPicker);
        fechaInput.setSpacing(10);

        // Crear cuenta
        Label cuentaLabel = new Label("Número de cuenta:");
        cuentaLabel.setStyle("-fx-font-size: 18px;");
        TextField cuentaField = new TextField();
        cuentaField.setPrefSize(290, 30);

        // Evento para la seleccion del usuario al que se le hara el desposito
        nombreField.setOnAction(event -> {

            // Seleccionamos el valor del desplegable
            String selectedName = nombreField.getValue().toString();

            // Hacemos la consulta a la base de datos para recoger el nombre y la cuenta bancaria
            String sql2 = "SELECT clientes.nombres, cuenta_bancaria.numero_cuenta FROM clientes LEFT JOIN cuenta_bancaria ON cuenta_bancaria.id_usuario = clientes.id_usuario WHERE clientes.nombres = '" + selectedName + "'";

            bd_nombres.consultas_sql(sql2);

            try {

                if (bd_nombres.rs.next()) {

                    // Obtenemos el numero de cuenta y lo agregamos al cuentaField
                    String num_cuenta = bd_nombres.rs.getString("numero_cuenta");
                    cuentaField.setText(num_cuenta);

                } else {

                    cuentaField.setText("");

                }

            } catch (SQLException ex) {

                Logger.getLogger(Deposito.class.getName()).log(Level.SEVERE, null, ex);

            }
        });

        // =========================
        HBox cuentaInput = new HBox();
        cuentaInput.getChildren().addAll(cuentaLabel, cuentaField);
        cuentaInput.setSpacing(10);
        cuentaInput.setPadding(new Insets(0, 0, 30, 50));

        //Crear cantidad
        Label cantidadLabel = new Label("Cantidad S/.");
        cantidadLabel.setStyle("-fx-font-size: 18px;");
        cantidadLabel.setPadding(new Insets(21, 20, 41, 0));
        TextField cantidadField = new TextField();
        cantidadField.setStyle("-fx-font-size: 25px;");
        cantidadField.setPrefSize(200, 70);

        HBox cantidadInput = new HBox();
        cantidadInput.getChildren().addAll(cantidadLabel, cantidadField);
        cantidadInput.setSpacing(5);
        cantidadInput.setPadding(new Insets(0, 0, 20, 200));

        // Creacion de los botones
        //Boton limpiar
        Button limpiarButton = new Button("Limpiar");
        limpiarButton.setOnAction(e -> limpiarCampos(cuentaField, fechaPicker));

        //Boton depositar
        Button depositarButton = new Button("Depositar");

        //Boton cancelar
        Button cancelarButton = new Button("Cancelar");

        //Creacion de los HBox
        //Creamos HBox para nombre y fecha
        HBox rootNF = new HBox();
        rootNF.getChildren().addAll(nombreInput, fechaInput);
        rootNF.setSpacing(50);
        rootNF.setPrefWidth(100);
        rootNF.setPadding(new Insets(0, 0, 50, 50));

        //Creamos HBox para el boton depositar y cancelar
        HBox botonera = new HBox();
        botonera.getChildren().addAll(depositarButton, cancelarButton);
        botonera.setSpacing(10);
        botonera.setPadding(new Insets(0, 0, 0, 400));

        //Creamos HBox para el boton limpiar
        HBox limpiarBox = new HBox();
        limpiarBox.getChildren().addAll(limpiarButton);
        limpiarBox.setPadding(new Insets(0, 100, 0, 0));

        //Creamos HBox para limpiar y botonera
        HBox BL = new HBox();
        BL.getChildren().addAll(limpiarBox, botonera);
        botonera.setSpacing(20);
        BL.setPadding(new Insets(20, 0, 40, 40));

        pie.getChildren().add(BL);

        VBox root = new VBox();

        root.setMargin(root, new Insets(5, 40, 5, 40));
        root.setStyle("-fx-border-color: black; -fx-border-width: 3px;");

        root.getChildren().addAll(titulo, Subtitulo, rootNF, cuentaInput, Subtitulo2, cantidadInput, pie);

        Scene scene = new Scene(root, 854, 503);

        Stage stage = new Stage();

        stage.setScene(scene);

        stage.setTitle("Deposito");
        stage.show();

        cancelarButton.setOnAction((ActionEvent t) -> {
            close(stage);
        });

        depositarButton.setOnAction((ActionEvent e) -> {

            try {

                String selectedName = nombreField.getValue().toString(); // Nombre
                String num_cuenta = cuentaField.getText(); // Numero de cuenta
                // LocalDate fecha = fechaPicker.getValue(); // Fecha
                String monto = cantidadField.getText(); // monto

                // Primero hacemos el deposito
                conexion tb_deposito = new conexion("trabajo_final");
                tb_deposito.conectar();

                // Traemos el monto actual
                String montoActual = "SELECT saldo FROM cuenta_bancaria\n"
                        + "WHERE id_usuario = (SELECT id_usuario FROM clientes WHERE nombres = '" + nombre + "');";

                tb_deposito.consultas_sql(montoActual);

                while (tb_deposito.rs.next()) {

                    // double saldoActual = Double.parseDouble(tb_deposito.rs.getString("saldo"));

                    String montoUser = "SELECT saldo FROM cuenta_bancaria\n"
                            + "WHERE id_usuario = (SELECT id_usuario FROM clientes WHERE nombres = '" + selectedName + "');";

                    tb_deposito.consultas_sql(montoUser);

                    // Calculamos el nuevo balance
                    if (tb_deposito.rs.next()) {

                        double montoDeposito = Double.parseDouble(monto);
                        double nuevoMonto = Double.parseDouble(tb_deposito.rs.getString(1)) + montoDeposito;

                        String sql1 = "UPDATE cuenta_bancaria\n"
                                + "SET saldo = '" + nuevoMonto + "'"
                                + "WHERE id_usuario = (SELECT id_usuario FROM clientes WHERE nombres = '" + selectedName + "');";

                        tb_deposito.update(sql1);

                        JOptionPane.showMessageDialog(null, "Deposito realizado con exito");

                        // Generacion de el movimiento
                        conexion tbMov = new conexion("trabajo_final");
                        tbMov.conectar();

                        String maxIdT = "SELECT COUNT(*) FROM movimiento";

                        tbMov.consultas_sql(maxIdT);

                        if (tbMov.rs.next()) {

                            int idCuenta = tbMov.rs.getInt(1) + 1;
                            int idUser = Integer.parseInt(id);
                            int tipo = 0;
                            int idNumCount = 0;

                            String movTip = "SELECT * FROM tipo_movimiento";
                            tbMov.consultas_sql(movTip);

                            if (tbMov.rs.next()) {

                                if (tbMov.rs.getString(1).equals("Retiro")) {

                                    tipo = 2;

                                } else {

                                    tipo = 1;

                                }

                            }

                            String idAccount = "SELECT cb.id_cuenta_bancaria FROM cuenta_bancaria cb WHERE cb.numero_cuenta = '" + num_cuenta + "'";

                            tbMov.consultas_sql(idAccount);

                            if (tbMov.rs.next()) {

                                idNumCount = Integer.parseInt(tbMov.rs.getString(1));

                            }
                            
                            LocalDateTime datetime = LocalDateTime.now();

                            String insertMov = "INSERT INTO movimiento (id_transaccion,id_usuario,id_tipo_movimiento,id_cuenta_bancaria,monto,fecha) "
                                    + " VALUES ('" + idCuenta + "', '" + idUser + "', '" + tipo + "', '" + idNumCount + "', '" + monto + "', '" + datetime + "') ";

                            tbMov.update(insertMov);

                            // Cerrar la ventana una vez realizada la transaccion
                            close(stage);
                            
                        }
                       
                    }
                }

            } catch (SQLException ex) {

                Logger.getLogger(Deposito.class.getName()).log(Level.SEVERE, null, ex);

            }

        }
        );

    }

    private void limpiarCampos(TextField cantidad, DatePicker fecha) {

        cantidad.clear();
        fecha.setValue(null);

    }

    private void close(Stage win) {

        win.close();

    }

}
