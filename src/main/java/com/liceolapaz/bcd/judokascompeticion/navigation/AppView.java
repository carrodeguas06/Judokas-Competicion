package com.liceolapaz.bcd.judokascompeticion.navigation;

public enum AppView {
    LOGIN("/com/liceolapaz/bcd/judokascompeticion/controllers/login-view.fxml"),
    JUDOKAS("/com/liceolapaz/bcd/judokascompeticion/controllers/judokas-view.fxml"),
    MAIN("/com/liceolapaz/bcd/judokascompeticion/controllers/main-view.fxml"),
    BIENVENIDO("/com/liceolapaz/bcd/judokascompeticion/controllers/bienvenido-view.fxml");

    private final String fxmlFile;

    AppView(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }
    public String getFxmlFile() {return fxmlFile;}
}
