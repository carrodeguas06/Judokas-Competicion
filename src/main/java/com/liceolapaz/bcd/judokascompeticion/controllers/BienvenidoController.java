package com.liceolapaz.bcd.judokascompeticion.controllers;

import com.liceolapaz.bcd.judokascompeticion.navigation.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pojos.User;

import java.net.URL;
import java.util.ResourceBundle;

public class BienvenidoController implements Initializable {
    @FXML
    private Label bienvenidoLabel;
    private User usuarioSelecionado;

    public void setBienvenidoLabel()
    {
        bienvenidoLabel.setText("¡Bienvenido "+ SessionManager.getInstance().getUsuario().getNickname() +"!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setBienvenidoLabel();
    }
}
