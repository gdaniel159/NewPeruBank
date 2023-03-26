package com.registros;

import javafx.beans.property.SimpleStringProperty;

public class datos_registro {

    private final SimpleStringProperty name;
    private final SimpleStringProperty dni;
    private final SimpleStringProperty sexo;
    private final SimpleStringProperty contraseña;
    

    public datos_registro(String strName, String strDni, String strSexo, String strContraseña) {

        this.name = new SimpleStringProperty(strName);
        this.dni = new SimpleStringProperty(strDni);
        this.sexo = new SimpleStringProperty(strSexo);
        this.contraseña = new SimpleStringProperty(strContraseña);

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

    public String getContraseña() {

        return contraseña.get();

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

    public void setDestinatario(String strContraseña) {

        contraseña.set(strContraseña);

    }

}
