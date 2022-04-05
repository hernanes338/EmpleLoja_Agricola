package com.aureliohernandez.EmpleLoja_Agricola;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://192.168.1.130:3306/empleloja_agricola";
    String username = "admin";
    String password = "foc2022";

    public void connection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Conexion realizada: " + connection.getMetaData());
        connection.close();
        System.out.println("Conexion finalizada: " + connection.getMetaData());
    }
}
