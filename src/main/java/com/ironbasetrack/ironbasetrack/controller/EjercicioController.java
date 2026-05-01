package com.ironbasetrack.ironbasetrack.controller;

import com.ironbasetrack.ironbasetrack.dao.EjercicioDAO;
import com.ironbasetrack.ironbasetrack.model.Ejercicio;
import com.ironbasetrack.ironbasetrack.util.ExportadorXML;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    @FXML private javafx.scene.control.TextField txtBuscador;
    @FXML private javafx.scene.layout.VBox panelAdmin;
    @FXML private javafx.scene.layout.HBox panelRM;
    @FXML private javafx.scene.control.TextField txtPesoRM;

    @FXML
    public void initialize() {
        // 1. Enlazar las columnas con la clase Ejercicio
        colNombre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nombre"));
        colGrupo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("grupoMuscular"));
        colSubgrupo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("subgrupoMuscular"));
        colMecanica.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("tipoMecanica"));
        colLateralidad.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("lateralidad"));
        colMaterial.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("material"));

        com.ironbasetrack.ironbasetrack.model.Usuario usuarioActual = com.ironbasetrack.ironbasetrack.util.Sesion.getUsuarioActual();

        if (usuarioActual != null) {
            if (usuarioActual.getIdRol() == 1) {
                // Es ADMIN: Ocultamos el panel de RMs
                panelRM.setVisible(false);
                panelRM.setManaged(false);
            } else {
                // Es ENTRENADOR/ATLETA: Ocultamos los botones de crear/borrar
                panelAdmin.setVisible(false);
                panelAdmin.setManaged(false);
            }
        }

        com.ironbasetrack.ironbasetrack.dao.EjercicioDAO dao = new com.ironbasetrack.ironbasetrack.dao.EjercicioDAO();
        javafx.collections.ObservableList<com.ironbasetrack.ironbasetrack.model.Ejercicio> listaEjercicios =
                javafx.collections.FXCollections.observableArrayList(dao.obtenerTodosLosEjercicios());

        javafx.collections.transformation.FilteredList<com.ironbasetrack.ironbasetrack.model.Ejercicio> datosFiltrados =
                new javafx.collections.transformation.FilteredList<>(listaEjercicios, b -> true);

        txtBuscador.textProperty().addListener((observable, oldValue, newValue) -> datosFiltrados.setPredicate(ejercicio -> {
            if (newValue == null || newValue.isEmpty()) return true;

            String filtro = newValue.toLowerCase();

            if (ejercicio.getNombre().toLowerCase().contains(filtro)) return true;
            if (ejercicio.getGrupoMuscular().toLowerCase().contains(filtro)) return true;
            if (ejercicio.getSubgrupoMuscular().toLowerCase().contains(filtro)) return true;
            return ejercicio.getMaterial().toLowerCase().contains(filtro);
        }));

        javafx.collections.transformation.SortedList<com.ironbasetrack.ironbasetrack.model.Ejercicio> datosOrdenados =
                new javafx.collections.transformation.SortedList<>(datosFiltrados);
        datosOrdenados.comparatorProperty().bind(tablaEjercicios.comparatorProperty());

        tablaEjercicios.setItems(datosOrdenados);
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
    @FXML
    public void onGuardarRMClick() {
        com.ironbasetrack.ironbasetrack.model.Ejercicio seleccionado = tablaEjercicios.getSelectionModel().getSelectedItem();
        String pesoTexto = txtPesoRM.getText().trim();

        if (seleccionado == null || pesoTexto.isEmpty()) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, selecciona un ejercicio de la tabla y escribe el peso.");
            alerta.showAndWait();
            return;
        }

        try {
            // Sustituir comas por puntos para evitar fallos al parsear a Double
            double peso = Double.parseDouble(pesoTexto.replace(",", "."));
            int idUsuario = com.ironbasetrack.ironbasetrack.util.Sesion.getUsuarioActual().getIdUsuario();
            int idEjercicio = seleccionado.getIdEjercicio();

            String sql = "INSERT INTO atleta_rm (id_usuario, id_ejercicio, peso_rm, fecha_consecucion) VALUES (?, ?, ?, CURRENT_DATE)";

            try (java.sql.Connection con = com.ironbasetrack.ironbasetrack.dao.ConexionDB.conectar();
                 java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idUsuario);
                ps.setInt(2, idEjercicio);
                ps.setDouble(3, peso);

                ps.executeUpdate();

                javafx.scene.control.Alert exito = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                exito.setHeaderText(null);
                exito.setContentText("¡RM de " + peso + " kg registrado en " + seleccionado.getNombre() + "!");
                exito.showAndWait();

                txtPesoRM.clear();

            } catch (java.sql.SQLException e) {
                System.err.println("Error de Base de Datos al guardar RM: " + e.getMessage());
            }

        } catch (NumberFormatException e) {
            javafx.scene.control.Alert error = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            error.setHeaderText(null);
            error.setContentText("El peso introducido no es válido. Usa números (ej: 100.5).");
            error.showAndWait();
        }
    }

}