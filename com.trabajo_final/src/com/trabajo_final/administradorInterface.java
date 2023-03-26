package com.trabajo_final;

import com.Clientes.clientes;
import com.conexion.bd.conexion;
import com.registros.Registro;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class administradorInterface extends Stage {

    public void administradorWindow(String nombre, String dni, String permisos, String sexo) throws FileNotFoundException {

        //SUB - Nodos
        //----------------------------------------------------------------------
        BorderPane Fila_Menubar = new BorderPane();

        MenuBar barra_menu = new MenuBar();

        //File
        Menu Fila_Sesion = new Menu("Sesión");

        //Menú Item
        MenuItem it_close_session = new MenuItem("Cerrar Sesión");

        //Menú items a File
        Fila_Sesion.getItems().addAll(it_close_session);

        // File  Formularios
        Menu Fila_forms = new Menu("Formularios");

        //Menú Item
        MenuItem it_rev_reg = new MenuItem("Revisar Formularios");

        //Acciones
        it_rev_reg.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {

                try {

                    // Registro de solicitudes de usuarios
                    Registro rg = new Registro();
                    rg.registroWindow();

                } catch (Exception ex) {

                    Logger.getLogger(usuarioInterface.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        });

        MenuItem it_rev_client = new MenuItem("Inspeccionar Clientes");

        // Acciones
        it_rev_client.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {

                try {

                    clientes lc = new clientes();
                    lc.ClientesWindow();

                } catch (Exception ex) {

                    Logger.getLogger(usuarioInterface.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        });

        //Menú items a File
        Fila_forms.getItems().addAll(it_rev_reg, it_rev_client);

        //Menus a MenúBar
        barra_menu.getMenus().addAll(Fila_Sesion, Fila_forms);

        //Barra_Menu a Fila_Menubar
        Fila_Menubar.setTop(barra_menu);

        //----------------------------------------------------------------------
        //Fila_bottom
        //----------------------------------------------------------------------
        HBox Fila_bottom = new HBox();
        Fila_bottom.setPrefWidth(1100);
        Fila_bottom.setPrefHeight(620);

        //Nodos Fila_bottom 
        //Colum_Izq
        //----------------------------------------------------------------------
        BorderPane Colum_Izq = new BorderPane();
        Colum_Izq.setPrefWidth(720);
        Colum_Izq.setPrefHeight(620);

        //TableView + ObservableList - Mostrar la informacion en la tabla
        TableView<Transacciones> Flujo_mov = new TableView<>();

        conexion bd_con = new conexion("trabajo_final");
        bd_con.conectar();

        String sql = "SELECT c.nombres, tm.movimiento, m.monto, c2.nombres as nombre_destinatario, m.fecha FROM movimiento m JOIN clientes c ON m.id_usuario = c.id_usuario JOIN tipo_movimiento tm ON m.id_tipo_movimiento = tm.id_tipo_movimiento JOIN cuenta_bancaria cb ON m.id_cuenta_bancaria = cb.id_cuenta_bancaria JOIN clientes c2 ON cb.id_usuario = c2.id_usuario WHERE c.id_estatus IS NOT NULL AND c.dni IS NOT NULL AND c.sexo IS NOT NULL AND c.contraseña IS NOT NULL;";

        bd_con.consultas_sql(sql);

        try {

            // Creacion de la lista que almacenara nuestros elementos de la BD
            ObservableList<Transacciones> listaMovimientos = FXCollections.observableArrayList();

            while (bd_con.rs.next()) {

                // Iteramos dentro de la BD y traemos los datos de cada columna
                String nombres = bd_con.rs.getString(1);
                String id_tipo_movimiento = bd_con.rs.getString(2);
                String monto = bd_con.rs.getString(3);

                String destinatario = "";

                if (bd_con.rs.getString(2).equals("Retiro") && bd_con.rs.getString(4).equals(nombres)) {

                    destinatario = "Efectivo";

                } else if (bd_con.rs.getString(2).equals("Deposito") && bd_con.rs.getString(4).equals(nombres)) {

                    destinatario = bd_con.rs.getString("nombre_destinatario");

                }

                String fecha = bd_con.rs.getString(5);

                // Añadimos los elementos creando la instancia de Transacciones, la cual nos crea un nuevo objeto por cada elemento
                listaMovimientos.add(new Transacciones(nombres, id_tipo_movimiento, monto, destinatario, fecha));

                // Por ultimo lo añadimos al TableView, dandole una respectiva dimension
                Flujo_mov.setItems(listaMovimientos);
                Flujo_mov.setMaxSize(650, 600);
                Flujo_mov.setMinSize(250, 550);
            }

        } catch (SQLException ex) {

            Logger.getLogger(administradorInterface.class.getName()).log(Level.SEVERE, null, ex);

        }

        //Creamos La columna Principal TableColumn
        TableColumn colPrincipal = new TableColumn("Flujo De Movimientos");

        // Creación de Columnas //
        //Columna Clientes
        TableColumn<Transacciones, String> colNombre = new TableColumn<>("Cuenta Cliente");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNombre.setMinWidth(Flujo_mov.getMaxWidth() / 5);

        //Columna Movimientos
        TableColumn<Transacciones, String> colMovimiento = new TableColumn<>("Movimiento");
        colMovimiento.setCellValueFactory(new PropertyValueFactory<>("movimiento"));
        colMovimiento.setMinWidth(Flujo_mov.getMaxWidth() / 5);

        //Columna Monto
        TableColumn<Transacciones, String> colMonto = new TableColumn<>("Monto");
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        colMonto.setMinWidth(Flujo_mov.getMaxWidth() / 5);

        //Columna Destinatario
        TableColumn<Transacciones, String> colFin = new TableColumn<>("Destinatario");
        colFin.setCellValueFactory(new PropertyValueFactory<>("destinatario"));
        colFin.setMinWidth(Flujo_mov.getMaxWidth() / 5);

        //Columna Fecha
        TableColumn<Transacciones, String> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colFecha.setMinWidth(Flujo_mov.getMaxWidth() / 5);

        // Asigamos las columnas al TableView
        colPrincipal.getColumns().addAll(colNombre, colMovimiento, colMonto, colFin, colFecha);
        Flujo_mov.getColumns().addAll(colPrincipal);

        //Layouts
        GridPane center = new GridPane();
        center.setHgap(10);
        center.setVgap(10);
        center.setPadding(new Insets(10, 30, 20, 30));
        center.add(Flujo_mov, 0, 1, 5, 5);
        Colum_Izq.setCenter(center);
        Colum_Izq.setBackground(new Background(new BackgroundFill(Color.web("#CDCDCD"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Nodos Colum_Izq //
        //----------------------------------------------------------------------
        //                             Colum_Der                              //
        //----------------------------------------------------------------------
        VBox Colum_Der = new VBox();
        Colum_Der.setMaxWidth(500);
        Colum_Der.setMaxHeight(570);

        //----------------------------------------------------------------------
        //                          Nodos ColumDer                            //
        //----------------------------------------------------------------------
        //----------------------------------------------------------------------
        //                              Log_Emp                               // 
        //----------------------------------------------------------------------
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

        //---------------------------------------------------------------------- 
        //                            Info_Ad                                 //
        //----------------------------------------------------------------------
        VBox Info_Ad = new VBox();

        // Nodos Info_Ad
        // Separador
        ImageView raya;
        FileInputStream arch = new FileInputStream("E:\\Exposicion\\Java_Trabajo_Final\\com.trabajo_final\\src\\com\\media\\img\\Linea_negra.png");
        Image LineH = new Image(arch);
        raya = new ImageView(LineH);
        VBox.setMargin(raya, new Insets(0, 0, 15, -2));
        raya.setFitWidth(380);

        //Usuario_img por defecto.
        ImageView user_unkn;
        FileInputStream user = new FileInputStream("E:\\Exposicion\\Java_Trabajo_Final\\com.trabajo_final\\src\\com\\media\\img\\usr_unk.png");
        Image UsuarioA = new Image(user);
        user_unkn = new ImageView(UsuarioA);
        VBox.setMargin(user_unkn, new Insets(0, 130, 10, 130));
        user_unkn.setFitWidth(120);
        user_unkn.setFitHeight(120);

        //Nombre Administrador
        Label nom_admin = new Label(nombre);
        nom_admin.setMaxWidth(Double.MAX_VALUE);
        nom_admin.setAlignment(Pos.CENTER);
        nom_admin.setFont(Font.font(20));
        Colum_Der.setMargin(nom_admin, new Insets(0, 0, 20, 0));

        //DNI (Hbox)
        HBox inf_dni = new HBox();

        //Dentro del Hbox
        Label DNI = new Label("DNI: ");
        DNI.setFont(Font.font("Times New Roman", 18));
        Label num_dni = new Label(dni);
        HBox.setMargin(DNI, new Insets(0, 4, 20, 40));
        num_dni.setFont(Font.font("Times New Roman", 18));
        inf_dni.getChildren().addAll(DNI, num_dni);

        //Información extra   
        HBox inf_sexo = new HBox();

        //dentro del Hbox
        Label genero = new Label("SEXO: ");
        genero.setFont(Font.font("Times New Roman", 18));
        HBox.setMargin(genero, new Insets(0, 4, 20, 40));

        Label genero_adm = new Label();

        if (sexo.equals("M")) {

            sexo = "Masculino";
            genero_adm.setText(sexo);

        } else if (sexo.equals("F")) {

            sexo = "Femenino";
            genero_adm.setText(sexo);

        }

        genero_adm.setFont(Font.font("Times New Roman", 18));
        inf_sexo.getChildren().addAll(genero, genero_adm);

        HBox inf_permisos = new HBox();

        // Dentro de Hbox
        Label Perm_acc = new Label();
        if (permisos.equals("1")) {
            permisos = "Administrador";
            Perm_acc.setText("Tipo de cuenta: " + permisos);
        }

        Perm_acc.setFont(Font.font("Times New Roman", 18));
        HBox.setMargin(Perm_acc, new Insets(0, 4, 45, 40));

        inf_permisos.getChildren().add(Perm_acc);

        //LABELS ADMINISTRACIÒN-BANCARIA 
        Label ADMINISTRACION = new Label("ADMINISTRACIÓN");
        ADMINISTRACION.setFont(Font.font("Times New Roman", 28));
        ADMINISTRACION.setMaxWidth(Double.MAX_VALUE);
        ADMINISTRACION.setAlignment(Pos.CENTER);

        Label BANCARIA = new Label("BANCARIA");
        BANCARIA.setFont(Font.font("Times New Roman", 26));
        BANCARIA.setMaxWidth(Double.MAX_VALUE);
        BANCARIA.setAlignment(Pos.CENTER);
        Colum_Der.setMargin(BANCARIA, new Insets(0, 0, 20, 0));

        //Empress_name
        Label name_final = new Label("New  Perú  Bank ");
        name_final.setFont(Font.font("Times New Roman", 20));
        name_final.setMaxWidth(Double.MAX_VALUE);
        name_final.setAlignment(Pos.CENTER_RIGHT);
        Colum_Der.setMargin(name_final, new Insets(0, 0, 50, 0));

        //Copyright
        Label copy = new Label("Interfaz de Admin.©Derechos reservados por NEW PERÚ BANK ");
        copy.setFont(Font.font("Times New Roman", 12));
        copy.setMaxWidth(Double.MAX_VALUE);
        copy.setAlignment(Pos.CENTER);

        Colum_Der.getChildren().addAll(Log_Emp, Info_Ad, raya, user_unkn, nom_admin, inf_dni, inf_sexo, inf_permisos, ADMINISTRACION, BANCARIA, name_final, copy);

        //----------------------------------------------------------------------
        //                        Conexión Fila_bottom                        //
        //----------------------------------------------------------------------
        Fila_bottom.getChildren().addAll(Colum_Izq, Colum_Der);

        //----------------------------------------------------------------------
        //                          Nodo Raiz(root)                           //
        //----------------------------------------------------------------------
        VBox raiz = new VBox();

        //conexción con la raiz
        raiz.getChildren().addAll(Fila_Menubar, Fila_bottom);

        // Escenario Principal
        Scene Escena_ = new Scene(raiz, 1100, 620);

        Stage administradores = new Stage();

        administradores.setTitle("Administración NewPerúBank");
        administradores.setScene(Escena_);
        administradores.initModality(Modality.APPLICATION_MODAL);
        administradores.show();

        it_close_session.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {

                administradores.close();

            }

        });

    }

}
