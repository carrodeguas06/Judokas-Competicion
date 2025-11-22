package com.liceolapaz.bcd.judokascompeticion.controllers;

import com.liceolapaz.bcd.judokascompeticion.SessionManager;
import com.liceolapaz.bcd.judokascompeticion.database.DatabaseConnection;
import com.liceolapaz.bcd.judokascompeticion.pojo.Usuario;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;

public class JudokasController implements Initializable {

    private Usuario usuarioLogueado;
    public void handleAnh() {
    }

    public void handleMod() {

    }


    public void handleEli() {
    }

    public void handleLim() {
    }

    private static void cargarJudokas()
    {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.usuarioLogueado = SessionManager.getInstance().getUsuario();
    }
}
