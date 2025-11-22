package com.liceolapaz.bcd.judokascompeticion.controllers;

import com.liceolapaz.bcd.judokascompeticion.navigation.SessionManager;
import com.liceolapaz.bcd.judokascompeticion.dao.judokas.JudokaDAOImpl;
import com.liceolapaz.bcd.judokascompeticion.dao.judokas.JudokasDAO;
import pojos.User;
import pojos.Judoka;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

import java.util.ResourceBundle;

public class JudokasController implements Initializable {
    @FXML
    public TableView<Judoka> table;
    @FXML
    public TableColumn tId;
    @FXML
    public TableColumn tnombre;
    @FXML
    public TableColumn tApellido;
    @FXML
    public TableColumn tPais;
    @FXML
    public TableColumn tCinturon;
    private JudokasDAO judokasDAO = new JudokaDAOImpl();
    private static ObservableList<Judoka> judokas;
    private User usuarioLogueado;
    private Judoka judokaSeleccionado;
    public void handleAnh() {
    }

    public void handleMod() {

    }

    public void handleEli() {
    }

    public void handleLim() {
    }

    public void meterEnTabla()
    {
        tId.setCellFactory(new PropertyValueFactory<>("id"));
        tnombre.setCellFactory(new PropertyValueFactory<>("nombre"));
        tApellido.setCellFactory(new PropertyValueFactory<>("apellido"));
        tPais.setCellFactory(new PropertyValueFactory<>("pais"));
        tCinturon.setCellFactory(new PropertyValueFactory<>("cinturon"));

        table.setItems(judokas);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        judokas = FXCollections.observableArrayList();

        this.usuarioLogueado = SessionManager.getInstance().getUsuario();

        judokas.clear();
        judokas.addAll(judokasDAO.obtenerJudokas());
    }
}
