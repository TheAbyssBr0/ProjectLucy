package git.shakibrahman;

import git.shakibrahman.model.IOPackage.LucyIO;
import git.shakibrahman.model.generator.CoreGenerator;
import git.shakibrahman.model.person.Person;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class NewUserScreen {

    private LucyIO lucyIO = new LucyIO();
    private CoreGenerator generator;

    @FXML
    private TextField newUsernameField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField newPinField;

    @FXML
    private CheckBox newRememberMeCheck;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private PasswordField confirmPinField;

    @FXML
    private void backToLoginScreen() throws IOException {
        App.setRoot("LoginScreen");
    }

    @FXML
    private void login() {
        String username = this.newUsernameField.getText();
        String password = this.newPasswordField.getText();
        String confirmPassword = this.confirmPasswordField.getText();

        int pin = 0;
        int confirmPin = 0;

        try {
            pin = Integer.parseInt(this.newPinField.getText());
            confirmPin = Integer.parseInt(this.confirmPinField.getText());
        } catch (Exception ignored) {}

        Person user = new Person(username, password, pin);

        if (username.equals("") || password.equals("") || pin <= 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(">:c");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid username, password and PIN!");

            alert.showAndWait();
        } else if (!password.equals(confirmPassword)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(">:C");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match. Please try again.");

            alert.showAndWait();
        } else if (pin != confirmPin) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(">:(");
            alert.setHeaderText(null);
            alert.setContentText("PINs do not match. Please try again.");

            alert.showAndWait();
        } else {
            if (newRememberMeCheck.isSelected())
                lucyIO.rememberMe(user);

            this.generator = new CoreGenerator(user);
            try {
                MainScreen mainController = new MainScreen();
                mainController.startMain(this.generator, lucyIO);
                App.setRoot("MainScreen");
            } catch (Exception ignored) {
            }
        }
    }
}
