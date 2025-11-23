package com.liceolapaz.bcd.judokascompeticion.controllers;

import com.liceolapaz.bcd.judokascompeticion.Comboboxes;
import com.liceolapaz.bcd.judokascompeticion.dao.tecnicas.TecnicaDAOImpl;
import com.liceolapaz.bcd.judokascompeticion.dao.tecnicas.TecnicasDAO;
import com.liceolapaz.bcd.judokascompeticion.database.FileReader;
import com.liceolapaz.bcd.judokascompeticion.navigation.SessionManager;
import com.liceolapaz.bcd.judokascompeticion.dao.judokas.JudokaDAOImpl;
import com.liceolapaz.bcd.judokascompeticion.dao.judokas.JudokasDAO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.util.Duration;
import pojos.Judoka;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.cell.PropertyValueFactory;
import pojos.User;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class JudokasController implements Initializable {
    @FXML
    private Button bAnh;
    @FXML
    private Button bMod;
    @FXML
    private Button bLim;
    @FXML
    private Button bEli;
    @FXML
    private ComboBox<String> iTecnica;
    @FXML
    private TextField iNombre;
    @FXML
    private TextField iApellido;
    @FXML
    private TableView<Judoka> table;
    @FXML
    private TableColumn<Judoka, Integer> tId;
    @FXML
    private TableColumn<Judoka, String> tnombre;
    @FXML
    private TableColumn<Judoka, String> tApellido;
    @FXML
    private TableColumn<Judoka, String> tPais;
    @FXML
    private TableColumn<Judoka, String> tCinturon;
    @FXML
    private ComboBox<String> iCinturon;
    @FXML
    private ComboBox<String> iPais;
    @FXML
    private TextField searchBar;
    private JudokasDAO judokaDAO = new JudokaDAOImpl();
    private ObservableList<Judoka> judokas;
    private Judoka judokaSeleccionado;
    private TecnicasDAO tecnicaDAO = new TecnicaDAOImpl();

    private Timeline debounce;
    private static final int DEBOUNCE_DELAY_MS = 300;
    private User usuarioLogueado;

    public void handleAnh() {
        if (camposSonValidos())
        {
            Judoka j = new Judoka(
                    iNombre.getText(),
                    iApellido.getText(),
                    iCinturon.getValue(),
                    iPais.getValue(),
                    tecnicaDAO.getTecnica(iTecnica.getValue())
            );

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Adición");
            confirmacion.setHeaderText("¿Estás seguro de que quieres añadir a " + j.getName() + "?");
            confirmacion.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                judokaDAO.anhadirJudoka(j);
                judokas.clear();
                judokas.addAll(judokaDAO.obtenerJudokas());
                handleLim();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Rellena todos los campos");
            alert.showAndWait();
        }
    }

    public void handleMod() {
        if (this.judokaSeleccionado == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Ningún judoka seleccionado");
            alert.showAndWait();
            return;
        }
        if(camposSonValidos())
        {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Edición");
            confirmacion.setHeaderText("¿Estás seguro de que quieres editar a " + judokaSeleccionado.getName() + "?");
            confirmacion.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                judokaSeleccionado.setName(iNombre.getText());
                judokaSeleccionado.setLastName(iApellido.getText());
                judokaSeleccionado.setBelt(iCinturon.getValue());
                judokaSeleccionado.setCountry(iPais.getValue());
                judokaSeleccionado.setEspecialTecnique(tecnicaDAO.getTecnica(iTecnica.getValue()));
                judokaDAO.editarJudoka(judokaSeleccionado);
                judokas.clear();
                judokas.addAll(judokaDAO.obtenerJudokas());
                handleLim();
            }
        }
    }

    private boolean camposSonValidos() {
        if (iNombre.getText().isEmpty()||
        iApellido.getText().isEmpty()||
        iCinturon.getValue().isEmpty()||
        iPais.getValue().isEmpty()||
        iTecnica.getValue().isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void actualizarVista(User user) {
        if (user.getAdmin()!=1) {
            iNombre.setEditable(false);
            iApellido.setEditable(false);
            iCinturon.setEditable(false);
            iPais.setEditable(false);
            iTecnica.setEditable(false);
            bAnh.setVisible(false);
            bMod.setVisible(false);
            bLim.setVisible(false);
            bEli.setVisible(false);
        }
    }

    public void handleEli() {
        if (judokaSeleccionado == null) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Seleccione el judoka");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Estás seguro de que quieres eliminar a " + judokaSeleccionado.getName() + "?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            judokaDAO.eliminarJudoka(judokaSeleccionado);
            judokas.clear();
            judokas.addAll(judokaDAO.obtenerJudokas());
            handleLim();
        }
    }

    public void handleLim() {
        iNombre.clear();
        iApellido.clear();
        iCinturon.setValue(null);
        iPais.setValue(null);
        iTecnica.setValue(null);
        judokaSeleccionado = null;
        table.getSelectionModel().clearSelection();
    }

    public void meterEnTabla() {
        tId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tnombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        tApellido.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tPais.setCellValueFactory(new PropertyValueFactory<>("country"));
        tCinturon.setCellValueFactory(new PropertyValueFactory<>("belt"));

        table.setItems(judokas);
    }

    private void buscarJudokas(String terminoBusqueda) {
        judokas.clear();
        if (terminoBusqueda != null && !terminoBusqueda.trim().isEmpty()) {
            List<Judoka> jugadoresEncontrados = judokaDAO.obtenerJudokas(terminoBusqueda);
            judokas.addAll(jugadoresEncontrados);
        } else {
            judokas.addAll(judokaDAO.obtenerJudokas());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        actualizarVista(SessionManager.getInstance().getUsuario());

        judokas = FXCollections.observableArrayList();

        this.usuarioLogueado = SessionManager.getInstance().getUsuario();

        judokas.addAll(judokaDAO.obtenerJudokas());
        meterEnTabla();
        try {
            List<String> b = FileReader.readToList(Comboboxes.BELTS.getUrl());
            ObservableList<String> b2 = FXCollections.observableList(b);
            List<String> c = FileReader.readToList(Comboboxes.COUNTRIES.getUrl());
            ObservableList<String> c2 = FXCollections.observableList(c);
            List<String> t = tecnicaDAO.getNombreTecnicas();
            ObservableList<String> t2 = FXCollections.observableList(t);
            iCinturon.setItems(b2);
            iPais.setItems(c2);
            iTecnica.setItems(t2);
            makeComboBoxSearchable(iCinturon);
            makeComboBoxSearchable(iPais);
            makeComboBoxSearchable(iTecnica);
        } catch (IOException e) {
            System.err.println("Error al leer la combobox");
        }

        debounce = new Timeline(new KeyFrame(Duration.millis(DEBOUNCE_DELAY_MS), e ->
        {
            buscarJudokas(searchBar.getText());
        }));
        debounce.setCycleCount(1);

        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
        {
            debounce.stop();
            debounce.playFromStart();
        });

        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        judokaSeleccionado = newValue;
                        rellenarFormulario(judokaSeleccionado);
                    }
                });
    }

    private void rellenarFormulario(Judoka judokaSeleccionado) {
        iNombre.setText(judokaSeleccionado.getName());
        iApellido.setText(judokaSeleccionado.getLastName());
        iCinturon.setValue(judokaSeleccionado.getBelt());
        iPais.setValue(judokaSeleccionado.getCountry());
        iTecnica.setValue(tecnicaDAO.getNombreTecnicas().get(judokaSeleccionado.getEspecialTecnique().getId()));
    }

    public void makeComboBoxSearchable(ComboBox<String> comboBox) {

        final ObservableList<String> masterData = comboBox.getItems();

        comboBox.setEditable(true);

        final javafx.scene.control.TextField editor = comboBox.getEditor();


        comboBox.getEditor().setOnKeyReleased(e -> {

            final String currentText = comboBox.getEditor().getText().toLowerCase();

            if (currentText.isEmpty()) {
                comboBox.setItems(masterData);
                return;
            }

            FilteredList<String> filtered = masterData.filtered(
                    item -> item.toLowerCase().contains(currentText)
            );

            if (!filtered.isEmpty()) {
                comboBox.setItems(filtered);
                comboBox.show();
            }

            comboBox.getEditor().setText(currentText);
            comboBox.getEditor().positionCaret(currentText.length());
        });

        editor.setOnAction(e -> {
            String selected = editor.getText();
            if (masterData.contains(selected)) {
                comboBox.setValue(selected);
            }
        });
    }
}
