package wacka;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Wacka wacka;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/CharlieImage.jpg"));
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/SnoopyImage.png"));

    @FXML
    public void initialize() {
        // Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }
    
    /**
     * Shows the welcome message when the GUI starts.
     * Should be called after setWacka() is called.
     */
    public void showWelcome() {
        if (wacka != null) {
            String welcome = wacka.getWelcomeMessage();
            dialogContainer.getChildren().add(
                    DialogBox.getWackaDialog(welcome, botImage)
            );
        }
    }

    /** Injects the Wacka instance */
    public void setWacka(Wacka w) {
        wacka = w;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        assert wacka != null : "wacka must be set before handling input";
        String input = userInput.getText();
        String response = wacka.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getWackaDialog(response, botImage)
        );
        userInput.clear();
    }
}
