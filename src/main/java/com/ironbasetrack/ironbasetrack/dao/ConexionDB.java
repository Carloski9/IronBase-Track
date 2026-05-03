package com.ironbasetrack.ironbasetrack.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    // Cambia el puerto 3306 si usas otro distinto
    private static final String URL = "jdbc:mariadb://localhost:3306/ironbasetrack";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {
        try {
            Connection conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("🟢 Éxito: ¡Conectado a la Base de Datos IronBase Track a través de Maven!");
            return conexion;
        } catch (SQLException e) {
            System.err.println("🔴 Error fatal: No se pudo conectar a la base de datos.");
            System.err.println(e.getMessage());
            return null;
        }
    }
}