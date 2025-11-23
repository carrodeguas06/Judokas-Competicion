package com.liceolapaz.bcd.judokascompeticion.controllers;

import com.liceolapaz.bcd.judokascompeticion.dao.judokasusuarios.JudokasUsuariosDAO;
import com.liceolapaz.bcd.judokascompeticion.dao.judokasusuarios.JudokasUsuariosDAOImpl;
import com.liceolapaz.bcd.judokascompeticion.navigation.AppView;
import com.liceolapaz.bcd.judokascompeticion.navigation.SessionManager;
import com.liceolapaz.bcd.judokascompeticion.navigation.ViewSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MiPerfilController implements Initializable {
    @FXML
    private Label cant;
    @FXML
    private Label nombre;
    @FXML
    private Label nombreCompleto;
    @FXML
    private Label usuario;
    @FXML
    private Button editar;
    JudokasUsuariosDAO judokasUsuariosDAO = new JudokasUsuariosDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nombre.setText(SessionManager.getInstance().getUsuario().getNickname());
        nombreCompleto.setText(SessionManager.getInstance().getUsuario().getName() + " "  + SessionManager.getInstance().getUsuario().getLastName());
        usuario.setText(SessionManager.getInstance().getUsuario().getNickname());
        cant.setText(Long.toString(judokasUsuariosDAO.cantidadJudokas(SessionManager.getInstance().getUsuario())));
    }

    public void handleEditar(ActionEvent actionEvent) {
        ViewSwitcher.switchView(AppView.EDITARPERFIL);
    }
}
