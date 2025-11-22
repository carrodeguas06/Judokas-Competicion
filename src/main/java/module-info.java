module com.liceolapaz.bcd.judokascompeticion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.liceolapaz.bcd.judokascompeticion;


    opens com.liceolapaz.bcd.judokascompeticion to javafx.fxml;
    exports com.liceolapaz.bcd.judokascompeticion;
    exports com.liceolapaz.bcd.judokascompeticion.controllers;
    opens com.liceolapaz.bcd.judokascompeticion.controllers to javafx.fxml;
    exports com.liceolapaz.bcd.judokascompeticion.pojo;
    opens com.liceolapaz.bcd.judokascompeticion.pojo to javafx.fxml;
}