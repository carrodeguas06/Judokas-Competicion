package com.liceolapaz.bcd.judokascompeticion.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
     private static final String USER = "root";
     private static final String PASSWORD = "";
     private static final String HOST = "jdbc:mysql://localhost/judo_proyect_bd";
     private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
     private static Connection connection = null;
     private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed())
        {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(HOST, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                System.out.println("Error al conectar con la base de datos");
            }
        }
        return connection;
    }
}
