package wacka;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static final String DEFAULT_FILE_PATH = "data/wacka.txt";
    private final String filePath;
    private Wacka wacka;

    public Main(String filePath) {
        this.filePath = filePath;
        this.wacka = new Wacka(filePath);
    }

    public Main() {
        this(DEFAULT_FILE_PATH);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            javafx.scene.layout.AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setWacka(wacka);
            
            stage.setTitle("Wacka - " + filePath);
            stage.setResizable(false);
            stage.setMinHeight(600.0);
            stage.setMinWidth(400.0);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}