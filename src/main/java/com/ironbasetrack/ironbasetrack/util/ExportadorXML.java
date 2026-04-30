package com.ironbasetrack.ironbasetrack.util;

import com.ironbasetrack.ironbasetrack.dao.ConexionDB;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExportadorXML {

    public static boolean exportarCatalogoEjercicios(String rutaDestino) {
        String sql = "SELECT id_ejercicio, nombre, grupo_muscular, subgrupo_muscular, tipo_mecanica, lateralidad, material FROM ejercicios";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             PrintWriter writer = new PrintWriter(new FileWriter(rutaDestino))) {

            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<catalogo_ejercicios xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"esquema_ejercicios.xsd\">");

            while (rs.next()) {
                writer.println("    <ejercicio id=\"" + rs.getInt("id_ejercicio") + "\">");
                writer.println("        <nombre>" + rs.getString("nombre") + "</nombre>");
                writer.println("        <grupo_muscular>" + rs.getString("grupo_muscular") + "</grupo_muscular>");
                writer.println("        <subgrupo_muscular>" + rs.getString("subgrupo_muscular") + "</subgrupo_muscular>");
                writer.println("        <tipo_mecanica>" + rs.getString("tipo_mecanica") + "</tipo_mecanica>");
                writer.println("        <lateralidad>" + rs.getString("lateralidad") + "</lateralidad>");
                writer.println("        <material>" + rs.getString("material") + "</material>");
                writer.println("    </ejercicio>");
            }

            writer.println("</catalogo_ejercicios>");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
