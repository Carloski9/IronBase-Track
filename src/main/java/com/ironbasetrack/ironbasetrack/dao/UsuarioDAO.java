package com.ironbasetrack.ironbasetrack.dao;

import com.ironbasetrack.ironbasetrack.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Method que devuelve un Usuario si las credenciales son correctas, o null si falla
    public Usuario autenticar(String email, String password) {
        // Cuidado: comprueba que tus columnas se llaman exactamente así en DBeaver
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password_hash = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // ¡Login correcto! Construimos y devolvemos el objeto Usuario
                    return new Usuario(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getInt("id_rol")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en la autenticación: " + e.getMessage());
        }

        // Si llega aquí, es que el usuario no existe o la contraseña está mal
        return null;
    }
}