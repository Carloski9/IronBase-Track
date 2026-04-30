package com.ironbasetrack.ironbasetrack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class IronBaseApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IronBaseApp.class.getResource("/com/ironbasetrack/login-view.fxml"));

        // Creamos la escena y le damos un tamaño de ventana (400x300)
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);

        stage.setTitle("IronBase Track - Catálogo de Ejercicios");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}