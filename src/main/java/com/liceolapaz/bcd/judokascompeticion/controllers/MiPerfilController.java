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
import pojos.User;

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
    private static User usuarioLogueado = SessionManager.getInstance().getUsuario();
    JudokasUsuariosDAO judokasUsuariosDAO = new JudokasUsuariosDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        User currentUser = SessionManager.getInstance().getUsuario();

        if (currentUser != null) {
            nombre.setText(currentUser.getNickname());
            nombreCompleto.setText(currentUser.getName() + " "  + currentUser.getLastName());
            usuario.setText(currentUser.getNickname());
            cant.setText(Long.toString(judokasUsuariosDAO.cantidadJudokas(currentUser)));
        } else {
            nombre.setText("Error de Sesión");
            System.err.println("Advertencia: Intento de acceso sin usuario logueado.");
        }
    }

    public void handleEditar(ActionEvent actionEvent) {
        ViewSwitcher.switchView(AppView.EDITARPERFIL);
    }
}
