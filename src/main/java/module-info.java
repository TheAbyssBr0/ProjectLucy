module git.shakibrahman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;

    opens git.shakibrahman to javafx.fxml;
    exports git.shakibrahman;
}