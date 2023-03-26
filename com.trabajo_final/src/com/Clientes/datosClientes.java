package com.Clientes;

import javafx.beans.property.SimpleStringProperty;

public class datosClientes {

    private final SimpleStringProperty name;
    private final SimpleStringProperty dni;
    private final SimpleStringProperty sexo;
    private final SimpleStringProperty num_cuenta;
    private final SimpleStringProperty saldo;

    public datosClientes(String strName, String strDni, String strSexo, String strNum_cuenta, String strSaldo) {

        this.name = new SimpleStringProperty(strName);
        this.dni = new SimpleStringProperty(strDni);
        this.sexo = new SimpleStringProperty(strSexo);
        this.num_cuenta = new SimpleStringProperty(strNum_cuenta);
        this.saldo = new SimpleStringProperty(strSaldo);

    }

    public String getName() {
        return name.get();
    }

    public String getDni() {
        return dni.get();
    }

    public String getSexo() {
        return sexo.get();
    }

    public String getNum_cuenta() {
        return num_cuenta.get();
    }

    public String getSaldo() {
        return saldo.get();
    }

    public void setName(String strName) {
        name.set(strName);
    }

    public void setDNI(String strDni) {
        dni.set(strDni);
    }

    public void setSexo(String strSexo) {
        sexo.set(strSexo);
    }

    public void setNumeroCuenta(String strNum_cuenta) {
        num_cuenta.set(strNum_cuenta);
    }
    
    public void setSaldo(String strSaldo) {
        saldo.set(strSaldo);
    }
}
