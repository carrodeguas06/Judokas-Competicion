module com.liceolapaz.bcd.judokascompeticion {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires mysql.connector.j;
    requires java.naming;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens pojos to org.hibernate.orm.core, javafx.base;
    exports com.liceolapaz.bcd.judokascompeticion;
    opens com.liceolapaz.bcd.judokascompeticion to javafx.fxml;
    exports com.liceolapaz.bcd.judokascompeticion.controllers;
    opens com.liceolapaz.bcd.judokascompeticion.controllers to javafx.fxml;
    exports com.liceolapaz.bcd.judokascompeticion.navigation;
    opens com.liceolapaz.bcd.judokascompeticion.navigation to javafx.fxml;
}