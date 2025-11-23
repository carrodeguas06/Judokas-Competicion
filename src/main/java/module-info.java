module com.liceolapaz.bcd.judokascompeticion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires javafx.graphics;

    opens pojos to org.hibernate.orm.core, javafx.base;
    opens com.liceolapaz.bcd.judokascompeticion to javafx.fxml;
    exports com.liceolapaz.bcd.judokascompeticion;
    exports com.liceolapaz.bcd.judokascompeticion.controllers;
    opens com.liceolapaz.bcd.judokascompeticion.controllers to javafx.fxml;
    exports com.liceolapaz.bcd.judokascompeticion.navigation;
    opens com.liceolapaz.bcd.judokascompeticion.navigation to javafx.fxml;
}