package com.ironbasetrack.ironbasetrack.controller;

import com.ironbasetrack.ironbasetrack.dao.UsuarioDAO;
import com.ironbasetrack.ironbasetrack.model.Usuario;
import com.ironbasetrack.ironbasetrack.util.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;
    @FXML private javafx.scene.layout.VBox panelRegistro;
    @FXML private javafx.scene.layout.VBox panelLogin; //
    @FXML private javafx.scene.control.TextField txtRegNombre, txtRegApellidos, txtRegEmail;
    @FXML private javafx.scene.control.PasswordField txtRegPassword;
    @FXML private javafx.scene.control.ComboBox<String> cbRegTipoCuenta;

    @FXML
    public void onLoginClick() {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            lblError.setTextFill(Color.RED);
            lblError.setText("Por favor, rellena todos los campos.");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.autenticar(email, password);

        if (usuario != null) {
            lblError.setTextFill(Color.GREEN);
            lblError.setText("Datos correctos, accediendo...");
            Sesion.setUsuarioActual(usuario);
            cargarPantallaPrincipal();
        } else {
            lblError.setTextFill(Color.RED);
            lblError.setText("Datos incorrectos, revíselos.");
        }
    }

    private void cargarPantallaPrincipal() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ironbasetrack/ejercicios-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1000, 650);

            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("IronBase Track - Catálogo");
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            lblError.setTextFill(Color.RED);
            lblError.setText("Error crítico al cargar el módulo principal.");
        }
    }
    @FXML
    public void mostrarPanelRegistro() {
        panelLogin.setVisible(false); panelLogin.setManaged(false);
        panelRegistro.setVisible(true); panelRegistro.setManaged(true);
    }

    @FXML
    public void mostrarPanelLogin() {
        panelRegistro.setVisible(false); panelRegistro.setManaged(false);
        panelLogin.setVisible(true); panelLogin.setManaged(true);
    }

    @FXML
    public void onRegistrarClick() {
        String nombre = txtRegNombre.getText().trim();
        String apellidos = txtRegApellidos.getText().trim();
        String email = txtRegEmail.getText().trim();
        String password = txtRegPassword.getText().trim();
        String tipoCuenta = cbRegTipoCuenta.getValue();

        // 1. Validar que no haya campos vacíos
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || tipoCuenta == null) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, rellena todos los campos obligatorios y elige el tipo de cuenta.");
            alerta.showAndWait();
            return;
        }

        // 2. Asignar el ID del rol correcto según tu base de datos (Atleta Gratis = 3, Atleta Pro = 4)
        int idRol = tipoCuenta.equals("Atleta Pro") ? 4 : 3;

        // 3. Insertar en la Base de Datos
        String sql = "INSERT INTO usuarios (id_rol, nombre, apellidos, email, password_hash) VALUES (?, ?, ?, ?, ?)";

        try (java.sql.Connection con = com.ironbasetrack.ironbasetrack.dao.ConexionDB.conectar();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRol);
            ps.setString(2, nombre);
            ps.setString(3, apellidos);
            ps.setString(4, email);
            ps.setString(5, password);

            ps.executeUpdate();

            // 4. Mostrar éxito, limpiar campos y volver al login
            javafx.scene.control.Alert exito = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            exito.setHeaderText("¡Registro Completado!");
            exito.setContentText("Tu cuenta de " + tipoCuenta + " se ha creado correctamente. Ya puedes iniciar sesión.");
            exito.showAndWait();

            txtRegNombre.clear();
            txtRegApellidos.clear();
            txtRegEmail.clear();
            txtRegPassword.clear();
            cbRegTipoCuenta.getSelectionModel().clearSelection();

            mostrarPanelLogin();

        } catch (java.sql.SQLException e) {
            // Capturar error si el email ya existe en la base de datos (clave UNIQUE)
            if (e.getErrorCode() == 1062) {
                javafx.scene.control.Alert error = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                error.setHeaderText(null);
                error.setContentText("Ya existe una cuenta con ese correo electrónico.");
                error.showAndWait();
            } else {
                System.err.println("Error al registrar usuario: " + e.getMessage());
            }
        }
    }
}
