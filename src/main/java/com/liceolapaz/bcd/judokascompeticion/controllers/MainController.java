package com.liceolapaz.bcd.judokascompeticion.controllers;

import com.liceolapaz.bcd.judokascompeticion.navigation.AppView;
import com.liceolapaz.bcd.judokascompeticion.navigation.SessionManager;
import com.liceolapaz.bcd.judokascompeticion.navigation.ViewSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class MainController {
    @FXML
    public BorderPane mainContentPane;

    @FXML
    public void initialize()
    {
        ViewSwitcher.setMainPane(mainContentPane);
        ViewSwitcher.switchView(AppView.BIENVENIDO);
    }

    @FXML
    private void handleShowJudokas()
    {ViewSwitcher.switchView(AppView.JUDOKAS);}

    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        SessionManager.getInstance().clearSession();

        Stage stage = (Stage) mainContentPane.getScene().getWindow();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(AppView.LOGIN.getFxmlFile())));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Judokas Competición - Inicio de sesión");
        stage.show();
    }



    private void showAlert(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
