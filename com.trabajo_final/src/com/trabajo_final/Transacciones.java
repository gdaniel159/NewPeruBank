package com.trabajo_final;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author AlexC
 */
 public class Transacciones
         
 {
        
        private final SimpleStringProperty name;
        private final SimpleStringProperty movimiento;
        private final SimpleStringProperty monto;
        private final SimpleStringProperty destinatario;
        private final SimpleStringProperty fecha;

        
        public Transacciones(String strNombre, String strMovimiento, String strmonto, String strDestinatario, String strFecha) {
            
            this.name = new SimpleStringProperty(strNombre);
            this.movimiento = new SimpleStringProperty(strMovimiento);
            this.monto = new SimpleStringProperty(strmonto);
            this.destinatario = new SimpleStringProperty(strDestinatario);
            this.fecha = new SimpleStringProperty(strFecha);
        
        }

        public String getName() {
            return name.get();
        }

        public String getMovimiento() {
            return movimiento.get();
        }
        public String getMonto() {
            return monto.get();
        }

        public String getDestinatario() {
            return destinatario.get();
        }
        
        public String getFecha() {
            return fecha.get();
        }
        
 
        public void setNombre(String strNombre) {
           name.set(strNombre);
        }

        public void setMovimiento(String strMovimiento) {
            movimiento.set(strMovimiento);
        }
        
        public void setMonto(String strmonto) {
           monto.set(strmonto);
        }

        public void setDestinatario(String strDestinatario) {
            destinatario.set(strDestinatario);
        }
        
        public void setFecha(String strFecha) {
            fecha.set(strFecha);
        }
        

}
