package git.shakibrahman;

import git.shakibrahman.model.IOPackage.LucyIO;
import git.shakibrahman.model.generator.CoreGenerator;
import git.shakibrahman.model.utils.LucyUtils;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class MainScreen {
    private static CoreGenerator generator;
    private static LucyIO lucyIO;

    void startMain(CoreGenerator generator, LucyIO lucyIO) {
        MainScreen.generator = generator;
        MainScreen.lucyIO = lucyIO;
    }

    private String password;

    @FXML
    private PasswordField generatedPasswordField;

    @FXML
    private CheckBox lowercaseToggle;

    @FXML
    private CheckBox uppercaseToggle;

    @FXML
    private CheckBox numbersToggle;

    @FXML
    private CheckBox symbolsToggle;

    @FXML
    private Label passwordLengthLabel;

    @FXML
    private Label numberLengthLabel;

    @FXML
    private Slider passwordLengthSlider;

    @FXML
    private Slider numberSlider;

    @FXML
    private TextField serviceTextBox;

    @FXML
    void checkCharacters() {
        if (!(lowercaseToggle.isSelected() || uppercaseToggle.isSelected()
                || numbersToggle.isSelected() || symbolsToggle.isSelected())){
            lowercaseToggle.setSelected(true);
        }
    }

    @FXML
    private void copy() {
        LucyUtils.copyText(this.password);
    }

    @FXML
    void generate() throws NoSuchAlgorithmException {
        if (!(serviceTextBox.getText().equals(""))) {
            this.password = generator.getPassword(serviceTextBox.getText(), (int)numberSlider.getValue(),
                    (int)passwordLengthSlider.getValue(),
                    LucyUtils.boolToKey(lowercaseToggle.isSelected(), uppercaseToggle.isSelected(),
                            numbersToggle.isSelected(), symbolsToggle.isSelected()));
        }
        generatedPasswordField.setText(this.password);
    }

    @FXML
    void logout() throws IOException {
        lucyIO.logOut();
        App.setRoot("LoginScreen");
    }

    @FXML
    void updateLengthLabel() {
        String lengthLabelString = "Password Length: ";
        passwordLengthLabel.setText(lengthLabelString + (int) passwordLengthSlider.getValue());
    }

    @FXML
    void updatePasswordNumber() {
        String numLabelString = "Password Number: ";
        numberLengthLabel.setText(numLabelString + (int) numberSlider.getValue());
    }
}