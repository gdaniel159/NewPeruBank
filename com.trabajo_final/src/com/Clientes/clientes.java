package com.Clientes;

import com.conexion.bd.conexion;
import com.trabajo_final.administradorInterface;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class clientes {

    public void ClientesWindow() throws FileNotFoundException {
        HBox Log_Emp = new HBox();

        //Nodos de Log_Emp
        //Nombre Empresa
        Label nombre_empresa_g = new Label("NEW PERÚ BANK");
        nombre_empresa_g.setFont(Font.font("Times New Roman", 31));
        nombre_empresa_g.setPadding(new Insets(15, 20, 0, 15));

        //Logo Empresa
        ImageView log_empresa_Ad;
        FileInputStream file = new FileInputStream("E:\\Exposicion\\Java_Trabajo_Final\\com.trabajo_final\\src\\com\\media\\img\\logo.png");
        Image image = new Image(file);
        log_empresa_Ad = new ImageView(image);
        HBox.setMargin(log_empresa_Ad, new Insets(15, 15, 0, 15));

        //Conexión de nodos a Log_Emp
        Log_Emp.getChildren().addAll(nombre_empresa_g, log_empresa_Ad);
        Log_Emp.setAlignment(Pos.CENTER);
        //border Pane
        BorderPane table = new BorderPane();
        //TableView + ObservableList - Mostrar la informacion en la tabla

        TableView<datosClientes> listado_clientes = new TableView<>();

        conexion bd_con = new conexion("trabajo_final");
        bd_con.conectar();

        String sql = "SELECT clientes.nombres, clientes.dni, clientes.sexo, cuenta_bancaria.numero_cuenta,cuenta_bancaria.saldo FROM clientes LEFT JOIN cuenta_bancaria ON cuenta_bancaria.id_usuario = clientes.id_usuario;";

        bd_con.consultas_sql(sql);

        try {

            // Creacion de la lista que almacenara nuestros elementos de la BD
            ObservableList<datosClientes> lista_clientes = FXCollections.observableArrayList();

            while (bd_con.rs.next()) {

                // Iteramos dentro de la BD y traemos los datos de cada columna
                String nombres = bd_con.rs.getString(1);
                String dni = bd_con.rs.getString(2);
                String sexo = bd_con.rs.getString(3);
                String num_cuenta = bd_con.rs.getString(4);
                String saldo = bd_con.rs.getString(5);

                // Añadimos los elementos creando la instancia de Transacciones, la cual nos crea un nuevo objeto por cada elemento
                lista_clientes.add(new datosClientes(nombres, dni,sexo, num_cuenta, saldo));

                // Por ultimo lo añadimos al TableView, dandole una respectiva dimension
                listado_clientes.setItems(lista_clientes);
                listado_clientes.setMaxSize(650, 550);
                listado_clientes.setMinSize(250, 500);
            }

        } catch (SQLException ex) {

            Logger.getLogger(administradorInterface.class.getName()).log(Level.SEVERE, null, ex);

        }

        //Creamos La columna Principal TableColumn
        TableColumn colPrincipal = new TableColumn("LISTADO DE CLIENTES");

        // Creación de Columnas //
        //Columna Clientes
        TableColumn<datosClientes, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNombre.setMinWidth(listado_clientes.getMaxWidth() / 5);

        //Columna Movimientos
        TableColumn<datosClientes, String> coldni = new TableColumn<>("DNI");
        coldni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        coldni.setMinWidth(listado_clientes.getMaxWidth() / 5);

        //Columna Monto
        TableColumn<datosClientes, String> colSexo = new TableColumn<>("Sexo");
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        colSexo.setMinWidth(listado_clientes.getMaxWidth() / 5);

        //Columna Destinatario
        TableColumn<datosClientes, String> colnum_cuenta = new TableColumn<>("Número de Cuenta");
        colnum_cuenta.setCellValueFactory(new PropertyValueFactory<>("num_cuenta"));
        colnum_cuenta.setMinWidth(listado_clientes.getMaxWidth() / 5);

        //
        TableColumn<datosClientes, String> colSaldo = new TableColumn<>("Saldo");
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        colSaldo.setMinWidth(listado_clientes.getMaxWidth() / 5);

        // Asigamos las columnas al TableView
        colPrincipal.getColumns().addAll(colNombre, coldni, colSexo, colnum_cuenta, colSaldo);
        listado_clientes.getColumns().addAll(colPrincipal);

        // TableView a BorderPane
        GridPane center = new GridPane();
        center.setHgap(10);
        center.setVgap(10);
        center.setPadding(new Insets(10, 30, 30, 30));
        center.add(listado_clientes, 0, 1, 5, 5);
        center.setAlignment(Pos.CENTER);
        table.setCenter(center);
        table.setBackground(new Background(new BackgroundFill(Color.web("#d3d3d3"), CornerRadii.EMPTY, Insets.EMPTY)));

        VBox root = new VBox();
        root.getChildren().addAll(Log_Emp, table);
        Scene scena = new Scene(root, 800, 600);
        Stage stage = new Stage();
        stage.setMaxWidth(850);
        stage.setMaxHeight(650);
        stage.setScene(scena);
        stage.setTitle("Clientes");
        stage.show();

    }

}
