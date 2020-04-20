package git.shakibrahman;

import git.shakibrahman.model.IOPackage.LucyIO;
import git.shakibrahman.model.generator.CoreGenerator;
import git.shakibrahman.model.person.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginScreen {

    private LucyIO lucyIO = new LucyIO();
    private CoreGenerator generator;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField pinField;

    @FXML
    private CheckBox rememberMeCheck;

    @FXML
    private void newUserScreen() throws IOException {
        App.setRoot("NewUserScreen");
    }

    @FXML
    private void login() {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        int pin = 0;

        try {
            pin = Integer.parseInt(this.pinField.getText());
        } catch (Exception ignored) {}

        Person user = new Person(username, password, pin);

        if (username.equals("") || password.equals("") || pin <= 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(">:c");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid username, password and PIN!");

            alert.showAndWait();
        } else {

            if (rememberMeCheck.isSelected())
                lucyIO.rememberMe(user);

            this.generator = new CoreGenerator(user);
            try {
                MainScreen mainController = new MainScreen();
                mainController.startMain(this.generator, this.lucyIO);
                App.setRoot("MainScreen");
            } catch (Exception ignored) {
            }
        }
    }
}
