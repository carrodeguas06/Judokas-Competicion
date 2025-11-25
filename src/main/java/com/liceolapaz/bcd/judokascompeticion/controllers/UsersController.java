package com.liceolapaz.bcd.judokascompeticion.controllers;

import com.liceolapaz.bcd.judokascompeticion.dao.usuarios.UsuarioDAOImpl;
import com.liceolapaz.bcd.judokascompeticion.dao.usuarios.UsuariosDAO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import pojos.User;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    @FXML private CheckBox admin;
    @FXML private Label usuario;
    @FXML private Label nombre;
    @FXML private Label cantidad;
    @FXML private TextField searchBar;
    @FXML private TableView<User> table;
    @FXML private TableColumn<User,String> tId;
    @FXML private TableColumn<User,String> tnombre;
    @FXML private TableColumn<User,String> tApellido;
    @FXML private TableColumn<User,String> tUsuario;
    @FXML private TableColumn<User, Boolean> tAdmin;
     private ObservableList<User> usuarios = FXCollections.observableArrayList();
     static UsuariosDAO  usuarioDAO = new UsuarioDAOImpl();
    private Timeline debounce;
    private static final int DEBOUNCE_DELAY_MS = 300;
    private User usuarioSeleccionado;


    private void buscarUsuarios(String terminoBusqueda) {
        usuarios.clear();
        if (terminoBusqueda != null && !terminoBusqueda.trim().isEmpty()) {
            List<User> usuariosEncontrados = usuarioDAO.buscarUsuario(terminoBusqueda);
            usuarios.addAll(usuariosEncontrados);
            cantidad.setText(Integer.toString(usuarios.size()));
        } else {
            usuarios.addAll(usuarioDAO.obtenerUsuarios());
            cantidad.setText(Integer.toString(usuarios.size()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usuarios = FXCollections.observableArrayList();

        usuarios.addAll(usuarioDAO.obtenerUsuarios());
        meterEnTabla();

        debounce = new Timeline(new KeyFrame(Duration.millis(DEBOUNCE_DELAY_MS), e ->
        {
            buscarUsuarios(searchBar.getText());
        }));
        debounce.setCycleCount(1);

        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
        {
            debounce.stop();
            debounce.playFromStart();
        });

        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        usuarioSeleccionado = newValue;
                        rellenarFormulario(usuarioSeleccionado);
                    }
                });
        cantidad.setText(String.valueOf(usuarios.size()));
    }

    private void rellenarFormulario(User usuarioSeleccionado) {
        nombre.setText(usuarioSeleccionado.getName() + " " + usuarioSeleccionado.getLastName());
        usuario.setText(usuarioSeleccionado.getNickname());
        if (usuarioSeleccionado.getAdmin() == 1)
        {
            admin.setSelected(true);
        }else
        {
            admin.setSelected(false);
        }

    }
    private void meterEnTabla() {
        tId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tnombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        tApellido.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tUsuario.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        tAdmin.setCellValueFactory(new PropertyValueFactory<>("admin"));

        table.setItems(usuarios);
    }

    public void handleActualizar(ActionEvent actionEvent) {
        if (this.usuarioSeleccionado == null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Ningún usuario seleccionado");
            alert.showAndWait();

            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Edición");
        confirmacion.setHeaderText("¿Estás seguro de que quieres editar a " + usuarioSeleccionado.getName() + "?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                if (admin.isSelected())
                {
                    usuarioSeleccionado.setAdmin((byte) 1);
                }else
                {
                    usuarioSeleccionado.setAdmin((byte) 0);
                }
                usuarioDAO.setAdmin(usuarioSeleccionado);
                usuarios.clear();
                usuarios.addAll(usuarioDAO.obtenerUsuarios());

        }
    }
}
