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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import pojos.Competition;
import pojos.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class CompeticionesController implements Initializable {

    @FXML private Label cantidad;
    @FXML private Slider iNota;
    @FXML private TableColumn<Competition,Double> tNota;
    @FXML private TextField searchBar;
    @FXML private TableView<Competition> table;
    @FXML private TableColumn<Competition, Integer> tTemp;
    @FXML private TableColumn<Competition,String> tId;
    @FXML private TableColumn<Competition,String> tnombre;
    @FXML private TableColumn<Competition,String> tLugar;
    @FXML private TableColumn<Competition,String> tPais;
    @FXML private TableColumn<Competition,String> tTipo;
    @FXML private TextField iNombre;
    @FXML private TextField iLugar;
    @FXML private ComboBox iPais;
    @FXML private ComboBox iTipo;
    @FXML private Button bAnh; //TODO arreglar el slideBar
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
            iNota.setValueChanging(false);
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

    private boolean camposSonValidos() {
        if (iNombre.getText().isEmpty()||
                iLugar.getText().isEmpty()||
                iAnho.getText().isEmpty()||
                iPais.getValue() == null ||
                iTipo.getValue() == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    private void rellenarFormulario(Competition  competition)
    {
        iNombre.setText(competicionSeleccionada.getName());
        iLugar.setText(competicionSeleccionada.getCity());
        iPais.setValue(competicionSeleccionada.getCountry());
        iTipo.setValue(competicionSeleccionada.getType());
        iAnho.setText(Integer.toString(competicionSeleccionada.getTemp()));
        iNota.setValue(competicionSeleccionada.getNote().doubleValue());
    }
    private void buscarCompeticiones(String terminoBusqueda) {
        competiciones.clear();
        if (terminoBusqueda != null && !terminoBusqueda.trim().isEmpty()) {
            List<Competition> competicionesEncontradas = competicionesDAO.obtenerCompeticion(terminoBusqueda);
            competiciones.addAll(competicionesEncontradas);
            cantidad.setText(Integer.toString(competiciones.size()));
        } else {
            competiciones.addAll(competicionesDAO.obtenerCompeticiones());
            cantidad.setText(Integer.toString(competiciones.size()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final String regex = "\\d*";

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches(regex)) {
                return change;
            } else {
                return null;
            }
        };

        iAnho.setTextFormatter(new TextFormatter<>(integerFilter));


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
        cantidad.setText(Integer.toString(competiciones.size()));
    }

    private void meterEnTabla() {
        tId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tnombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        tTipo.setCellValueFactory(new PropertyValueFactory<>("type"));
        tLugar.setCellValueFactory(new PropertyValueFactory<>("city"));
        tPais.setCellValueFactory(new PropertyValueFactory<>("country"));
        tTemp.setCellValueFactory(new PropertyValueFactory<>("temp"));
        tNota.setCellValueFactory(new PropertyValueFactory<>("note"));

        table.setItems(competiciones);
    }

    public void handleAnh() {
        if(camposSonValidos())
        {
            Competition competition = new Competition();
            competition.setName(iNombre.getText());
            competition.setCity(iLugar.getText());
            competition.setCountry(iPais.getValue().toString());
            competition.setTemp(Integer.valueOf(iAnho.getText().trim()));
            competition.setType(iTipo.getValue().toString());
            competition.setNote(BigDecimal.valueOf(iNota.getValue()));

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Adición");
            confirmacion.setHeaderText("¿Estás seguro de que quieres añadir " + competition.getName() + "?");
            confirmacion.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                competicionesDAO.anhadirCompeticion(competition);
                competiciones.clear();
                competiciones.addAll(competicionesDAO.obtenerCompeticiones());
                handleLim();
            }
            competicionesDAO.anhadirCompeticion(competition);
        }
    }

    public void handleMod() {
        if(this.competicionSeleccionada == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Ninguna competición seleccionada");
            alert.showAndWait();
            return;
        }
        if (camposSonValidos())
        {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar Edición");
            confirmacion.setHeaderText("¿Estás seguro de que quieres editar " + competicionSeleccionada.getName() + "?");
            confirmacion.setContentText("Esta acción no se puede deshacer.");
            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK)
            {
                competicionSeleccionada.setName(iNombre.getText());
                competicionSeleccionada.setCity(iLugar.getText());
                competicionSeleccionada.setCountry(iPais.getValue().toString());
                competicionSeleccionada.setTemp(Integer.parseInt(iAnho.getText()));
                competicionSeleccionada.setType(iTipo.getValue().toString());
                competicionSeleccionada.setNote(BigDecimal.valueOf(iNota.getValue()));
                competicionesDAO.editarCompeticion(competicionSeleccionada);
                competiciones.clear();
                competiciones.addAll(competicionesDAO.obtenerCompeticiones());
                handleLim();
            }
        }
    }

    public void handleEli() {
        if (competicionSeleccionada == null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Seleccione la competición");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Estás seguro de que quieres eliminar " + competicionSeleccionada.getName() + "?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            competicionesDAO.eliminarCompeticion(competicionSeleccionada);
            competiciones.clear();
            competiciones.addAll(competicionesDAO.obtenerCompeticiones());
            cantidad.setText(Integer.toString(competiciones.size()));
            handleLim();
        }
    }

    public void handleLim() {
        iNombre.clear();
        iLugar.clear();
        iAnho.clear();
        iTipo.setValue(null);
        iPais.setValue(null);
        iNota.setValue(0);
        table.getSelectionModel().clearSelection();
    }
}
