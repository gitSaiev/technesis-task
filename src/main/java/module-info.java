module ru.saiev.technesistask {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ru.saiev.technesistask to javafx.fxml;
    exports ru.saiev.technesistask;
    exports ru.saiev.technesistask.config;
    opens ru.saiev.technesistask.config to javafx.fxml;
    exports ru.saiev.technesistask.controller;
    opens ru.saiev.technesistask.controller to javafx.fxml;
    exports ru.saiev.technesistask.service;
    opens ru.saiev.technesistask.service to javafx.fxml;
}