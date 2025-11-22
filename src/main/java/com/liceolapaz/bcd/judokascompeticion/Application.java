package com.liceolapaz.bcd.judokascompeticion;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(AppView.LOGIN.getFxmlFile()));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        stage.setTitle("Inicio de sesión");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}
