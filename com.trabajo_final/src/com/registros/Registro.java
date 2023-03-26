package com.registros;

import com.conexion.bd.conexion;
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
import javafx.scene.input.MouseEvent;
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

public class Registro extends Stage {

    public void registroWindow() {

        HBox presentacion = new HBox();
        presentacion.setPrefSize(700, 50);
        Label Label_1 = new Label("PETICIONES DE REGISTRO");
        Label_1.setFont(Font.font("Times New Roman", 20));

        //label a presentacion
        presentacion.getChildren().add(Label_1);

        //centrar HBox
        presentacion.setMaxWidth(Double.MAX_VALUE);
        presentacion.setAlignment(Pos.CENTER);
        BorderPane table = new BorderPane();
        table.setPrefSize(700, 450);

        //TableView + ObservableList - Mostrar la informacion en la tabla
        TableView<datos_registro> Flujo_registros = new TableView<>();
        Flujo_registros.setMaxSize(650, 600);
        Flujo_registros.setMinSize(250, 550);

        //Creamos La columna Principal TableColumn
        TableColumn colPrincipal = new TableColumn("Registros NO Revisados");

        // Creación de Columnas //
        //Columna Clientes
        TableColumn<datos_registro, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNombre.setMinWidth(Flujo_registros.getMaxWidth() / 4);

        //Columna Movimientos
        TableColumn<datos_registro, String> colDni = new TableColumn<>("Dni");
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colDni.setMinWidth(Flujo_registros.getMaxWidth() / 4);

        //Columna Monto
        TableColumn<datos_registro, String> colSexo = new TableColumn<>("Sexo");
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        colSexo.setMinWidth(Flujo_registros.getMaxWidth() / 4);

        //Columna Destinatario
        TableColumn<datos_registro, String> colPassw = new TableColumn<>("Contraseña");
        colPassw.setCellValueFactory(new PropertyValueFactory<>("contraseña"));
        colPassw.setMinWidth(Flujo_registros.getMaxWidth() / 4);

        // Obtener los datos de la tabla
        conexion bd_con = new conexion("trabajo_final");
        bd_con.conectar();

        String sql = "SELECT nombres,dni,sexo,contraseña,id_usuario FROM clientes WHERE id_usuario NOT IN (SELECT id_usuario FROM cuenta_bancaria)";
        bd_con.consultas_sql(sql);

        ObservableList<datos_registro> registro = FXCollections.observableArrayList();

        try {
            
            // Iterar a través de los resultados y agregar cada registro a la lista ObservableList
            
            while (bd_con.rs.next()) {
                
                String name = bd_con.rs.getString("nombres");
                String dni = bd_con.rs.getString("dni");
                String sexo = bd_con.rs.getString("sexo");
                String pass = bd_con.rs.getString("contraseña");

                registro.add(new datos_registro(name, dni, sexo, pass));
            }
            
        } catch (SQLException ex) {
            
            Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
            
        }

        // Mostrar la lista ObservableList en la tabla
        
        Flujo_registros.setItems(registro);

        // Asigamos las columnas al TableView
        colPrincipal.getColumns().addAll(colNombre, colDni, colSexo, colPassw);
        Flujo_registros.getColumns().addAll(colPrincipal);

        // TableView a BorderPane
        GridPane center = new GridPane();
        center.setHgap(10);
        center.setVgap(10);
        center.setPadding(new Insets(10, 30, 20, 30));
        center.add(Flujo_registros, 0, 1, 4, 4);
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
        stage.setTitle("Flujo de Registros");
        stage.show();

        Flujo_registros.setOnMousePressed((MouseEvent event) -> {

            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {

                try {
                    
                    ObservableList<datos_registro> selectedItems = Flujo_registros.getSelectionModel().getSelectedItems();
                    
                    if (selectedItems.size() == 1){
                        
                        int indice = Flujo_registros.getSelectionModel().getSelectedIndex();
                        
                        conexion bd_form = new conexion("trabajo_final");
                    
                        bd_form.conectar();

                        String sql1 = "SELECT nombres,dni,sexo,contraseña,id_usuario FROM clientes WHERE id_usuario NOT IN (SELECT id_usuario FROM cuenta_bancaria)";

                        bd_form.consultas_sql(sql1);
                        
                        while (bd_form.rs.next()) {

                            Formulario form = new Formulario();

                            String name = bd_form.rs.getString("nombres");
                            String dni = bd_form.rs.getString("dni");
                            String sexo = bd_form.rs.getString("sexo");
                            String pass = bd_form.rs.getString("contraseña");

                            form.FormularioWindow(name,dni,sexo,pass,(indice + 1),registro);

                        }
                        
                    }
                    
                } catch (SQLException ex) {
                    
                    Logger.getLogger(Registro.class.getName()).log(Level.SEVERE, null, ex);
                
                }
                
            }
        });
    }
}
