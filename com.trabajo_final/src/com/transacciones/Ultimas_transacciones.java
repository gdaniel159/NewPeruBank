package com.transacciones;

import com.conexion.bd.conexion;
import com.trabajo_final.administradorInterface;
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

public class Ultimas_transacciones extends Stage {

    public void UltTransationsWindow(String nombre) {

        HBox presentacion = new HBox();
        presentacion.setPrefSize(700, 50);
        Label Label_1 = new Label("CLIENTE");
        Label_1.setFont(Font.font("Times New Roman", 20));

        Label Label_nombre = new Label(nombre);
        Label_nombre.setFont(Font.font("Times New Roman", 20));

        //label a presentacion
        presentacion.getChildren().addAll(Label_1, Label_nombre);

        //centrar HBox
        presentacion.setMaxWidth(Double.MAX_VALUE);
        presentacion.setAlignment(Pos.CENTER);
        HBox.setMargin(Label_nombre, new Insets(0, 0, 0, 10));
        BorderPane table = new BorderPane();
        table.setPrefSize(700, 450);
        //TableView + ObservableList - Mostrar la informacion en la tabla

        TableView<datos_ultimasT> Flujo_Ult_transac = new TableView<>();

        conexion bd_con = new conexion("trabajo_final");
        bd_con.conectar();

        String sql = "SELECT c.nombres, tm.movimiento, m.monto, c2.nombres as nombre_destinatario, m.fecha FROM movimiento m JOIN clientes c ON m.id_usuario = c.id_usuario JOIN tipo_movimiento tm ON m.id_tipo_movimiento = tm.id_tipo_movimiento JOIN cuenta_bancaria cb ON m.id_cuenta_bancaria = cb.id_cuenta_bancaria JOIN clientes c2 ON cb.id_usuario = c2.id_usuario WHERE c.id_estatus IS NOT NULL AND c.dni IS NOT NULL AND c.sexo IS NOT NULL AND c.contraseña IS NOT NULL;";

        bd_con.consultas_sql(sql);

        try {

            // Creacion de la lista que almacenara nuestros elementos de la BD
            ObservableList<datos_ultimasT> lista_ult_Movimientos = FXCollections.observableArrayList();

            while (bd_con.rs.next()) {

                // Iteramos dentro de la BD y traemos los datos de cada columna
                String nombres = bd_con.rs.getString(1);
                
                if (nombres.equals(nombre)) {
                    
                    String id_tipo_movimiento = bd_con.rs.getString(2);
                    String monto = bd_con.rs.getString(3);

                    String destinatario = "";

                    if (bd_con.rs.getString(2).equals("Retiro") && bd_con.rs.getString(4).equals(nombres)) {

                        destinatario = "Efectivo";

                    } else if (bd_con.rs.getString(2).equals("Deposito") && bd_con.rs.getString(4).equals(nombres)) {

                        destinatario = bd_con.rs.getString(4);

                    } else {
                        
                        destinatario = bd_con.rs.getString(4);
                        
                    }

                    String fecha = bd_con.rs.getString(5);

                    // Añadimos los elementos creando la instancia de Transacciones, la cual nos crea un nuevo objeto por cada elemento
                    lista_ult_Movimientos.add(new datos_ultimasT(nombres, id_tipo_movimiento, monto, destinatario, fecha));

                    // Por ultimo lo añadimos al TableView, dandole una respectiva dimension
                    Flujo_Ult_transac.setItems(lista_ult_Movimientos);
                    Flujo_Ult_transac.setMaxSize(650, 600);
                    Flujo_Ult_transac.setMinSize(250, 550);
                    
                }
                

            }

        } catch (SQLException ex) {

            Logger.getLogger(administradorInterface.class.getName()).log(Level.SEVERE, null, ex);

        }

        //Creamos La columna Principal TableColumn
        TableColumn colPrincipal = new TableColumn("ULTIMAS TRANSACCIONES");

        // Creación de Columnas //
        //Columna Clientes
        TableColumn<datos_ultimasT, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNombre.setMinWidth(Flujo_Ult_transac.getMaxWidth() / 5);

        //Columna Movimientos
        TableColumn<datos_ultimasT, String> colMovimiento = new TableColumn<>("Movimiento");
        colMovimiento.setCellValueFactory(new PropertyValueFactory<>("movimiento"));
        colMovimiento.setMinWidth(Flujo_Ult_transac.getMaxWidth() / 5);

        //Columna Monto
        TableColumn<datos_ultimasT, String> colMonto = new TableColumn<>("Monto");
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        colMonto.setMinWidth(Flujo_Ult_transac.getMaxWidth() / 5);

        //Columna Destinatario
        TableColumn<datos_ultimasT, String> colDestinatario = new TableColumn<>("Destinatario");
        colDestinatario.setCellValueFactory(new PropertyValueFactory<>("destinatario"));
        colDestinatario.setMinWidth(Flujo_Ult_transac.getMaxWidth() / 5);

        //
        TableColumn<datos_ultimasT, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colFecha.setMinWidth(Flujo_Ult_transac.getMaxWidth() / 5);

        // Asigamos las columnas al TableView
        colPrincipal.getColumns().addAll(colNombre, colMovimiento, colMonto, colDestinatario, colFecha);
        Flujo_Ult_transac.getColumns().addAll(colPrincipal);

        // TableView a BorderPane
        GridPane center = new GridPane();
        center.setHgap(10);
        center.setVgap(10);
        center.setPadding(new Insets(10, 30, 20, 30));
        center.add(Flujo_Ult_transac, 0, 1, 5, 5);
        center.setAlignment(Pos.CENTER);
        table.setCenter(center);
        table.setBackground(new Background(new BackgroundFill(Color.web("#d3d3d3"), CornerRadii.EMPTY, Insets.EMPTY)));

        VBox root = new VBox();
        root.getChildren().addAll(presentacion, table);
        Scene scena = new Scene(root, 700, 500);
        Stage stage = new Stage();
        stage.setMaxWidth(705);
        stage.setMaxHeight(505);
        stage.setScene(scena);
        stage.setTitle("Ultimas Transacciones");
        stage.show();

    }

}
