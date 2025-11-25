package com.liceolapaz.bcd.judokascompeticion.navigation;

public enum AppView {
    LOGIN("/com/liceolapaz/bcd/judokascompeticion/controllers/login-view.fxml"),
    REGISTER("/com/liceolapaz/bcd/judokascompeticion/controllers/register-view.fxml"),
    MIPERFIL("/com/liceolapaz/bcd/judokascompeticion/controllers/miPerfil-view.fxml"),
    EDITARPERFIL("/com/liceolapaz/bcd/judokascompeticion/controllers/editar-view.fxml"),
    JUDOKAS("/com/liceolapaz/bcd/judokascompeticion/controllers/judokas-view.fxml"),
    COMPETICIONES("/com/liceolapaz/bcd/judokascompeticion/controllers/competiciones-view.fxml"),
    USERS("/com/liceolapaz/bcd/judokascompeticion/controllers/usuarios-view.fxml"),
    MISJUDOKAS("/com/liceolapaz/bcd/judokascompeticion/controllers/misJudokas-view.fxml"),
    MAIN("/com/liceolapaz/bcd/judokascompeticion/controllers/main-view.fxml"),
    BIENVENIDO("/com/liceolapaz/bcd/judokascompeticion/controllers/bienvenido-view.fxml");

    private final String fxmlFile;

    AppView(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }
}
