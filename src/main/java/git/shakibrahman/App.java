package git.shakibrahman;

import git.shakibrahman.model.IOPackage.LucyIO;
import git.shakibrahman.model.generator.CoreGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private LucyIO lucyIO = new LucyIO();

    @Override
    public void start(Stage stage) throws IOException {
        if (lucyIO.startUpCheck()) {
            CoreGenerator generator = lucyIO.getGenerator();
            MainScreen screen = new MainScreen();
            screen.startMain(generator, lucyIO);
            scene = new Scene(loadFXML("MainScreen"));
        } else {
            scene = new Scene(loadFXML("LoginScreen"));
        }
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}