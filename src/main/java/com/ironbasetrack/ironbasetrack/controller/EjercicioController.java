package com.ironbasetrack.ironbasetrack.controller;

import com.ironbasetrack.ironbasetrack.dao.EjercicioDAO;
import com.ironbasetrack.ironbasetrack.model.Ejercicio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EjercicioController {

    @FXML private TableView<Ejercicio> tablaEjercicios;
    @FXML private TableColumn<Ejercicio, String> colNombre;
    @FXML private TableColumn<Ejercicio, String> colGrupo;
    @FXML private TableColumn<Ejercicio, String> colMaterial;
    @FXML private TextField txtNombre;
    @FXML private TextField txtGrupoMuscular;
    @FXML private TextField txtSubgrupoMuscular;
    @FXML private ComboBox<String> cbMecanica;
    @FXML private ComboBox<String> cbLateralidad;
    @FXML private ComboBox<String> cbMaterial;

    @FXML
    public void initialize() {
        // 1. Configuración de la tabla
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("grupoMuscular"));
        colMaterial.setCellValueFactory(new PropertyValueFactory<>("material"));

        EjercicioDAO dao = new EjercicioDAO();
        ObservableList<Ejercicio> listaVisual = FXCollections.observableArrayList(dao.obtenerTodosLosEjercicios());
        tablaEjercicios.setItems(listaVisual);

        // 2. Llenamos los ComboBox con los ENUM de MariaDB
        cbMecanica.getItems().addAll("Multiarticular", "Monoarticular");
        cbLateralidad.getItems().addAll("Bilateral", "Unilateral");
        cbMaterial.getItems().addAll("Libre_Barra", "Libre_Mancuernas", "Polea", "Maquina_Discos", "Maquina_Placas", "Peso_Corporal");
    }

    // 3. El method que guarda el ejercicio al pulsar el botón
    @FXML
    public void onGuardarClick() {
        // Recogemos los datos del formulario
        String nombre = txtNombre.getText();
        String grupo = txtGrupoMuscular.getText();
        String subgrupo = txtSubgrupoMuscular.getText();
        String mecanica = cbMecanica.getValue();
        String lateralidad = cbLateralidad.getValue();
        String material = cbMaterial.getValue();

        // Evitamos que guarden si faltan campos clave o no han elegido nada en los desplegables
        if (nombre.isEmpty() || mecanica == null || lateralidad == null || material == null) {
            System.out.println("Por favor, rellena todos los campos clave.");
            return;
        }

        // Creamos el objeto (ajustado a los 7 parámetros de tu clase Ejercicio)
        Ejercicio nuevoEjercicio = new Ejercicio(0, nombre, grupo, subgrupo, mecanica, lateralidad, material);

        // Lo mandamos al DAO para que haga el INSERT
        EjercicioDAO dao = new EjercicioDAO();
        if (dao.insertarEjercicio(nuevoEjercicio)) {
            System.out.println("¡Ejercicio guardado en la base de datos!");

            // Actualizamos la tabla en tiempo real sin tener que recargar la app
            tablaEjercicios.getItems().add(nuevoEjercicio);

            // Limpiamos el formulario para que puedan meter otro
            txtNombre.clear();
            txtGrupoMuscular.clear();
            txtSubgrupoMuscular.clear();
            cbMecanica.getSelectionModel().clearSelection();
            cbLateralidad.getSelectionModel().clearSelection();
            cbMaterial.getSelectionModel().clearSelection();
        } else {
            System.err.println("Error al guardar en la base de datos.");
        }
    }
    @FXML
    public void onBorrarClick() {
        // 1. Obtenemos el ejercicio que el admin ha clicado en la tabla
        Ejercicio seleccionado = tablaEjercicios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            System.out.println("Por favor, selecciona un ejercicio de la tabla primero.");
            return;
        }

        // 2. Llamamos al DAO pasándole la ID de ese ejercicio
        EjercicioDAO dao = new EjercicioDAO();

        if (dao.borrarEjercicio(seleccionado.getIdEjercicio())) {
            System.out.println("Ejercicio borrado de la base de datos.");

            // 3. Lo quitamos de la tabla visualmente
            tablaEjercicios.getItems().remove(seleccionado);
        } else {
            System.err.println("No se pudo borrar. (¿Quizás está siendo usado en alguna rutina?)");
        }
    }
}