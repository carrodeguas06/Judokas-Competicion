package com.liceolapaz.bcd.judokascompeticion.controllers;

import com.liceolapaz.bcd.judokascompeticion.Comboboxes;
import com.liceolapaz.bcd.judokascompeticion.dao.competiciones.CompeticionDAOImpl;
import com.liceolapaz.bcd.judokascompeticion.dao.competiciones.CompeticionesDAO;
import com.liceolapaz.bcd.judokascompeticion.database.FileReader;
import com.liceolapaz.bcd.judokascompeticion.navigation.SessionManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import pojos.Competition;
import pojos.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CompeticionesController implements Initializable {

    @FXML private TextField searchBar;
    @FXML private TableView<Competition> table;
    @FXML private TableColumn<Competition, String> tTemp;
    @FXML private TableColumn<Competition,String> tId;
    @FXML private TableColumn<Competition,String> tnombre;
    @FXML private TableColumn<Competition,String> tLugar;
    @FXML private TableColumn<Competition,String> tPais;
    @FXML private TableColumn<Competition,String> tTipo;
    @FXML private TextField iNombre;
    @FXML private TextField iLugar;
    @FXML private ComboBox iPais;
    @FXML private ComboBox iTipo;
    @FXML private Button bAnh;
    @FXML private Button bMod;
    @FXML
    private Button bLim;
    @FXML
    private Button bEli;
    @FXML
    private TextField iAnho;
    private Competition competicionSeleccionada;
    private CompeticionesDAO competicionesDAO = new CompeticionDAOImpl();
    ObservableList<Competition> competiciones = FXCollections.observableArrayList();
    private Timeline debounce;
    private static final int DEBOUNCE_DELAY_MS = 300;
    private User usuarioLogueado;

    public void actualizarVista(User user) {
        if (user.getAdmin()!=1) {
            iNombre.setEditable(false);
            iLugar.setEditable(false);
            iTipo.setEditable(false);
            iPais.setEditable(false);
            iAnho.setEditable(false);
            bAnh.setVisible(false);
            bMod.setVisible(false);
            bLim.setVisible(false);
            bEli.setVisible(false);
        }
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
    private void rellenarFormulario(Competition  competition)
    {
        iNombre.setText(competicionSeleccionada.getName());
        iLugar.setText(competicionSeleccionada.getCity());
        iPais.setValue(competicionSeleccionada.getCountry());
        iTipo.setValue(competicionSeleccionada.getType());
        iAnho.setText(Integer.toString(competicionSeleccionada.getTemp()));
    }
    private void buscarCompeticiones(String terminoBusqueda) {
        competiciones.clear();
        if (terminoBusqueda != null && !terminoBusqueda.trim().isEmpty()) {
            List<Competition> competicionesEncontradas = competicionesDAO.obtenerCompeticion(terminoBusqueda);
            competiciones.addAll(competicionesEncontradas);
        } else {
            competiciones.addAll(competicionesDAO.obtenerCompeticiones());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actualizarVista(SessionManager.getInstance().getUsuario());

        competiciones = FXCollections.observableArrayList();

        this.usuarioLogueado = SessionManager.getInstance().getUsuario();

        competiciones.addAll(competicionesDAO.obtenerCompeticiones());
        meterEnTabla();
        try {
            List<String> b = FileReader.readToList(Comboboxes.COMPETITION_TYPES.getUrl());
            ObservableList<String> b2 = FXCollections.observableList(b);
            List<String> c = FileReader.readToList(Comboboxes.COUNTRIES.getUrl());
            ObservableList<String> c2 = FXCollections.observableList(c);
            iTipo.setItems(b2);
            iPais.setItems(c2);
            makeComboBoxSearchable(iTipo);
            makeComboBoxSearchable(iPais);
        } catch (IOException e) {
            System.err.println("Error al leer la combobox");
        }

        debounce = new Timeline(new KeyFrame(Duration.millis(DEBOUNCE_DELAY_MS), e ->
        {
            buscarCompeticiones(searchBar.getText());
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
                        competicionSeleccionada = newValue;
                        rellenarFormulario(competicionSeleccionada);
                    }
                });
    }

    private void meterEnTabla() {
        tId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tnombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        tTipo.setCellValueFactory(new PropertyValueFactory<>("type"));
        tLugar.setCellValueFactory(new PropertyValueFactory<>("city"));
        tPais.setCellValueFactory(new PropertyValueFactory<>("country"));
        tTemp.setCellValueFactory(new PropertyValueFactory<>("temp"));

        table.setItems(competiciones);
    }

    public void handleAnh(ActionEvent actionEvent) {
    }

    public void handleMod(ActionEvent actionEvent) {
    }

    public void handleEli(ActionEvent actionEvent) {
    }

    public void handleLim(ActionEvent actionEvent) {
    }
}
