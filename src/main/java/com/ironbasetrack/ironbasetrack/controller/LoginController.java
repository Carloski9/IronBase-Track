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

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblError;

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
}
