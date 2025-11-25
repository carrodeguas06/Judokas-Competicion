package com.liceolapaz.bcd.judokascompeticion.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public class ViewSwitcher
{
    private static BorderPane mainPane;

    public static void setMainPane(BorderPane mainPane)
    {
        ViewSwitcher.mainPane = mainPane;
    }

    public static void switchView(AppView view) {
        if(mainPane == null)
        {
            System.err.println("Error: el contenido no ha sido inicializado");
            return;
        }

        try {
            Parent viewRoot = FXMLLoader.load(Objects.requireNonNull(ViewSwitcher.class.getResource(view.getFxmlFile())));
            mainPane.setCenter(viewRoot);
        }catch (IOException e)
        {
            System.err.println("Error al cargar vista");
            e.printStackTrace();
        }
    }
}
