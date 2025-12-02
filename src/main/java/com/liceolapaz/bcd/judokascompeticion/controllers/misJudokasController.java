package com.liceolapaz.bcd.judokascompeticion.controllers;

import com.liceolapaz.bcd.judokascompeticion.dao.judokas.JudokaDAOImpl;
import com.liceolapaz.bcd.judokascompeticion.dao.judokas.JudokasDAO;
import com.liceolapaz.bcd.judokascompeticion.dao.judokasusuarios.JudokasUsuariosDAO;
import com.liceolapaz.bcd.judokascompeticion.dao.judokasusuarios.JudokasUsuariosDAOImpl;
import com.liceolapaz.bcd.judokascompeticion.navigation.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pojos.Judoka;
import pojos.User;

import java.net.URL;
import java.util.ResourceBundle;

public class misJudokasController implements Initializable {
    @FXML
    private Button elim;
    @FXML
    private Button an;
    @FXML
    private Label cantidad;
    @FXML
    private TableView<Judoka> tablaF;
    @FXML
    private TableColumn<Judoka, String> nombreF;
    @FXML
    private TableColumn<Judoka, String> apellidoF;
    @FXML
    private TableColumn<Judoka, String> paisF;
    @FXML
    private TableView<Judoka> table;
    @FXML
    private TableColumn<Judoka, String> tnombre;
    @FXML
    private TableColumn<Judoka, String> tApellido;
    @FXML
    private TableColumn<Judoka, String> tPais;

    private JudokasDAO judokaDAO = new JudokaDAOImpl();
    private ObservableList<Judoka> judokas;
    private Judoka judokaSeleccionado;

    private User usuarioLogueado;
    private ObservableList<Judoka> judokasFavoritos;
    private JudokasUsuariosDAO judokasUsuariosDAO = new JudokasUsuariosDAOImpl();

    public void meterEnTablaF() {
        nombreF.setCellValueFactory(new PropertyValueFactory<>("name"));
        apellidoF.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        paisF.setCellValueFactory(new PropertyValueFactory<>("country"));

        tablaF.setItems(judokasFavoritos);
    }

    public void meterEnTabla() {
        tnombre.setCellValueFactory(new PropertyValueFactory<>("name"));
        tApellido.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tPais.setCellValueFactory(new PropertyValueFactory<>("country"));

        table.setItems(judokas);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        judokas = FXCollections.observableArrayList();
        elim.setDisable(true);
        an.setDisable(true);

        this.usuarioLogueado = SessionManager.getInstance().getUsuario();

        judokas.addAll(judokaDAO.obtenerJudokas());
        judokasFavoritos = FXCollections.observableArrayList();
        judokasFavoritos.addAll(judokasUsuariosDAO.obtenerJudokas(SessionManager.getInstance().getUsuario()));
        judokas.removeAll(judokasFavoritos);
        meterEnTabla();


        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    judokaSeleccionado = newValue;
                    elim.setDisable(true);
                    an.setDisable(false);
                });



        meterEnTablaF();

        tablaF.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        judokaSeleccionado = newValue;
                        elim.setDisable(false);
                        an.setDisable(true);
                    }
                });
        cantidad.setText(Integer.toString(judokasFavoritos.size()));
        judokas.removeAll(judokasFavoritos);
    }

    private void quitarFavoritos(Judoka judoka) {
        judokasUsuariosDAO.eliminarJudokas(SessionManager.getInstance().getUsuario(), judoka);

        judokasFavoritos.remove(judoka);

        judokas.add(judoka);

        cantidad.setText(Integer.toString(judokasFavoritos.size()));
        elim.setDisable(true);
        an.setDisable(true);
    }

    private void anhadirFavoritos(Judoka judoka) {
        judokasUsuariosDAO.insertarJudokas(SessionManager.getInstance().getUsuario(), judoka);

        judokas.remove(judoka);

        judokasFavoritos.add(judoka);

        cantidad.setText(Integer.toString(judokasFavoritos.size()));
        elim.setDisable(true);
        an.setDisable(true);
    }
    @FXML
    public void onAddJudoka() {

        if (judokaSeleccionado != null && !judokasFavoritos.contains(judokaSeleccionado)) {
            anhadirFavoritos(judokaSeleccionado);
            table.getSelectionModel().clearSelection(); }
    }
    @FXML
    public void onRemJudoka() {
        if (judokaSeleccionado != null) {
            quitarFavoritos(judokaSeleccionado);
            tablaF.getSelectionModel().clearSelection();
        }
    }

}
