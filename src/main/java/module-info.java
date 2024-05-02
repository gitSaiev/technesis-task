module ru.saiev.technesistask {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.saiev.technesistask to javafx.fxml;
    exports ru.saiev.technesistask;
}