package com.Retiro;

import com.conexion.bd.conexion;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class retiro_funcionalidad {

    public void funcionalidad(Button element, int cantidad, Stage stage, String id) {

        element.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {

                    conexion bd_cuenta = new conexion("trabajo_final");
                    bd_cuenta.conectar();
                    String sql1 = "SELECT clientes.nombres, cuenta_bancaria.numero_cuenta, cuenta_bancaria.saldo FROM clientes LEFT JOIN cuenta_bancaria ON cuenta_bancaria.id_usuario = clientes.id_usuario;";
                    bd_cuenta.consultas_sql(sql1);

                    while (bd_cuenta.rs.next()) {

                        double saldo_actual = bd_cuenta.rs.getDouble("saldo");

                        if (saldo_actual >= cantidad) {

                            double nuevo_saldo = saldo_actual - cantidad;

                            String sql2 = "UPDATE cuenta_bancaria SET saldo = " + nuevo_saldo + " WHERE numero_cuenta = " + bd_cuenta.rs.getString(2);

                            bd_cuenta.update(sql2);

                            JOptionPane.showMessageDialog(null, "Retiro realizado con exito");

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

                                    if (tbMov.rs.getString(1).equals("Deposito")) {

                                        tipo = 1;

                                    } else {

                                        tipo = 2;

                                    }

                                }

                                String cuenta = "SELECT numero_cuenta FROM cuenta_bancaria WHERE id_usuario = '" + id + "'";

                                tbMov.consultas_sql(cuenta);

                                String num_cuenta = "";

                                if (tbMov.rs.next()) {

                                    num_cuenta = tbMov.rs.getString(1);

                                }

                                String idAccount = "SELECT cb.id_cuenta_bancaria FROM cuenta_bancaria cb WHERE cb.numero_cuenta = '" + num_cuenta + "'";

                                tbMov.consultas_sql(idAccount);

                                if (tbMov.rs.next()) {

                                    idNumCount = Integer.parseInt(tbMov.rs.getString(1));

                                }

                                LocalDateTime datetime = LocalDateTime.now();

                                String insertMov = "INSERT INTO movimiento (id_transaccion,id_usuario,id_tipo_movimiento,id_cuenta_bancaria,monto,fecha) "
                                        + " VALUES ('" + idCuenta + "', '" + idUser + "', '" + tipo + "', '" + idNumCount + "', '" + cantidad + "', '" + datetime + "') ";

                                tbMov.update(insertMov);

                            }
                            
                            break;
                            
                        } else {

                            JOptionPane.showMessageDialog(null, "Saldo Insuficiente");
                            break;

                        }

                    }

                    close(stage);

                } catch (SQLException ex) {

                    Logger.getLogger(Retiro.class.getName()).log(Level.SEVERE, null, ex);

                }

            }

        });

    }

    public void close(Stage win) {

        win.close();

    }

}
