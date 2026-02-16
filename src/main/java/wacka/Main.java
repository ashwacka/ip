package wacka;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static final String DEFAULT_FILE_PATH = "data/wacka.txt";
    private final String filePath;
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private Image userImage;
    private Image botImage;

    public Main(String filePath) {
        this.filePath = filePath;
    }

    public Main() {
        this(DEFAULT_FILE_PATH);
    }

    @Override
    public void start(Stage stage) {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        userImage = loadImageOrPlaceholder("/images/UserImage.jpg");
        botImage = loadImageOrPlaceholder("/images/WackaImage.png");
        DialogBox dialogBox = new DialogBox("Hello! I am Wacka.", botImage);
        dialogContainer.getChildren().add(dialogBox);

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setTitle("Wacka - " + filePath);
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385.0, 535.0);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);
        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        stage.setScene(scene);
        stage.show();
    }

    private Image loadImageOrPlaceholder(String resourcePath) {
        java.io.InputStream stream = getClass().getResourceAsStream(resourcePath);
        if (stream != null) {
            return new Image(stream);
        }
        // Create a visible colored placeholder image
        WritableImage placeholder = new WritableImage(100, 100);
        var pixelWriter = placeholder.getPixelWriter();
        // Fill with a light gray color so it's visible
        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                pixelWriter.setColor(x, y, Color.LIGHTGRAY);
            }
        }
        return placeholder;
    }
}
