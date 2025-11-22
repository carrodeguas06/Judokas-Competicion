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
    @FXML public TableColumn<Judoka, Integer> tId;
    @FXML public TableColumn<Judoka, String> tnombre;
    @FXML public TableColumn<Judoka, String> tApellido;
    @FXML public TableColumn<Judoka, String> tPais;
    @FXML public TableColumn<Judoka, String> tCinturon;
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
        tId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tnombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        tApellido.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tPais.setCellValueFactory(new PropertyValueFactory<>("country"));
        tCinturon.setCellValueFactory(new PropertyValueFactory<>("belt"));

        table.setItems(judokas);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        judokas = FXCollections.observableArrayList();

        this.usuarioLogueado = SessionManager.getInstance().getUsuario();

        judokas.clear();
        judokas.addAll(judokasDAO.obtenerJudokas());
        meterEnTabla();


    }
}
