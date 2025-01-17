module ConnectFour {
    requires javafx.controls;
    requires javafx.fxml;

    opens application to javafx.fxml;
    opens application.controllers to javafx.fxml;

    exports application;
    exports application.controllers;
}
