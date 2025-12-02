package com.liceolapaz.bcd.judokascompeticion;

import com.liceolapaz.bcd.judokascompeticion.navigation.AppView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(AppView.LOGIN.getFxmlFile()));
        Scene scene = new Scene(fxmlLoader.load(), 500, 350);
        try {

            Image icon = new Image(getClass().getResourceAsStream("/img/icono.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Error al cargar el icono: /img/icono.png");
            e.printStackTrace();
        }
        stage.setTitle("Inicio de sesión");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
        String cssPath = getClass().getResource("/css/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
    }
}
