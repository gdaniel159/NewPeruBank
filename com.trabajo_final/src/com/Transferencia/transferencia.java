package com.Transferencia;

import com.conexion.bd.conexion;
import com.deposito.Deposito;
import com.trabajo_final.usuarioInterface;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class transferencia extends Stage {

    public void transferenciaWindow(String nombre, String id) {

        //Creacion del fondo
        BorderPane borderPane = new BorderPane();
        Pane panel = new Pane();

        //HeaderTransferencia
        HBox Htransferencia = new HBox();
        Htransferencia.setPrefSize(855, 75);

        //Label nombre-cliente
        Label cliente = new Label(nombre);
        cliente.setFont(Font.font(20));
        cliente.setFont(Font.font("Times New Roman", 25));
        cliente.setMaxWidth(Double.MAX_VALUE);
        cliente.setAlignment(Pos.CENTER);

        // Label - Texto Saldo
        Label txSaldo = new Label("Saldo: S/.");
        txSaldo.setFont(Font.font(20));
        txSaldo.setFont(Font.font("Times New Roman", 23));
        txSaldo.setMaxWidth(Double.MAX_VALUE);
        txSaldo.setAlignment(Pos.CENTER);

        // Label - Saldo
        conexion bd_saldo = new conexion("trabajo_final");
        bd_saldo.conectar();

        String sql_saldo = "SELECT cuenta_bancaria.saldo, clientes.nombres FROM cuenta_bancaria LEFT JOIN clientes ON cuenta_bancaria.id_usuario = clientes.id_usuario;";
        bd_saldo.consultas_sql(sql_saldo);

        Label saldoActual = new Label();

        try {

            while (bd_saldo.rs.next()) {

                if (bd_saldo.rs.getString(2).equals(nombre)) {

                    String num_saldo = bd_saldo.rs.getString(1);
                    saldoActual.setText(num_saldo + " soles");

                }

            }

        } catch (SQLException ex) {

            Logger.getLogger(usuarioInterface.class.getName()).log(Level.SEVERE, null, ex);

        }

        saldoActual.setFont(Font.font(20));
        saldoActual.setFont(Font.font("Times New Roman", 28));
        saldoActual.setMaxWidth(Double.MAX_VALUE);
        saldoActual.setAlignment(Pos.CENTER);

        // nodos a HeaderTransferencia
        Htransferencia.getChildren().addAll(cliente, txSaldo, saldoActual);
        Htransferencia.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        HBox.setMargin(cliente, new Insets(12, 150, 0, 20));
        HBox.setMargin(txSaldo, new Insets(12, 10, 0, 150));
        HBox.setMargin(saldoActual, new Insets(12, 10, 0, 10));
        // Agrega el panel al centro del BorderPane

        borderPane.setCenter(panel);

        // Define los márgenes del panel
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        // Crear etiqueta de título
        Label titulo = new Label("Transferencia");
        titulo.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-underline: true;");
        titulo.setPadding(new Insets(0, 0, 5, 310));

        //Crear un panel para el pie
        Pane pie = new Pane();
        pie.setStyle("-fx-background-color: gray; -fx-opacity: 1.0;");

        // Establecer el tamaño y la posición del pie
        pie.setPadding(new Insets(0, 0, 0, 0));
        borderPane.setBottom(pie);

        //Creacion de Subtitulos
        // Subtitulo
        Label Subtitulo = new Label("Datos de la Cuenta:");
        Subtitulo.setStyle("-fx-font-size: 21px; -fx-font-weight: bold; -fx-underline: true;");
        Subtitulo.setPadding(new Insets(0, 0, 30, 30));

        // Subtitulo2
        Label Subtitulo2 = new Label("Monto a Transferir:");
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

        //Crear cuenta
        Label cuentaLabel = new Label("Número de cuenta:");
        cuentaLabel.setStyle("-fx-font-size: 18px;");
        TextField cuentaField = new TextField();
        cuentaField.setPrefSize(290, 30);

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

        // Creacion de los botones
        //Boton limpiar
        Button limpiarButton = new Button("Limpiar");
        limpiarButton.setOnAction(e -> limpiarCampos(cantidadField, fechaPicker));

        //Boton depositar
        Button transferir = new Button("Transferir");
        
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
        botonera.getChildren().addAll(transferir, cancelarButton);
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
        root.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        root.getChildren().addAll(Htransferencia, titulo, Subtitulo, rootNF, cuentaInput, Subtitulo2, cantidadInput, pie);

        Scene scene = new Scene(root, 855, 550);

        Stage stage = new Stage();

        stage.setScene(scene);

        stage.setTitle("Transferencia");
        stage.show();

        cancelarButton.setOnAction(e -> stage.close());
        
        transferir.setOnAction(e -> {

            try {

                String selectedName = nombreField.getValue().toString(); // Nombre
                String num_cuenta = cuentaField.getText(); // Numero de cuenta
                LocalDateTime fecha = LocalDateTime.now(); // Fecha
                String monto = cantidadField.getText(); // monto

                // Primero hacemos el deposito
                conexion tb_transf = new conexion("trabajo_final");
                tb_transf.conectar();

                // Traemos el monto actual
                String montoActual = "SELECT saldo FROM cuenta_bancaria\n"
                        + "WHERE id_usuario = (SELECT id_usuario FROM clientes WHERE nombres = '" + nombre + "');";

                tb_transf.consultas_sql(montoActual);

                while (tb_transf.rs.next()) {

                    double actualMonto = Double.parseDouble(tb_transf.rs.getString("saldo"));

                    if (Double.parseDouble(monto) > actualMonto) {

                        JOptionPane.showMessageDialog(null, "Monto insuficiente para realizar el deposito");

                    } else {

                        String montoUser = "SELECT saldo FROM cuenta_bancaria\n"
                                + "WHERE id_usuario = (SELECT id_usuario FROM clientes WHERE nombres = '" + selectedName + "');";

                        tb_transf.consultas_sql(montoUser);

                        // Calculamos el nuevo balance
                        if (tb_transf.rs.next()) {
                            
                            double saldoNuevo = 0;
                            double montoDeposito = Double.parseDouble(monto);
                            double nuevoMonto = Double.parseDouble(tb_transf.rs.getString(1)) + montoDeposito;

                            String sql1 = "UPDATE cuenta_bancaria\n"
                                    + "SET saldo = '" + nuevoMonto + "'"
                                    + "WHERE id_usuario = (SELECT id_usuario FROM clientes WHERE nombres = '" + selectedName + "');";

                            tb_transf.update(sql1);
                            
                            String saldo_cliente = "SELECT cuenta_bancaria.saldo, clientes.nombres FROM cuenta_bancaria LEFT JOIN clientes ON cuenta_bancaria.id_usuario = clientes.id_usuario WHERE cuenta_bancaria.id_usuario = '" + id + "';";
                            
                            tb_transf.consultas_sql(saldo_cliente);
                            
                            if (tb_transf.rs.next()){
                                
                                saldoNuevo =  Double.parseDouble(tb_transf.rs.getString(1)) - Double.parseDouble(monto);
                                
                            }

                            if (saldoNuevo > 0) {

                                String updateSaldoUser = "UPDATE cuenta_bancaria SET saldo = " + saldoNuevo + " WHERE id_usuario = " + id;

                                tb_transf.update(updateSaldoUser);

                                JOptionPane.showMessageDialog(null, "Transferencia realizado con exito");

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

                                        } else if (tbMov.rs.getString(1).equals("Deposito")) {

                                            tipo = 1;

                                        } else {

                                            tipo = 3;

                                        }

                                    }

                                    String idAccount = "SELECT cb.id_cuenta_bancaria FROM cuenta_bancaria cb WHERE cb.numero_cuenta = '" + num_cuenta + "'";

                                    tbMov.consultas_sql(idAccount);

                                    if (tbMov.rs.next()) {

                                        idNumCount = Integer.parseInt(tbMov.rs.getString(1));

                                    }

                                    String insertMov = "INSERT INTO movimiento (id_transaccion,id_usuario,id_tipo_movimiento,id_cuenta_bancaria,monto,fecha) "
                                            + " VALUES ('" + idCuenta + "', '" + idUser + "', '" + tipo + "', '" + idNumCount + "', '" + monto + "', '" + fecha + "') ";

                                    tbMov.update(insertMov);

                                    // Hacer que se descuente del monto del usuario que transfiere
                                }

                            } else {

                                JOptionPane.showMessageDialog(null, "Algo salio mal al operar");

                            }

                        }

                    }
                }
                
                close(stage);

            } catch (SQLException ex) {

                Logger.getLogger(Deposito.class.getName()).log(Level.SEVERE, null, ex);

            }

        });
        

    }

    private void limpiarCampos(TextField cantidad, DatePicker fecha) {

        cantidad.clear();
        // Limpiar el DatePicker
        fecha.setValue(null);

    }
    
    public void close(Stage win){
        
        win.close();
        
    }

}
