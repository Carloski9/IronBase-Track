package com.ironbasetrack.ironbasetrack.controller;

import com.ironbasetrack.ironbasetrack.dao.EjercicioDAO;
import com.ironbasetrack.ironbasetrack.model.Ejercicio;
import com.ironbasetrack.ironbasetrack.util.ExportadorXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

public class EjercicioController {

    @FXML private TableView<Ejercicio> tablaEjercicios;
    @FXML private TableColumn<Ejercicio, String> colNombre;
    @FXML private TableColumn<Ejercicio, String> colGrupo;
    @FXML private TableColumn<Ejercicio, String> colSubgrupo;
    @FXML private TableColumn<Ejercicio, String> colMecanica;
    @FXML private TableColumn<Ejercicio, String> colLateralidad;
    @FXML private TableColumn<Ejercicio, String> colMaterial;
    @FXML private TextField txtNombre;
    @FXML private TextField txtGrupoMuscular;
    @FXML private TextField txtSubgrupoMuscular;
    @FXML private ComboBox<String> cbMecanica;
    @FXML private ComboBox<String> cbLateralidad;
    @FXML private ComboBox<String> cbMaterial;
    @FXML private javafx.scene.control.Button btnGuardar;
    @FXML private javafx.scene.control.Button btnBorrar;
    @FXML private javafx.scene.control.Button btnExportar;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colGrupo.setCellValueFactory(new PropertyValueFactory<>("grupoMuscular"));
        colSubgrupo.setCellValueFactory(new PropertyValueFactory<>("subgrupoMuscular"));
        colMecanica.setCellValueFactory(new PropertyValueFactory<>("tipoMecanica"));
        colLateralidad.setCellValueFactory(new PropertyValueFactory<>("lateralidad"));
        colMaterial.setCellValueFactory(new PropertyValueFactory<>("material"));

        cargarEjerciciosEnTabla();
        com.ironbasetrack.ironbasetrack.model.Usuario usuarioActual = com.ironbasetrack.ironbasetrack.util.Sesion.getUsuarioActual();

        if (usuarioActual != null && usuarioActual.getIdRol() != 1) {
            btnGuardar.setVisible(false);
            btnGuardar.setManaged(false);

            btnBorrar.setVisible(false);
            btnBorrar.setManaged(false);

            btnExportar.setVisible(false);
            btnExportar.setManaged(false);
        }
    }

    private void cargarEjerciciosEnTabla() {
        EjercicioDAO dao = new EjercicioDAO();
        // Convierte la lista normal de Java a una lista "Observable" que es la que usan las tablas de JavaFX
        javafx.collections.ObservableList<Ejercicio> listaObservable = javafx.collections.FXCollections.observableArrayList(dao.obtenerTodosLosEjercicios());
        tablaEjercicios.setItems(listaObservable);
    }

    // 3. El method que guarda el ejercicio al pulsar el botón
    @FXML
    public void onGuardarClick() {
        String nombre = txtNombre.getText();
        String grupo = txtGrupoMuscular.getText();
        String subgrupo = txtSubgrupoMuscular.getText();
        String mecanica = cbMecanica.getValue();
        String lateralidad = cbLateralidad.getValue();
        String material = cbMaterial.getValue();

        if (nombre.isEmpty() || mecanica == null || lateralidad == null || material == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos Incompletos");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, rellena todos los campos clave y selecciona opciones en los desplegables.");
            alerta.showAndWait();
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Guardado");
        confirmacion.setHeaderText("Vas a añadir el ejercicio: " + nombre);
        confirmacion.setContentText("¿Estás seguro de que los datos introducidos son correctos?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

            Ejercicio nuevoEjercicio = new Ejercicio(0, nombre, grupo, subgrupo, mecanica, lateralidad, material);
            EjercicioDAO dao = new EjercicioDAO();

            if (dao.insertarEjercicio(nuevoEjercicio)) {
                tablaEjercicios.getItems().add(nuevoEjercicio);

                txtNombre.clear();
                txtGrupoMuscular.clear();
                txtSubgrupoMuscular.clear();
                cbMecanica.getSelectionModel().clearSelection();
                cbLateralidad.getSelectionModel().clearSelection();
                cbMaterial.getSelectionModel().clearSelection();

                Alert exito = new Alert(Alert.AlertType.INFORMATION);
                exito.setTitle("Éxito");
                exito.setHeaderText(null);
                exito.setContentText("El ejercicio se ha guardado correctamente en el catálogo.");
                exito.showAndWait();
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error de Base de Datos");
                error.setHeaderText(null);
                error.setContentText("Hubo un problema al guardar el ejercicio en MariaDB.");
                error.showAndWait();
            }
        }
    }

    @FXML
    public void onBorrarClick() {
        Ejercicio seleccionado = tablaEjercicios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            // Alerta de advertencia si no ha seleccionado nada
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Atención");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecciona un ejercicio de la tabla primero.");
            alerta.showAndWait();
            return;
        }

        // Alerta de confirmación antes de borrar
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Borrado");
        confirmacion.setHeaderText("Vas a borrar: " + seleccionado.getNombre());
        confirmacion.setContentText("¿Estás completamente seguro de esta acción?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            EjercicioDAO dao = new EjercicioDAO();

            if (dao.borrarEjercicio(seleccionado.getIdEjercicio())) {
                // Si MariaDB da el OK, lo borramos de la tabla visual
                tablaEjercicios.getItems().remove(seleccionado);

                Alert exito = new Alert(Alert.AlertType.INFORMATION);
                exito.setTitle("Borrado exitoso");
                exito.setHeaderText(null);
                exito.setContentText("El ejercicio se ha eliminado de la base de datos.");
                exito.showAndWait();
            } else {
                // Si MariaDB lo bloquea (ej. está en una rutina)
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Error al borrar");
                error.setHeaderText("Operación bloqueada por la Base de Datos");
                error.setContentText("No se pudo borrar. Es posible que este ejercicio esté siendo utilizado en alguna rutina o registro de RM.");
                error.showAndWait();
            }
        }
    }

    @FXML
    public void onExportarXMLClick() {
        String rutaDestino = "docs/xml/datos.xml";
        boolean exito = ExportadorXML.exportarCatalogoEjercicios(rutaDestino);

        Alert alerta = new Alert(exito ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alerta.setTitle("Exportación XML");
        alerta.setHeaderText(null);
        alerta.setContentText(exito ? "El catálogo se ha exportado correctamente en " + rutaDestino : "Hubo un error al exportar el archivo XML.");
        alerta.showAndWait();
    }
}