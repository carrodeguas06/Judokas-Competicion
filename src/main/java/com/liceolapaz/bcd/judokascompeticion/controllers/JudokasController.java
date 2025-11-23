package com.liceolapaz.bcd.judokascompeticion.controllers;

import com.liceolapaz.bcd.judokascompeticion.Comboboxes;
import com.liceolapaz.bcd.judokascompeticion.database.FileReader;
import com.liceolapaz.bcd.judokascompeticion.navigation.SessionManager;
import com.liceolapaz.bcd.judokascompeticion.dao.judokas.JudokaDAOImpl;
import com.liceolapaz.bcd.judokascompeticion.dao.judokas.JudokasDAO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import pojos.User;
import pojos.Judoka;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JudokasController implements Initializable {
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
    private JudokasDAO judokasDAO = new JudokaDAOImpl();
    private static ObservableList<Judoka> judokas;
    private User usuarioLogueado;
    private Judoka judokaSeleccionado;

    private Timeline debounce;
    private static final int DEBOUNCE_DELAY_MS = 300;

    List<Judoka> judokasBuscados = new ArrayList<>();

    public void handleAnh() {
    }

    public void handleMod() {

    }

    public void handleEli() {
    }

    public void handleLim() {
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
            List<Judoka> jugadoresEncontrados = judokasDAO.obtenerJudokas(terminoBusqueda);
            judokas.addAll(jugadoresEncontrados);
        } else {
            judokas.addAll(judokasDAO.obtenerJudokas());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        judokas = FXCollections.observableArrayList();

        this.usuarioLogueado = SessionManager.getInstance().getUsuario();

        judokas.clear();
        judokas.addAll(judokasDAO.obtenerJudokas());
        meterEnTabla();
        try {
            List<String> b = FileReader.readToList(Comboboxes.BELTS.getUrl());
            ObservableList<String> b2 = FXCollections.observableList(b);
            List<String> c = FileReader.readToList(Comboboxes.COUNTRIES.getUrl());
            ObservableList<String> c2 = FXCollections.observableList(c);
            iCinturon.setItems(b2);
            iPais.setItems(c2);
            makeComboBoxSearchable(iCinturon);
            makeComboBoxSearchable(iPais);
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
