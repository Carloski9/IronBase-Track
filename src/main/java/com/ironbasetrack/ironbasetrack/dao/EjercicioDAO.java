package com.ironbasetrack.ironbasetrack.dao;

import com.ironbasetrack.ironbasetrack.model.Ejercicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EjercicioDAO {


    public List<Ejercicio> obtenerTodosLosEjercicios() {
        List<Ejercicio> listaEjercicios = new ArrayList<>();
        String sql = "SELECT * FROM ejercicios";

        // El try-with-resources cierra la conexión automáticamente al terminar
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ejercicio ej = new Ejercicio(
                        rs.getInt("id_ejercicio"),
                        rs.getString("nombre"),
                        rs.getString("grupo_muscular"),
                        rs.getString("subgrupo_muscular"),
                        rs.getString("tipo_mecanica"),
                        rs.getString("lateralidad"),
                        rs.getString("material")
                );

                listaEjercicios.add(ej);
            }

        } catch (SQLException e) {
            System.err.println("🔴 Error al obtener los ejercicios: " + e.getMessage());
        }

        return listaEjercicios;
    }

    public boolean insertarEjercicio(Ejercicio ej) {
        // Usamos ? para evitar Inyección SQL (¡Buenas prácticas!)
        String sql = "INSERT INTO ejercicios (nombre, grupo_muscular, subgrupo_muscular, tipo_mecanica, lateralidad, material, url_video_demostracion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, ej.getNombre());
            ps.setString(2, ej.getGrupoMuscular());
            ps.setString(3, ej.getSubgrupoMuscular());
            ps.setString(4, ej.getTipoMecanica());
            ps.setString(5, ej.getLateralidad());
            ps.setString(6, ej.getMaterial());
            ps.setString(7, ej.getUrlVideoDemostracion());

            // executeUpdate() devuelve el número de filas afectadas. Si es > 0, se guardó bien.
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar el ejercicio: " + e.getMessage());
            return false;
        }
    }
    public boolean borrarEjercicio(int idEjercicio) {
        String sql = "DELETE FROM ejercicios WHERE id_ejercicio = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEjercicio);
            return ps.executeUpdate() > 0; // Devuelve true si borró algo

        } catch (SQLException e) {
            System.err.println("Error al borrar el ejercicio: " + e.getMessage());
            return false;
        }
    }
}