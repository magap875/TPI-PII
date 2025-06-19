package com.mycompany.sistemadegestionpadeltpi;

import com.mycompany.sistemadegestionpadeltpi.Controlador.ControladorGeneral;
import java.sql.Connection;
import java.sql.DriverManager;

public class SistemaDeGestionPadelTPI {

    public static void main(String[] args) {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdpadel", "root", "frodo123");

            ControladorGeneral controlador = new ControladorGeneral(con);
            controlador.ejecutarMenuGeneral();

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
