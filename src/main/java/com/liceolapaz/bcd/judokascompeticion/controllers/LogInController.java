package com.liceolapaz.bcd.judokascompeticion.controllers;

import com.liceolapaz.bcd.judokascompeticion.navigation.AppView;
import com.liceolapaz.bcd.judokascompeticion.navigation.SessionManager;
import com.liceolapaz.bcd.judokascompeticion.dao.usuarios.UsuarioDAOImpl;
import pojos.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    static List<User> usuarios = new ArrayList<>();
    static UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();

    private static User usuarioSelecionado;

    @FXML
    private TextField usuario;

    @FXML
    private PasswordField contrasena;

    @FXML
    private Button boton;

    public void onClic(ActionEvent event) throws IOException {

        if (validar())
        {
            SessionManager.getInstance().setUsuario(usuarioSelecionado);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(AppView.MAIN.getFxmlFile())));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Judokas - Panel Principal");
            stage.show();
            stage.centerOnScreen();
        }
    }
    private boolean validar()
    {
        if(usuario.getText().isEmpty()||contrasena.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Rellena los campos");
            alert.showAndWait();
            usuario.clear();
            contrasena.clear();
        }
        else
        {
            for (User u : usuarios) {
                if (u.getPassword().equals(contrasena.getText()) && (u.getNickname().equals(usuario.getText())))
                {
                    usuarioSelecionado = u;
                    return true;
                }
            }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("El usuario o la contraseña es incorrecto");
            alert.showAndWait();
            usuario.clear();
            contrasena.clear();
        }
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarios.addAll(usuarioDAO.obtenerUsuarios());
        Platform.runLater(() -> usuario.requestFocus());
    }
}
