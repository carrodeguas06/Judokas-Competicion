package com.liceolapaz.bcd.judokascompeticion.controllers;

import com.liceolapaz.bcd.judokascompeticion.dao.usuarios.UsuarioDAOImpl;
import com.liceolapaz.bcd.judokascompeticion.navigation.AppView;
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
import pojos.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private Button register;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellido;
    @FXML
    private TextField usuario;
    @FXML
    private PasswordField contrasena;
    @FXML
    private PasswordField rcontrasena;

    private static User usuarioSelecionado;
    static List<User> usuarios = new ArrayList<>();
    static UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();

    private boolean validar()
    {
        if(usuario.getText().isEmpty()||contrasena.getText().isEmpty() || nombre.getText().isEmpty() || apellido.getText().isEmpty() || rcontrasena.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Rellena los campos");
            alert.showAndWait();
            return false;
        }
        if (contrasena.getText().length()<4)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("La contraseña debe tener 4 caracteres");
            alert.showAndWait();
        }
        if (!contrasena.getText().equals(rcontrasena.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Las contraseñas no coinciden");
            alert.showAndWait();
        }
        else
        {
            for (User u : usuarios) {
                if (u.getNickname().equals(usuario.getText()))
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Este nombre de usuario ya existe");
                    alert.showAndWait();
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarios.addAll(usuarioDAO.obtenerUsuarios());
        Platform.runLater(() -> nombre.requestFocus());
    }


    public void logInClic(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(AppView.LOGIN.getFxmlFile())));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Judokas - Iniciar Sesión");
        stage.show();
        stage.centerOnScreen();
    }

    public void onClic(ActionEvent event) throws IOException {
        if (validar())
        {
            User u = new User();
            u.setNickname(usuario.getText());
            u.setPassword(contrasena.getText());
            u.setLastName(apellido.getText());
            u.setName(nombre.getText());
            Byte b = 0;
            u.setAdmin(b);
            usuarioDAO.crearUsuario(u);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Creación de Usuario");
            alert.setHeaderText("La creación del usuario ha sido exitosa");
            alert.showAndWait();
            nombre.clear();
            apellido.clear();
            contrasena.clear();
            usuario.clear();
            rcontrasena.clear();
        }
    }
}
