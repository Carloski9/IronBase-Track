module com.ironbasetrack.ironbasetrack {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.ironbasetrack.ironbasetrack to javafx.fxml;
    opens com.ironbasetrack.ironbasetrack.controller to javafx.fxml;

    opens com.ironbasetrack.ironbasetrack.model to javafx.base;

    exports com.ironbasetrack.ironbasetrack;
    exports com.ironbasetrack.ironbasetrack.controller;
}