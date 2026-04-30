package com.ironbasetrack.ironbasetrack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // 👇 AQUÍ ESTÁ LA LÍNEA MÁGICA CON LA BARRA "/" 👇
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ejercicios-view.fxml"));

        // Creamos la escena y le damos un tamaño de ventana (700x500)
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);

        stage.setTitle("IronBase Track - Catálogo de Ejercicios");
        stage.setScene(scene);
        stage.show(); // Esto es lo que hace que la ventana se vuelva visible
    }

    public static void main(String[] args) {
        launch();
    }
}